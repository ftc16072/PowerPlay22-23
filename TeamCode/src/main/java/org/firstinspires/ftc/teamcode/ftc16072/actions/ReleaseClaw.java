package org.firstinspires.ftc.teamcode.ftc16072.actions;

import org.firstinspires.ftc.teamcode.ftc16072.OpModes.QQOpMode;

public class ReleaseClaw extends QQAction{
    @Override
    public QQAction run(QQOpMode opMode) {
        opMode.robot.claw.release();
        return nextAction;
    }

}