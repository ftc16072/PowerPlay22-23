package org.firstinspires.ftc.teamcode.ftc16072.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

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
public class Lift extends Mechanism {
    private DcMotorEx liftMotor;
    public static int GROUND_POSITION = 200;
    public static int INTAKE_POSITION = 100;
    public static int LOW_POSITION = 100;
    public static int MIDDLE_POSITION = 600;
    public static int HIGH_POSITION = 800;
    public static int slidesMin = 0;
    public static int slidesMax = -3000;
    //TODO: find the right values and make final
    public Level state = Level.INTAKE;

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
        liftMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public List<QQTest> getTests() {
        return Arrays.asList(
                new TestMotor(liftMotor, "lift_motor", 0.3)
        );

    }

    public void stopMotor() {
        liftMotor.setPower(0);
    }

    public double getLiftPosition() {
        return liftMotor.getCurrentPosition();
    }

    public void goTo(Level level) {
        switch (level) {
            case INTAKE:
                liftMotor.setTargetPosition(INTAKE_POSITION);
                break;
            case GROUND:
                liftMotor.setTargetPosition(GROUND_POSITION);
                break;
            case LOW:
                liftMotor.setTargetPosition(LOW_POSITION);
                break;
            case MIDDLE:
                liftMotor.setTargetPosition(MIDDLE_POSITION);
                break;
            case HIGH:
                liftMotor.setTargetPosition(HIGH_POSITION);
                break;
        }
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }


    public void extend(double power) {
        if (canExtend()) {
            liftMotor.setPower(power);
        }
    }

    public void retract(double power) {
        if (canRetract()) {
            liftMotor.setPower(power);
        }
    }

    public boolean canExtend() {
        return liftMotor.getCurrentPosition() < slidesMin;
    }

    public boolean canRetract() {
        return liftMotor.getCurrentPosition() >= slidesMax;
    }

}
