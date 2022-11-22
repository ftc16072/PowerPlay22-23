package org.firstinspires.ftc.teamcode.ftc16072.mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.ftc16072.tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.tests.TestMotor;
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
    public static int GROUND_POSITION = 200;
    public static int INTAKE_POSITION = 50;
    public static int LOW_POSITION = 1000;
    public static int MIDDLE_POSITION = 2000;
    public static int HIGH_POSITION = 2900;
    public static int SLIDES_MIN = 0;
    public static int SLIDES_MAX = 2940;
    public static double PROPORTIONAL_CONSTANT = 0.01;
    public static double GRAVITY_CONSTANT = 0.2;
    public static double MAX_LIFT_SPEED_UP = 1.0;
    public static double MAX_LIFT_SPEED_DOWN = 0.5;
    public int desiredPosition;


    //TODO: find the right values and make final
    public DcMotorEx rightLiftMotor;
    public DcMotorEx leftLiftMotor;
    public Level state = Level.INTAKE;

    public Level getCurrentLevel(){
        return state;
    }

    public enum Level {
        INTAKE,
        GROUND,
        LOW,
        MIDDLE,
        HIGH;

        public Level next(Level current) {
            Level returnValue = current;
            switch (current) {
                case INTAKE:
                    returnValue = GROUND;
                    break;
                case GROUND:
                    returnValue = LOW;
                    break;
                case LOW:
                    returnValue = MIDDLE;
                    break;
                case MIDDLE:
                    returnValue = HIGH;
                    break;
            }
            return returnValue;
        }

        public Level previous(Level current) {
            Level returnValue = current;
            switch (current) {
                case HIGH:
                    returnValue = MIDDLE;
                    break;
                case MIDDLE:
                    returnValue = LOW;
                    break;
                case LOW:
                    returnValue = GROUND;
                    break;
                case GROUND:
                    returnValue = INTAKE;
                    break;
            }
            return returnValue;
        }
    }

    @Override
    public void init(HardwareMap hwMap) {
        rightLiftMotor = hwMap.get(DcMotorEx.class, "right_lift_motor");
        rightLiftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightLiftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightLiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLiftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftLiftMotor = hwMap.get(DcMotorEx.class, "left_lift_motor");
       // leftLiftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftLiftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        leftLiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftLiftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    @Override
    public List<QQTest> getTests() {
        return Arrays.asList(
                new TestTwoMotor(rightLiftMotor, leftLiftMotor, "lift_up", 0.1),
                new TestTwoMotor(rightLiftMotor, leftLiftMotor, "lift_down", -0.1)

        );

    }

    public void stopMotor() {
        rightLiftMotor.setPower(0);
        leftLiftMotor.setPower(0);
    }

    public double getRightLiftPosition() {
        return rightLiftMotor.getCurrentPosition();
    }

    public double getLeftLiftPosition() {
        return leftLiftMotor.getCurrentPosition();
    }

    public boolean isSafe(){
        return getLiftPosition() > GROUND_POSITION; //checks if lift is higher than ground position
    }


    public void goTo(Level level) {
        switch (level) {
            case INTAKE:
                desiredPosition = INTAKE_POSITION;
                break;
            case GROUND:
                desiredPosition = GROUND_POSITION;
                break;
            case LOW:
                desiredPosition = LOW_POSITION;
                break;
            case MIDDLE:
                desiredPosition = MIDDLE_POSITION;
                break;
            case HIGH:
                desiredPosition = HIGH_POSITION;
                break;
        }
    }

    public void adjustPosition(int change) {
        desiredPosition = Range.clip(desiredPosition + change, SLIDES_MIN, SLIDES_MAX);
    }

    public void update() {
        //right motor
        int errorRight = desiredPosition - rightLiftMotor.getCurrentPosition();
        double powerRight = (errorRight * PROPORTIONAL_CONSTANT) +
                GRAVITY_CONSTANT;

        powerRight = Range.clip(powerRight, -MAX_LIFT_SPEED_DOWN, MAX_LIFT_SPEED_UP);
        rightLiftMotor.setPower(powerRight);

        //left motor
        int errorLeft = desiredPosition - leftLiftMotor.getCurrentPosition();
        double powerLeft = (errorLeft * PROPORTIONAL_CONSTANT) +
                GRAVITY_CONSTANT;

        powerLeft = Range.clip(powerLeft, -MAX_LIFT_SPEED_DOWN, MAX_LIFT_SPEED_UP);
        leftLiftMotor.setPower(powerLeft);
    }

}
