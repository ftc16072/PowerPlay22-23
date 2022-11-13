package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.Lift;


@TeleOp()
public class TestHorizontal extends QQOpMode {

    @Override
    public void loop() {
        robot.horizontalSlides.goTo(gamepad1.left_stick_x);
    }
}
