package org.firstinspires.ftc.teamcode.ftc16072.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ftc16072.tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.tests.TestMotor;
import org.firstinspires.ftc.teamcode.ftc16072.util.MoveDeltas;

import java.util.Arrays;
import java.util.List;

public class Odometry extends Mechanism {
    private DcMotor forwardEncoder;
    private DcMotor strafeEncoder;

    public double oldStrafeEncoderValue;
    public double oldForwardEncoderValue;
    public double TICKS_PER_ROTATION = 8125;
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
    public double[] getDistance(boolean reset) {
        double[] distances = {0.0, 0.0};
        distances[0] = forwardEncoder.getCurrentPosition()-oldForwardEncoderValue;
        distances[1] = strafeEncoder.getCurrentPosition()-oldStrafeEncoderValue;
        return distances;
    }

}
