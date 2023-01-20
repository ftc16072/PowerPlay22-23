package org.firstinspires.ftc.teamcode.ftc16072.actions;

import org.firstinspires.ftc.teamcode.ftc16072.OpModes.QQOpMode;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.Lift;

public class ChangeLiftAction extends QQAction{
    Lift.Level liftLevel;

    public ChangeLiftAction(String description, Lift.Level liftLevel){
        super(description);
        this.liftLevel = liftLevel;
    }

    @Override
    public QQAction run(QQOpMode opMode) {
        opMode.robot.lift.goTo(liftLevel);

        if(opMode.robot.lift.isAtLevel(liftLevel)) {
            return nextAction;
        }
        return this;
    }

}