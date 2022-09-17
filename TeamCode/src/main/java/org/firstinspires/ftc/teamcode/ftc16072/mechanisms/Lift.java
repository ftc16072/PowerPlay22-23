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
public class Lift extends Mechanism{
    private DcMotorEx liftMotor;
    private double GROUNDPOSITION = 0.2;
    private double INTAKEPOSITION = 0.1;
    private double LOWPOSITION = 0.4;
    private double MIDDLEPOSITION = 0.6;
    private double HIGHPOSITION = 0.8;

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
    public void stopMotor(){
        liftMotor.setPower(0);
    }
    public double getLiftPosition(){
        return liftMotor.getCurrentPosition();
    }

    public void goToIntake(double power){

    }
    public void goToGround(double power){

    }
    public void goToMiddle(double power){

    }
    public void goToHigh(double power){

    }

}
