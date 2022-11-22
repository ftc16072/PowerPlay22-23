package org.firstinspires.ftc.teamcode.ftc16072.tests;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TestTwoMotor extends QQTest {
    DcMotor motor1;
    DcMotor motor2;
    double speed;

    public TestTwoMotor(DcMotor motor1, DcMotor motor2, String description, double speed) {
        super(description);
        this.speed = speed;
        this.motor1 = motor1;
        this.motor2 = motor2;
    }

    @Override
    public void run(boolean on, Telemetry telemetry) {
        if (on) {
            motor1.setPower(speed);
            motor2.setPower(speed);
        } else {
            motor1.setPower(0.0);
            motor2.setPower(0.0);
        }
        telemetry.addData("Encoder", motor1.getCurrentPosition()+" "+motor2.getCurrentPosition());
    }
}
