package org.firstinspires.ftc.teamcode.ftc16072.actions;

import org.firstinspires.ftc.teamcode.ftc16072.OpModes.QQOpMode;

public class LoopAction extends QQAction {
    QQAction action;
    int loops;
    int finished = 0;

    public LoopAction (String description, QQAction action, int loops) {
        super(description);
        this.action = action;
        this.loops = loops;
    }

    @Override
    public QQAction run(QQOpMode opmode) {
        action = action.run(opmode);
        if(action == null){
            if(finished == loops){
                return this.nextAction;
            } else {
                finished++;
            }
        }
        return this;
    }
}
