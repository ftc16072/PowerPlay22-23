package org.firstinspires.ftc.teamcode.ftc16072.actions;

import org.firstinspires.ftc.teamcode.ftc16072.OpModes.QQOpMode;

public abstract class QQAction {
    String description;
    QQAction nextAction;

    public QQAction(String description) {
        this.description = description;
    }

    public QQAction() {
        this(Class.class.getSimpleName());
    }

    public abstract QQAction run(QQOpMode opMode);

    public QQAction setLast(QQAction nextAction) {
        if (this.nextAction == null) {
            this.nextAction = nextAction;
        } else {
            this.nextAction.setLast(nextAction);
        }
        return this;
    }

    public String getDescription() {
        return description;
    }

}
