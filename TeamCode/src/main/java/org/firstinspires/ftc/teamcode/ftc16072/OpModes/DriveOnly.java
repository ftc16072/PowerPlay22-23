package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.ftc16072.Robot;
import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationMecanum;

@TeleOp()
public class DriveOnly extends OpMode {
    Robot robot = new Robot();
    NavigationMecanum nav = new NavigationMecanum(robot);


    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {
        nav.driveFieldRelative(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);

    }
}
