package org.firstinspires.ftc.teamcode.ftc16072.mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.ftc16072.tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.tests.TestSwitch;
import org.firstinspires.ftc.teamcode.ftc16072.tests.TestTwoMotor;

import java.util.Arrays;
import java.util.List;

/*
 stateDiagram-v2
    [*] --> Intake
    note left of Intake
       Rotation = IntakeRotation
       Height = IntakeHeight
    end note
    Intake --> GroundPosition
    Intake --> LowPosition
    Intake --> MedPosition
    Intake --> HighPosition
    GroundPosition --> Intake
 */
@Config
public class Lift extends Mechanism {
    public static int BOTTOM_POSITION = 0;
    public static int GROUND_POSITION = 200;
    public static int HIGHPLACE_POSITION = 2800;
    public static int SAFE_POSITION = 400;  //TODO: test with cone
    public static int CONE_FIVE_STACK_POSITION = 400;
    public static int CONE_FOUR_STACK_POSITION = 350;
    public static int INTAKE_POSITION = 0;
    public static int LOW_POSITION = 1050;
    public static int MIDDLE_POSITION = 2075;
    public static int HIGH_POSITION = 2850;
    public static int SLIDES_MIN = 0;
    public static int SLIDES_MAX = 2940;
    public static double PROPORTIONAL_CONSTANT = 0.001;
    public static double GRAVITY_CONSTANT = 0.2;
    public static double MAX_LIFT_SPEED_UP = 1.0;
    public static double MAX_LIFT_SPEED_DOWN = 0.5;
    public static int CHECK_TOLERANCE = 150;
    public int desiredPosition;


    //TODO: find the right values and make final
    public DcMotorEx rightLiftMotor;
    public DcMotorEx leftLiftMotor;
    public Level state = Level.INTAKE;
    private DigitalChannel limitSwitch;


    public Level getCurrentLevel() {
        return state;
    }

    public enum Level {
        BOTTOM,
        INTAKE,
        GROUND,
        LOW,
        MIDDLE,
        HIGH,
        HIGHPLACE,
        CONE_FIVE_STACK,
        CONE_FOUR_STACK
    }

    @Override
    public void init(HardwareMap hwMap) {
        rightLiftMotor = hwMap.get(DcMotorEx.class, "right_lift_motor");
        //rightLiftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightLiftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightLiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLiftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftLiftMotor = hwMap.get(DcMotorEx.class, "left_lift_motor");
        leftLiftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftLiftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        leftLiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftLiftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        limitSwitch = hwMap.get(DigitalChannel.class, "lift_switch");
        limitSwitch.setMode(DigitalChannel.Mode.INPUT);

    }

    @Override
    public List<QQTest> getTests() {
        return Arrays.asList(
                new TestTwoMotor(rightLiftMotor, leftLiftMotor, "lift_up", 0.3),
                new TestTwoMotor(rightLiftMotor, leftLiftMotor, "lift_down", -0.2),
                new TestSwitch(limitSwitch, "limitSwitch")
        );

    }

    public void stopMotor() {
        rightLiftMotor.setPower(0);
        leftLiftMotor.setPower(0);
    }

    public void resetEncoder() {
        leftLiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftLiftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightLiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLiftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }


    public double getRightLiftPosition() {
        return rightLiftMotor.getCurrentPosition();
    }

    private double getLeftLiftPosition() {
        return leftLiftMotor.getCurrentPosition();
    }

    public boolean isAtLevel(Level level){
        return (Math.abs(getLeftLiftPosition() - levelToPosition(level)) <= CHECK_TOLERANCE);
    }


    public boolean isSafe() {
        return getLeftLiftPosition() > SAFE_POSITION;
    }

    public boolean isSafeToGoTo(int position) {
        return position >= SAFE_POSITION;
    }

    public boolean isSafeToAdjust(int change) {
        return isSafeToGoTo(Range.clip(desiredPosition + change, SLIDES_MIN, SLIDES_MAX));
    }

    private int levelToPosition(Level level){
        switch (level) {
            case INTAKE:
                return INTAKE_POSITION;

            case GROUND:
                return GROUND_POSITION;

            case LOW:
                return LOW_POSITION;

            case MIDDLE:
                return MIDDLE_POSITION;

            case HIGH:
                return HIGH_POSITION;

            case BOTTOM:
                return BOTTOM_POSITION;
            case HIGHPLACE:
                return HIGHPLACE_POSITION;
            case CONE_FIVE_STACK:
                return CONE_FIVE_STACK_POSITION;
            case CONE_FOUR_STACK:
                return CONE_FOUR_STACK_POSITION;
        }
        return 0;
    }
    public void goTo(Level level) {
        desiredPosition = levelToPosition(level);
    }

    private void checkAndReset() {
        //means pressed, getState is flipped
        if (limitSwitch.getState() == false && (leftLiftMotor.getCurrentPosition() != 0)) {
            leftLiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftLiftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        if (limitSwitch.getState() == false && (rightLiftMotor.getCurrentPosition() != 0)) {
            rightLiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightLiftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    public void adjustPosition(int change) {
        desiredPosition = Range.clip(desiredPosition + change, SLIDES_MIN, SLIDES_MAX);
    }

    private void update(DcMotor liftMotor) {
        double power;

        checkAndReset();
        if (desiredPosition == 0){
            if (limitSwitch.getState() == false){
                power = 0;
            }
            else {
                power = -1;
            }
        } else {
            int error = desiredPosition - liftMotor.getCurrentPosition();
            power = (error * PROPORTIONAL_CONSTANT);


            if (desiredPosition > 100) {
                power += GRAVITY_CONSTANT;
            }
        }
        power = Range.clip(power, -MAX_LIFT_SPEED_DOWN, MAX_LIFT_SPEED_UP);
        liftMotor.setPower(power);

    }

    public void update() {
        update(leftLiftMotor);
        update(rightLiftMotor);
    }

}