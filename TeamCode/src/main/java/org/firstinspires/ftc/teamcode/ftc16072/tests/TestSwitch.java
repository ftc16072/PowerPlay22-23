package org.firstinspires.ftc.teamcode.ftc16072.tests;

import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TestSwitch extends QQTest {
    DigitalChannel digitalChannel;

    public TestSwitch(DigitalChannel digitalChannel, String description){
        super(description);
        this.digitalChannel = digitalChannel;
    }

    @Override
    public void run(boolean on, Telemetry telemetry) {
        telemetry.addData("switch", !digitalChannel.getState());
    }
}
