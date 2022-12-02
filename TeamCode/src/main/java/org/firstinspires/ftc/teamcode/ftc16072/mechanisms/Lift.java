package org.firstinspires.ftc.teamcode.ftc16072.mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.ftc16072.tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.tests.TestMotor;

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
    public DcMotorEx liftMotor;
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
        liftMotor = hwMap.get(DcMotorEx.class, "lift_motor");
        liftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        liftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    @Override
    public List<QQTest> getTests() {
        return Arrays.asList(
                new TestMotor(liftMotor, "lift_motor_up", 0.3),
                new TestMotor(liftMotor, "lift_motor down", -0.3)
        );

    }

    public void stopMotor() {
        liftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        liftMotor.setPower(0);
    }

    public double getLiftPosition() {
        return liftMotor.getCurrentPosition();
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

    public void update(){
        int error = desiredPosition - liftMotor.getCurrentPosition();
        double power = (error * PROPORTIONAL_CONSTANT) +
                GRAVITY_CONSTANT;

        power = Range.clip(power, -MAX_LIFT_SPEED_DOWN, MAX_LIFT_SPEED_UP);
        liftMotor.setPower(power);
    }

}
