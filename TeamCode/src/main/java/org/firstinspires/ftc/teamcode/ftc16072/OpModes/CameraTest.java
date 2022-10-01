package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class CameraTest extends ConeDetection {

    @Override
    public void start() {
        int parkingZone = super.signalSleevePipeline.numberOfDots;
        telemetry.addData("Parking Zone: ", parkingZone);
        super.start();
    }

    @Override
    public void loop() {

    }
}
