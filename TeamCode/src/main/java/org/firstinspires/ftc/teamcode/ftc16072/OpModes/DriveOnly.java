package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationMecanum;

@TeleOp()
public class DriveOnly extends QQOpMode {
    NavigationMecanum nav = new NavigationMecanum(robot);


    @Override
    public void loop() {
        double FULL_SPEED = 0.25;
        if (gamepad1.left_bumper){
            FULL_SPEED = 0.5;
        }
        if (gamepad1.right_bumper){
            FULL_SPEED= 1.0;
        }
        nav.driveFieldRelative(-gamepad1.left_stick_y * FULL_SPEED, gamepad1.left_stick_x * FULL_SPEED,
                gamepad1.right_stick_x * FULL_SPEED);
        if (gamepad1.a) {
            robot.mecanumDrive.setEncodeOffsets();
        }

        double[] distances = robot.mecanumDrive.getDistanceCM();
        telemetry.addData("Distance driven forward", distances[0]);
        telemetry.addData("distance strafed", distances[1]);

    }
}
