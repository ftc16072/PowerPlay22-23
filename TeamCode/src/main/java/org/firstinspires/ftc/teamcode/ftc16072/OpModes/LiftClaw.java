package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationMecanum;

@TeleOp()
public class LiftClaw extends QQOpMode {

    @Override
    public void loop() {
        if (gamepad1.a){
            robot.claw.grip();
        }else if (gamepad1.b){
            robot.claw.release();
        }


    }
}
