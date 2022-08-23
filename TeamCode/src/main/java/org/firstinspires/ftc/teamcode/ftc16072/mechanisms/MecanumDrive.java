package org.firstinspires.ftc.teamcode.ftc16072.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.matrices.GeneralMatrixF;
import org.firstinspires.ftc.robotcore.external.matrices.MatrixF;
import org.firstinspires.ftc.teamcode.ftc16072.tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.tests.TestMotor;

import java.util.Arrays;
import java.util.List;

public class MecanumDrive extends Mechanism {
    DcMotorEx leftFront;
    DcMotorEx rightFront;
    DcMotorEx leftRear;
    DcMotorEx rightRear;

    private final double GEAR_RATIO = 4.0 / 6.0;
    private final double WHEEL_RADIUS = 5.0; //cmstarted
    private final double TPR = 383.6; //ticks per rotation
    //cm per rotation/ticks per rotation
    private final double CM_PER_TICK = (2 * Math.PI * GEAR_RATIO * WHEEL_RADIUS) / TPR;
    private double maxSpeed = 1.0;

    private int frontLeftOffset;
    private int frontRightOffset;
    private int backRightOffset;
    private int backLeftOffset;

    @Override
    public void init(HardwareMap hwMap) {
        leftFront = hwMap.get(DcMotorEx.class, "front_left_motor");
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRear = hwMap.get(DcMotorEx.class, "back_left_motor");
        leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRear = hwMap.get(DcMotorEx.class, "back_right_motor");
        rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront = hwMap.get(DcMotorEx.class, "front_right_motor");
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public List<QQTest> getTests() {
        return Arrays.asList(
                new TestMotor(leftFront, "Left Front", 0.2),
                new TestMotor(rightFront, "Right Front", 0.2),
                new TestMotor(leftRear, "Left Rear", 0.2),
                new TestMotor(rightRear, "Right Rear", 0.2)
        );
    }

    public void drive(double forward, double right, double rotate) {
        double leftFrontPower = forward + right + rotate;
        double rightFrontPower = forward - right - rotate;
        double rightRearPower = forward + right - rotate;
        double leftRearPower = forward - right + rotate;
        double maxPower = 1.0;
        double maxSpeed = 0.5;

        maxPower = Math.max(maxPower, Math.abs(leftFrontPower));
        maxPower = Math.max(maxPower, Math.abs(rightFrontPower));
        maxPower = Math.max(maxPower, Math.abs(rightRearPower));
        maxPower = Math.max(maxPower, Math.abs(leftRearPower));


        leftFront.setPower(maxSpeed * (leftFrontPower/maxPower));
        rightFront.setPower(maxSpeed * (rightFrontPower/maxPower));
        rightRear.setPower(maxSpeed * rightRearPower/maxPower);
        leftRear.setPower(maxSpeed * (leftRearPower/maxPower));
    }

    private MatrixF conversion;
    private final GeneralMatrixF encoderMatrix = new GeneralMatrixF(3, 1);

    public MecanumDrive() {
        float[] data = {1.0f, 1.0f, 1.0f,
                        1.0f, -1.0f, -1.0f,
                        1.0f, -1.0f, 1.0f};
        conversion = new GeneralMatrixF(3, 3, data);
        conversion = conversion.inverted();
    }

    double[] getDistance() {
        double[] distances = {0.0, 0.0};

        encoderMatrix.put(0, 0, (float) ((leftFront.getCurrentPosition() - frontLeftOffset) * CM_PER_TICK));
        encoderMatrix.put(1, 0, (float) ((rightFront.getCurrentPosition() - frontRightOffset) * CM_PER_TICK));
        encoderMatrix.put(2, 0, (float) ((leftRear.getCurrentPosition() - backLeftOffset) * CM_PER_TICK));

        MatrixF distanceMatrix = conversion.multiplied(encoderMatrix);
        distances[0] = distanceMatrix.get(0, 0);
        distances[1] = distanceMatrix.get(1, 0);

        return distances;
    }

    void setEncodeOffsets() {
        frontRightOffset = rightFront.getCurrentPosition();
        frontLeftOffset = leftFront.getCurrentPosition();
        backLeftOffset = leftRear.getCurrentPosition();
        backRightOffset = rightRear.getCurrentPosition();
    }

    void setMaxSpeed(double speed) {
        maxSpeed = Math.min(speed, 1.0);
    }
}