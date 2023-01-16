package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationMecanum;

//@TeleOp()
public class DriveOnly extends QQOpMode {
    NavigationMecanum nav = new NavigationMecanum(robot);


    @Override
    public void loop() {
        nav.driveFieldRelative(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        if (gamepad1.a) {
            robot.mecanumDrive.setEncodeOffsets();
        }

        double[] distances = robot.mecanumDrive.getDistanceCM();
        telemetry.addData("Distance driven forward", distances[0]);
        telemetry.addData("distance strafed", distances[1]);

    }
}
