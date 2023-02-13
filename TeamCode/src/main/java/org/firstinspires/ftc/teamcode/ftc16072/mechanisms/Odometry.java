package org.firstinspires.ftc.teamcode.ftc16072.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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
    public double oldStrafeEncoderValue;
    public double oldForwardEncoderValue;
    public final double TICKS_PER_ROTATION = 8192;
    private final double ENCODER_WHEEL_DIAMETER_MM = 38;
    public double CM_PER_ROTATION = (ENCODER_WHEEL_DIAMETER_MM/10)*Math.PI;

    private double ticksToCm(double numTicks){
        double numRotations = numTicks / TICKS_PER_ROTATION;
        return numRotations * CM_PER_ROTATION;

    }



    @Override
    public void init(HardwareMap hwMap) {
        forwardEncoder = hwMap.get(DcMotor.class,"enc_left");
        strafeEncoder  = hwMap.get(DcMotor.class, "enc_x");
        forwardEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        strafeEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        oldForwardEncoderValue = forwardEncoder.getCurrentPosition();
        oldStrafeEncoderValue = strafeEncoder.getCurrentPosition();

        forwardEncoder.setDirection(DcMotorSimple.Direction.REVERSE);
        strafeEncoder.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    @Override
    public List<QQTest> getTests() {
        return Arrays.asList(
                new TestMotor(forwardEncoder, "Forward Encoder", 0),
                new TestMotor(strafeEncoder, "Strafe Encoder", 0));
    }


    public MoveDeltas getDistance(boolean reset) {


        double forwardDifference = (ticksToCm(forwardEncoder.getCurrentPosition()-oldForwardEncoderValue));
        double strafeDifference = (ticksToCm(strafeEncoder.getCurrentPosition()-oldStrafeEncoderValue));
        //


        if (reset){

            oldStrafeEncoderValue = strafeEncoder.getCurrentPosition();
            oldForwardEncoderValue = forwardEncoder.getCurrentPosition();

        }

        return new MoveDeltas(forwardDifference,strafeDifference, DistanceUnit.CM, 0 , AngleUnit.DEGREES);
}

}
