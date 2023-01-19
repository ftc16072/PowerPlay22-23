package org.firstinspires.ftc.teamcode.ftc16072.actions;

import org.firstinspires.ftc.teamcode.ftc16072.OpModes.QQOpMode;

public class DualAction extends QQAction{
    QQAction a1;
    QQAction a2;
    public DualAction(String description, QQAction action1, QQAction action2) {
        super(description);
        a1 = action1;
        a2 = action2;
    }
    @Override
    public QQAction run(QQOpMode opMode) {
        if(a1!=null) {
            a1 = a1.run(opMode);
        }
        if(a2!=null) {
            a2 = a2.run(opMode);
        }
        if (a1 == null && a2 == null) {

            return nextAction;
        } else {
            return this;
        }
    }
    /*every time through the loop
we execute the run command
and when its done
it returns the next action
and the run command cannot contain a loop
so when we do a.run
and b.run
it does a single step of action a
and a single step of action b
then it checks if they are both done
if it is then it moves on
otherwise it signals that it needs to be run again by returning itself*/
}
