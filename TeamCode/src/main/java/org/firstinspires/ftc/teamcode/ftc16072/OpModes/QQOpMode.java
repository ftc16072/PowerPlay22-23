package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.ftc16072.Robot;

public abstract class QQOpMode extends OpMode {
    public Robot robot = new Robot();
    @Override
    public void init() {
        robot.init(hardwareMap);
    }
}
