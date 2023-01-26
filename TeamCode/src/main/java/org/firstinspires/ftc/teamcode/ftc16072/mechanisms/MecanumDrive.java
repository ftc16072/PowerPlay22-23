package org.firstinspires.ftc.teamcode.ftc16072.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.matrices.GeneralMatrixF;
import org.firstinspires.ftc.robotcore.external.matrices.MatrixF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ftc16072.tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.tests.TestMotor;
import org.firstinspires.ftc.teamcode.ftc16072.util.MoveDeltas;
import org.firstinspires.ftc.teamcode.ftc16072.util.Polar;

import java.util.Arrays;
import java.util.List;

public class MecanumDrive extends Mechanism {
    DcMotorEx leftFront;
    DcMotorEx rightFront;

    DcMotorEx leftRear;
    DcMotorEx rightRear;

    private final double GEAR_RATIO = 1.0;
    private final double WHEEL_RADIUS = 5.0; //cmstarted
    private final double TPR = 537.7; //ticks per rotation
    //cm per rotation/ticks per rotation
    private final double CM_PER_TICK = (2 * Math.PI * GEAR_RATIO * WHEEL_RADIUS) / TPR;
    private double maxSpeed = 0.7;

    private int frontLeftOffset;
    private int frontRightOffset;
    private int backRightOffset;
    private int backLeftOffset;
   public final double PI = Math.PI;


    @Override
    public void init(HardwareMap hwMap) {
        leftFront = hwMap.get(DcMotorEx.class, "front_left_motor");
        leftRear = hwMap.get(DcMotorEx.class, "back_left_motor");
        rightRear = hwMap.get(DcMotorEx.class, "back_right_motor");
        rightFront = hwMap.get(DcMotorEx.class, "front_right_motor");
        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        resetEncoders();
    }
    public void resetEncoders(){
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRear.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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

        maxPower = Math.max(maxPower, Math.abs(leftFrontPower));
        maxPower = Math.max(maxPower, Math.abs(rightFrontPower));
        maxPower = Math.max(maxPower, Math.abs(rightRearPower));
        maxPower = Math.max(maxPower, Math.abs(leftRearPower));


        leftFront.setPower(maxSpeed * (leftFrontPower / maxPower));
        rightFront.setPower(maxSpeed * (rightFrontPower / maxPower));
        rightRear.setPower(maxSpeed * rightRearPower / maxPower);
        leftRear.setPower(maxSpeed * (leftRearPower / maxPower));
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

    public double[] getDistanceCM() {
        double[] distances = {0.0, 0.0};

        encoderMatrix.put(0, 0, (float) ((leftFront.getCurrentPosition() - frontLeftOffset) * CM_PER_TICK));
        encoderMatrix.put(1, 0, (float) ((rightFront.getCurrentPosition() - frontRightOffset) * CM_PER_TICK));
        encoderMatrix.put(2, 0, (float) ((leftRear.getCurrentPosition() - backLeftOffset) * CM_PER_TICK));

        MatrixF distanceMatrix = conversion.multiplied(encoderMatrix);
        distances[0] = distanceMatrix.get(0, 0);
        distances[1] = distanceMatrix.get(1, 0);

        return distances;
    }

    public void setEncodeOffsets() {
        frontRightOffset = rightFront.getCurrentPosition();
        frontLeftOffset = leftFront.getCurrentPosition();
        backLeftOffset = leftRear.getCurrentPosition();
        backRightOffset = rightRear.getCurrentPosition();
    }

    public void driveOrthogonal(double joystickX, double joystickY){
        Polar orthogonal = new Polar(joystickX, joystickY);
        double theta = orthogonal.getTheta(AngleUnit.RADIANS);
        double r = Math.sqrt(Math.pow(joystickX,2)+Math.pow(joystickY,2));

        if(theta>=PI/4&&theta<=3*PI/4){
            drive(r,0,0);
        }
        if(theta<=-PI/4&&theta>=-3*PI/4){
            drive(-r,0,0);
        }
        if((theta<(-3*PI/4) && theta>=-PI) || (theta>(3*PI)/4 && theta<PI)){
            drive(0,-r,0);
        }
        if((theta>(-PI/4) && theta<=0) || (theta<PI/4 && theta>0)){
            drive(0,r,0);
        }
    }

    public void setMaxSpeed(double speed) {
        maxSpeed = Math.min(speed, 1.0);
    }

    public MoveDeltas getDistance(boolean reset) {
        int backLeftPosition = leftRear.getCurrentPosition();
        int backRightPosition = rightRear.getCurrentPosition();
        int frontLeftPosition = leftFront.getCurrentPosition();
        int frontRightPosition = rightFront.getCurrentPosition();

        encoderMatrix.put(0, 0, (float) ((frontLeftPosition - frontLeftOffset) * CM_PER_TICK));
        encoderMatrix.put(1, 0, (float) ((frontRightPosition - frontRightOffset) * CM_PER_TICK));
        encoderMatrix.put(2, 0, (float) ((backLeftPosition - backLeftOffset) * CM_PER_TICK));


        MatrixF distanceMatrix = conversion.multiplied(encoderMatrix);

        double forward = distanceMatrix.get(0, 0);
        double strafe = distanceMatrix.get(1, 0);
        //double angle = distanceMatrix.get(0, 2);
        double angle = 0;
        if(reset){
            frontLeftOffset  = frontLeftPosition;
            frontRightOffset = frontRightPosition;
            backLeftOffset   = backLeftPosition;
            backRightOffset  = backRightPosition;
        }

        return new MoveDeltas(forward, strafe, DistanceUnit.CM, angle , AngleUnit.DEGREES);
    }

}