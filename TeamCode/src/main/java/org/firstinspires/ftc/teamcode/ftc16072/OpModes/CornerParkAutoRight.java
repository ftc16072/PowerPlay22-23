package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveForwardAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveRightAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.QQAction;

@Autonomous
public class CornerParkAutoRight extends QQOpMode{
    QQAction currentAction;
    @Override
    public void start() {
        currentAction = new DriveForwardAction(2, DistanceUnit.INCH);
        currentAction.setLast(new DriveRightAction(22, DistanceUnit.INCH));

    }

    @Override
    public void loop() {
        if (currentAction != null) {
            currentAction = currentAction.run(this);
        }
    }
}
