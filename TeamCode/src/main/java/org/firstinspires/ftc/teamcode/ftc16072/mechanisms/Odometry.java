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
        double robot_forward = forwardDifference+(thetaDifference/360*TURNING_CIRCUMFERENCE);
        double robot_strafe = strafeDifference-(thetaDifference/360*TURNING_CIRCUMFERENCE);

        double angle = 0;
        if (reset){
            oldTheta = robot.gyro.getHeading(AngleUnit.DEGREES);
            oldStrafeEncoderValue = strafeEncoder.getCurrentPosition();
            oldForwardEncoderValue = forwardEncoder.getCurrentPosition();

        }

        return new MoveDeltas(robot_forward, robot_strafe, DistanceUnit.CM, angle , AngleUnit.DEGREES);
}}
