package org.firstinspires.ftc.teamcode.ftc16072.actions;

import org.firstinspires.ftc.teamcode.ftc16072.OpModes.QQOpMode;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.HorizontalSlides;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.Lift;

public class ReturnIntakeAction extends QQAction{
    @Override
    public QQAction run(QQOpMode opMode) {

        opMode.robot.horizontalSlides.goTo(HorizontalSlides.Position.BACK);
        opMode.robot.lift.goTo(Lift.Level.INTAKE);
        return nextAction;
    }
    public ReturnIntakeAction(){

    }
}
