package org.firstinspires.ftc.teamcode.ftc16072.tests;

import com.qualcomm.robotcore.hardware.ColorRangeSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class TestColorSensor extends QQTest {
    ColorRangeSensor colorSensor;

    public TestColorSensor(ColorRangeSensor colorSensor, String description) {
        super(description);
        this.colorSensor = colorSensor;
    }

    @Override
    public void run(boolean on, Telemetry telemetry) {
        telemetry.addData("Color R", colorSensor.red());
        telemetry.addData("Color G", colorSensor.green());
        telemetry.addData("Color B", colorSensor.blue());
        telemetry.addData("Distance (in.)", colorSensor.getDistance(DistanceUnit.INCH));
    }
}
