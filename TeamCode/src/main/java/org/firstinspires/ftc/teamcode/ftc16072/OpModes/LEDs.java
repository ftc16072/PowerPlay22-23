package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.EndGameLight;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.Lift;


@TeleOp()
public class LEDs extends QQOpMode {

    @Override
    public void loop() {
        if (gamepad1.a){
            robot.endGameLight.setColor(EndGameLight.Color.GREEN);
            telemetry.addData("led", "green");
        }else if (gamepad1.b){
            robot.endGameLight.setColor(EndGameLight.Color.RED);
            telemetry.addData("led", "red");

        }else if (gamepad1.y){
            robot.endGameLight.setColor(EndGameLight.Color.YELLOW);
            telemetry.addData("led", "yellow");
        }else if (gamepad1.x){
            robot.endGameLight.setColor(EndGameLight.Color.NONE);
            telemetry.addData("led", "none");
        }

    }
}
