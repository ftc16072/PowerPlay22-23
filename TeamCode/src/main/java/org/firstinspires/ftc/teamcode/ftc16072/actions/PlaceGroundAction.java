package org.firstinspires.ftc.teamcode.ftc16072.actions;

import org.firstinspires.ftc.teamcode.ftc16072.OpModes.QQOpMode;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.HorizontalSlides;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.Lift;

public class PlaceGroundAction extends QQAction{
    @Override
    public QQAction run(QQOpMode opMode) {
        opMode.robot.lift.goTo(Lift.Level.GROUND);
        opMode.robot.horizontalSlides.goToPosition(HorizontalSlides.Position.FRONT);
        opMode.robot.claw.release();
        return nextAction;
    }
    public PlaceGroundAction(){

    }
}
