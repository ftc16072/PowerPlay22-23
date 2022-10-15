package org.firstinspires.ftc.teamcode.ftc16072.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.ftc16072.tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.tests.TestMotor;
import org.firstinspires.ftc.teamcode.ftc16072.tests.TestServo;

import java.util.Arrays;
import java.util.List;


public class Intake extends Mechanism {

//creates a Dcmotor objectfinis
    private DcMotorEx intakeLeft;
    private DcMotorEx intakeRight;

    //creates different intake states in an enum
    private enum Which{
        LEFT,
        RIGHT,
        BOTH
    }


    @Override
    public void init(HardwareMap hwMap) {
        intakeLeft = hwMap.get(DcMotorEx.class, "left_intake");
        intakeRight = hwMap.get(DcMotorEx.class, "right_intake");
    }




    @Override
    public List<QQTest> getTests() {
        return Arrays.asList(
                new TestMotor(intakeLeft, "left_intake", 0.25),
                new TestMotor(intakeRight, "right_intake", 0.25)
        );
    }
//sets the power for the different states of the intake
    public void intake(Which which){
        switch (which){
            case BOTH:
            default:
                intakeLeft.setPower(1);
                intakeRight.setPower(1);
                break;
            case LEFT:
                intakeLeft.setPower(1);
                break;
            case RIGHT:
                intakeRight.setPower(1);
                break;
        }
    }
    public void off(Which which){
        switch(which){
            case BOTH:
            default:
                intakeLeft.setPower(0);
                intakeRight.setPower(0);
                break;
            case LEFT:
                intakeLeft.setPower(0);
                break;
            case RIGHT:
                intakeRight.setPower(0);
                break;

        }
    }
}
