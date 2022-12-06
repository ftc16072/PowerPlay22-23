package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.ftc16072.Robot;
import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationMecanum;

public abstract class QQOpMode extends OpMode {
    public Robot robot = new Robot();
    public NavigationMecanum nav = new NavigationMecanum(robot);


    @Override
    public void init() {
        robot.init(hardwareMap);
    }
}
