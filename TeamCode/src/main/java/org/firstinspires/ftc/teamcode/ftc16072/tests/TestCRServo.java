package org.firstinspires.ftc.teamcode.ftc16072.tests;

import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TestCRServo extends QQTest{
    CRServo servo;
    double speed;

    public TestCRServo(CRServo servo, String description, double speed) {
        super(description);
        this.speed = speed;
        this.servo = servo;
    }
    @Override
    public void run(boolean on, Telemetry telemetry) {
        if (on) {
            servo.setPower(speed);
        }
        else{
            servo.setPower(0.0);
        }
    }
}
