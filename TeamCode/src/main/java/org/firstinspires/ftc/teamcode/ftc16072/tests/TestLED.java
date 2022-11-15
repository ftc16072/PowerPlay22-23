package org.firstinspires.ftc.teamcode.ftc16072.tests;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TestLED extends QQTest {
    DigitalChannel led;

    public TestLED(DigitalChannel led, String description) {
        super(description);
        this.led = led;

    }

    @Override
    public void run(boolean on, Telemetry telemetry) {
        led.setState(!on);
    }
}
