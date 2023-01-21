package org.firstinspires.ftc.teamcode.ftc16072.actions;

import org.firstinspires.ftc.teamcode.ftc16072.OpModes.QQOpMode;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.Lift;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.LiftAuto;

public class ChangeLiftAction extends QQAction{
    LiftAuto.Level liftLevel;

    public ChangeLiftAction(String description, LiftAuto.Level liftLevel){
        super(description);
        this.liftLevel = liftLevel;
    }

    @Override
    public QQAction run(QQOpMode opMode) {
        opMode.robot.liftAuto.goTo(liftLevel);

        if(opMode.robot.liftAuto.isAtLevel(liftLevel)) {
            return nextAction;
        }
        return this;
    }

}