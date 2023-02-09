package org.firstinspires.ftc.teamcode.ftc16072.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.checkerframework.checker.units.qual.Angle;
import org.firstinspires.ftc.robotcore.external.matrices.GeneralMatrixF;
import org.firstinspires.ftc.robotcore.external.matrices.MatrixF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ftc16072.Robot;
import org.firstinspires.ftc.teamcode.ftc16072.tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.tests.TestMotor;
import org.firstinspires.ftc.teamcode.ftc16072.util.MoveDeltas;
import org.firstinspires.ftc.teamcode.ftc16072.util.Polar;
import org.firstinspires.ftc.teamcode.ftc16072.util.RobotPose;

import java.util.Arrays;
import java.util.List;

public class MecanumDrive extends Mechanism {
    DcMotorEx leftFront;
    DcMotorEx rightFront;

    DcMotorEx leftRear;
    DcMotorEx rightRear;
    //odometry
    public DcMotor LeftEncoder;
    public DcMotor RightEncoder;
    public DcMotor PerpEncoder;
    private Robot robot;
    public final double CIRCUMFERENCE = 10 * Math.PI;// needs to be changed
    public final double TICKS_PER_INCH = 15.3; // number will need to be changed based on the diameter of the encoder wheel
    public double oldTheta = 0;
    public double Old_Right_Encoder;
    public double Old_Left_Encoder;
    public double Old_Perp_Encoder;
    private final double TURNING_CIRCUMFERENCE=8;

    private final double GEAR_RATIO = 1.0;
    private final double WHEEL_RADIUS = 5.0; //cmstarted
    private final double TPR = 537.7; //ticks per rotation
    //cm per rotation/ticks per rotation
    private final double CM_PER_TICK = (2 * Math.PI * GEAR_RATIO * WHEEL_RADIUS) / TPR;
    private double maxSpeed = 0.7;

    private int frontLeftOffset;
    private int frontRightOffset;
    @SuppressWarnings("unused")
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

        //odometry
        LeftEncoder = leftFront;
        RightEncoder = rightFront;
        PerpEncoder = rightRear;






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
    private MatrixF conversionToHypo;
    private MatrixF conversionToFieldRelative;
    private final GeneralMatrixF encoderMatrix = new GeneralMatrixF(3, 1);
    /* not used
    public MecanumDrive() {
        float[] data = {1.0f, 1.0f, 1.0f,
                1.0f, -1.0f, -1.0f,
                1.0f, -1.0f, 1.0f};
        conversion = new GeneralMatrixF(3, 3, data);
        conversion = conversion.inverted();
    }
    */
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

    @SuppressWarnings("unused")
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

    public MoveDeltas getDistance() {

        double thetaDifference = robot.gyro.getHeading(AngleUnit.DEGREES)-oldTheta;
        double [] encoderValues = robot.odometry.getDistance();
        double robot_forward = encoderValues[0]-(thetaDifference/360*TURNING_CIRCUMFERENCE);
        double robot_strafe = encoderValues[1]-(thetaDifference/360*TURNING_CIRCUMFERENCE);
        double angleToField = oldTheta-Math.atan(robot_strafe/robot_forward);


        encoderMatrix.put(0, 0, (float) ((robot_forward) * CM_PER_TICK));
        encoderMatrix.put(1, 0, (float) ((robot_strafe) * CM_PER_TICK));
        encoderMatrix.put(2, 0, (float) ((thetaDifference) * CM_PER_TICK));

        //covert to field relative

        float[] ConversionToHypo = {
                (float)Math.cos(oldTheta-angleToField),
                (float) Math.sin(oldTheta-angleToField),
                (float) 0};

        conversionToHypo = new GeneralMatrixF(3,1,ConversionToHypo);

        float[] ConversionToFieldRelative = {
                (float) Math.cos(angleToField),
                (float) Math.sin(angleToField),
                (float) 0};
        conversionToFieldRelative = new GeneralMatrixF(3,1,ConversionToFieldRelative);

        MatrixF distanceMatrix = conversionToHypo.multiplied(encoderMatrix); // how to multiply 3 matrices with 1 line?
        distanceMatrix = distanceMatrix.multiplied(conversionToFieldRelative);

        double forward = distanceMatrix.get(0, 0);
        double strafe = distanceMatrix.get(1, 0);
        //double angle = distanceMatrix.get(0, 2);
        double angle = 0;
        oldTheta = robot.gyro.getHeading(AngleUnit.DEGREES);
        conversionToFieldRelative = new GeneralMatrixF(3,1, ConversionToFieldRelative);

        return new MoveDeltas(forward, strafe, DistanceUnit.CM, angle , AngleUnit.DEGREES);
    }

}