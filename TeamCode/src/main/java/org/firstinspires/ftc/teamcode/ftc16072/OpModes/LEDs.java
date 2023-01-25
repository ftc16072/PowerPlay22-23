package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp()
public class LEDs extends QQOpMode {

    @Override
    public void loop() {
        if (gamepad1.a){
            robot.lights.turn_off();
            telemetry.addData("led", "green");
        }else if (gamepad1.b){
            robot.lights.turn_purple();
            telemetry.addData("led", "red");

        }else if (gamepad1.y){
            robot.lights.turn_yellow();
            telemetry.addData("led", "yellow");
        }else if (gamepad1.x){
            robot.lights.flash_purple();

            telemetry.addData("led", "none");
        }

    }
}
