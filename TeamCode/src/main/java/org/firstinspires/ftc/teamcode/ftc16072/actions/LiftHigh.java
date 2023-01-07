package org.firstinspires.ftc.teamcode.ftc16072.actions;

import org.firstinspires.ftc.teamcode.ftc16072.OpModes.QQOpMode;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.HorizontalSlides;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.Lift;

public class LiftHigh extends QQAction{
    @Override
    public QQAction run(QQOpMode opMode) {
        opMode.robot.lift.goTo(Lift.Level.HIGH);
        opMode.robot.horizontalSlides.goToPosition(HorizontalSlides.Position.FRONT);
        return nextAction;
    }

}