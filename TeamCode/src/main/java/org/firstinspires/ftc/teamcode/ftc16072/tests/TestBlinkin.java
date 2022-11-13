package org.firstinspires.ftc.teamcode.ftc16072.tests;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TestBlinkin extends QQTest {
    RevBlinkinLedDriver lights;
    RevBlinkinLedDriver.BlinkinPattern onPattern;
    RevBlinkinLedDriver.BlinkinPattern offPattern;

    public TestBlinkin(RevBlinkinLedDriver lights, String description, RevBlinkinLedDriver.BlinkinPattern onPattern, RevBlinkinLedDriver.BlinkinPattern offPattern) {
        super(description);
        this.onPattern = onPattern;
        this.offPattern = offPattern;
        this.lights = lights;
    }

    @Override
    public void run(boolean on, Telemetry telemetry) {
        if (on) {
            lights.setPattern(onPattern);
        } else {
            lights.setPattern(offPattern);
        }
    }
}
