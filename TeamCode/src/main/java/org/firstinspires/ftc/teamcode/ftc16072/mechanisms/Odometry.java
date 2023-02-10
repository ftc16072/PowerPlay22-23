package org.firstinspires.ftc.teamcode.ftc16072.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.matrices.GeneralMatrixF;
import org.firstinspires.ftc.robotcore.external.matrices.MatrixF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ftc16072.Robot;
import org.firstinspires.ftc.teamcode.ftc16072.tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.tests.TestMotor;
import org.firstinspires.ftc.teamcode.ftc16072.util.MoveDeltas;

import java.util.Arrays;
import java.util.List;

public class Odometry extends Mechanism {
    private DcMotor forwardEncoder;
    private DcMotor strafeEncoder;
    private Robot robot;
    private double oldTheta;
    private final GeneralMatrixF encoderMatrix = new GeneralMatrixF(3, 1);
    public double oldStrafeEncoderValue;
    public double oldForwardEncoderValue;
    public double TICKS_PER_ROTATION = 8125;
    private double TURNING_CIRCUMFERENCE = 8; // need value from mech team
    private double ENCODER_WHEEL_DIAMETER_MM = 38;
    public double CM_PER_ROTATION = (ENCODER_WHEEL_DIAMETER_MM/10)*Math.PI;

    private double ticksToCm(double numTicks){
        double numRotations = numTicks / TICKS_PER_ROTATION;
        return numRotations * CM_PER_ROTATION;

    }



    @Override
    public void init(HardwareMap hwMap) {
        forwardEncoder = hwMap.get(DcMotor.class,"forward_encoder");
        strafeEncoder  = hwMap.get(DcMotor.class, "strafe_encoder");
        oldForwardEncoderValue = forwardEncoder.getCurrentPosition();
        oldStrafeEncoderValue = strafeEncoder.getCurrentPosition();

    }

    @Override
    public List<QQTest> getTests() {
        return Arrays.asList(
                new TestMotor(forwardEncoder, "Forward Encoder", 0),
                new TestMotor(strafeEncoder, "Strafe Encoder", 0));
    }


    public MoveDeltas getDistance(boolean reset) {

        double thetaDifference = robot.gyro.getHeading(AngleUnit.DEGREES)-oldTheta;
        double forwardDifference = ticksToCm(forwardEncoder.getCurrentPosition()-oldForwardEncoderValue);
        double strafeDifference = ticksToCm(strafeEncoder.getCurrentPosition()-oldStrafeEncoderValue);
        double robot_forward = forwardDifference-(thetaDifference/360*TURNING_CIRCUMFERENCE);
        double robot_strafe = strafeDifference-(thetaDifference/360*TURNING_CIRCUMFERENCE);
        double angleToField = oldTheta-Math.atan(robot_strafe/robot_forward);


        encoderMatrix.put(0, 0, (float) ((robot_forward)));
        encoderMatrix.put(1, 0, (float) ((robot_strafe)));
        encoderMatrix.put(2, 0, (float) ((thetaDifference)));

        //covert to field relative

        float[] ConversionToHypo = {
                (float)Math.cos(oldTheta-angleToField),
                (float) Math.sin(oldTheta-angleToField),
                (float) 0};

        MatrixF conversionToHypo = new GeneralMatrixF(3, 1, ConversionToHypo);

        float[] ConversionToFieldRelative = {
                (float) Math.cos(angleToField),
                (float) Math.sin(angleToField),
                (float) 0};
        MatrixF conversionToFieldRelative = new GeneralMatrixF(3, 1, ConversionToFieldRelative);

        MatrixF distanceMatrix = conversionToHypo.multiplied(encoderMatrix); // how to multiply 3 matrices with 1 line?
        distanceMatrix = distanceMatrix.multiplied(conversionToFieldRelative);

        double forward = distanceMatrix.get(0, 0);
        double strafe = distanceMatrix.get(1, 0);
        //double angle = distanceMatrix.get(0, 2);
        double angle = 0;
        if (reset){
            oldTheta = robot.gyro.getHeading(AngleUnit.DEGREES);
            oldStrafeEncoderValue = strafeEncoder.getCurrentPosition();
            oldForwardEncoderValue = forwardEncoder.getCurrentPosition();

        }



        return new MoveDeltas(forward, strafe, DistanceUnit.CM, angle , AngleUnit.DEGREES);
}}
