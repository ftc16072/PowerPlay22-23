package org.firstinspires.ftc.teamcode.ftc16072.tests;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TestServo extends QQTest {
    Servo servo;
    double onPosition;
    double offPosition;

    public TestServo(Servo servo, String description, double onPosition, double offPosition) {
        super(description);
        this.servo = servo;
        this.onPosition = onPosition;
        this.offPosition = offPosition;
    }

    @Override
    public void run(boolean on, Telemetry telemetry) {
        double position;
        if (on) {
            position = onPosition;
        } else {
            position = offPosition;
        }
        servo.setPosition(position);
        telemetry.addData(getDescription(), position);
    }
}
