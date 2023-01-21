package org.firstinspires.ftc.teamcode.ftc16072.actions;

import org.firstinspires.ftc.teamcode.ftc16072.OpModes.QQOpMode;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.HorizontalSlides.Position;

public class HorizontalSlides extends QQAction{

    Position level;

    public HorizontalSlides(String description, Position level){
        super(description);
        this.level = level;
    }

    @Override
    public QQAction run(QQOpMode opMode) {
        opMode.robot.horizontalSlides.goToPosition(this.level);

        return nextAction;
    }

}