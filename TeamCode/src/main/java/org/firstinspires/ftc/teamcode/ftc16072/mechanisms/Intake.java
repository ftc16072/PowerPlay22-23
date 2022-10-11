package org.firstinspires.ftc.teamcode.ftc16072.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.ftc16072.tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.tests.TestServo;

import java.util.Arrays;
import java.util.List;


public class Intake extends Mechanism {


    private DcMotor intakeLeft;
    private DcMotor intakeRight;




    @Override
    public void init(HardwareMap hwMap) {

/*


    @Override
    public List<QQTest> getTests() {
        return Arrays.asList(
                new TestServo(clawServo, "claw", GRIPPED_SERVO_POSITION, RELEASED_SERVO_POSITION)
                //          new TestColorSensor(coneDetector, "cone_detector")
        );
    }



}
