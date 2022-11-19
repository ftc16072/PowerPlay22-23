package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveBackwardAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveForwardAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveLeftAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveRightAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.PlaceMiddleAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.QQAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.ReturnIntakeAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.RotateAction;

@Autonomous
public class MidCycleAuto extends VisionAutoBase {
    //NavigationMecanum nav = new NavigationMecanum(robot);
    QQAction currentAction;


    @Override
    public void start() {
        int parkingZone = 0;
        telemetry.addData("Parking Zone: ", parkingZone);
        currentAction = new DriveForwardAction(24, DistanceUnit.INCH);
        currentAction.setNext(new RotateAction("-30",-30,AngleUnit.DEGREES));
        currentAction.setNext(new PlaceMiddleAction());
        currentAction.setNext(new ReturnIntakeAction());

        currentAction.setNext(new RotateAction("-30",-30, AngleUnit.DEGREES));
        currentAction.setNext(new DriveBackwardAction(24,DistanceUnit.INCH));

        if (parkingZone == 3) {
            currentAction.setNext(new DriveForwardAction(24, DistanceUnit.INCH));
            currentAction.setLast(new DriveLeftAction(36, DistanceUnit.INCH)); // goes to 1st parking zone
        } else if (parkingZone == 2) {
            currentAction = new DriveLeftAction(36, DistanceUnit.INCH); // goes to 2nd parking zone

        } else if (parkingZone == 1) {
            currentAction.setNext(new DriveForwardAction(24, DistanceUnit.INCH));
            currentAction.setLast(new DriveLeftAction(36, DistanceUnit.INCH)); // goes to 3rd parking zone

        } else {
            currentAction = new DriveRightAction(36, DistanceUnit.INCH); // goes to 1st parking zone
        }
    }


    @Override
    public void loop() {
        if (currentAction != null) {
            currentAction = currentAction.run(this);
        }
    }
}