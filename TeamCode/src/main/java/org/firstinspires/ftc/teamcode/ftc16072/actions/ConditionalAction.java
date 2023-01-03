package org.firstinspires.ftc.teamcode.ftc16072.actions;

import org.firstinspires.ftc.teamcode.ftc16072.OpModes.QQOpMode;

import java.util.ArrayList;
import java.util.function.IntSupplier;
import java.util.HashSet;

public class ConditionalAction extends QQAction{
    ArrayList<QQAction> actionChoices;
    IntSupplier getChoice;
    public ConditionalAction(String description, ArrayList<QQAction> actionChoices, IntSupplier getChoice){
        super(description);
        this.actionChoices = actionChoices;
        this.getChoice = getChoice;
    }
    //to use: ConditionalAction a = new ConditionalAction("test", new ArrayList<QQAction>(Arrays.asList(new DriveRightAction(2.0, DistanceUnit.CM))), ()->{return 0;});
    @Override
    public QQAction run(QQOpMode opMode) {
        return actionChoices.get(getChoice.getAsInt());
    }
}
