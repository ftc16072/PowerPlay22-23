package org.firstinspires.ftc.teamcode.ftc16072.tests;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TestMotor extends QQTest{
    DcMotor motor;
    double speed;

    public TestMotor(DcMotor motor, String description, double speed) {
        super(description);
        this.speed = speed;
        this.motor = motor;
    }
    @Override
    public void run(boolean on, Telemetry telemetry) {
        if (on) {
            motor.setPower(speed);
        }
        else{
            motor.setPower(0.0);
        }
        telemetry.addData("Encoder",motor.getCurrentPosition());
    }
}
