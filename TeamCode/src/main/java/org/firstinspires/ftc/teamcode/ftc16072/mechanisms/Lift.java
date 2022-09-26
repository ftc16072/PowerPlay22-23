package org.firstinspires.ftc.teamcode.ftc16072.mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

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
    public static int INTAKE_POSITION = 80;
    public static int LOW_POSITION = 100;
    public static int MIDDLE_POSITION = 600;
    public static int HIGH_POSITION = 2900;
    public static int slidesMin = 0;
    public static int slidesMax = 3000;
    public static double P;
    public static double I;
    public static double D;
    public static double F;
    private PIDFCoefficients liftMotorPIDF;

    //TODO: find the right values and make final
    public DcMotorEx liftMotor;
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
        liftMotorPIDF = liftMotor.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION);
        P = liftMotorPIDF.p;
        D = liftMotorPIDF.d;
        I = liftMotorPIDF.i;
        F = liftMotorPIDF.f;
    }

    @Override
    public List<QQTest> getTests() {
        return Arrays.asList(
                new TestMotor(liftMotor, "lift_motor_up", 0.3),
                new TestMotor(liftMotor, "lift_motor down", -0.3)
        );

    }

    public void stopMotor() {
        liftMotor.setPower(0);
    }

    public double getLiftPosition() {
        return liftMotor.getCurrentPosition();
    }

    public void goTo(Level level) {
        liftMotorPIDF.p = P;
        liftMotorPIDF.i = I;
        liftMotorPIDF.d = D;
        liftMotorPIDF.f = F;
        liftMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION, liftMotorPIDF);
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
            default:
                liftMotor.setTargetPosition(0);
        }
        liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor.setPower(0.5);
    }


    public void extend(double power) {
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        if (canExtend()) {
            liftMotor.setPower(power);
        }
        else{
            liftMotor.setPower(0);
        }
    }

    public void retract(double power) {
        liftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        if (canRetract()) {
            liftMotor.setPower(power);
        }
        else{
            liftMotor.setPower(0);
        }
    }

    public boolean canExtend() {
        return liftMotor.getCurrentPosition() > slidesMin;
    }

    public boolean canRetract() {
        return liftMotor.getCurrentPosition() < slidesMax;
    }

}
