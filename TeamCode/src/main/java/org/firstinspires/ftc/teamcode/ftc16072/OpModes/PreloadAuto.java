package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.ftc16072.actions.ConditionalAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveToAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DualAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.GripClaw;
import org.firstinspires.ftc.teamcode.ftc16072.actions.LiftHigh;
import org.firstinspires.ftc.teamcode.ftc16072.actions.QQAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.ReleaseClaw;
import org.firstinspires.ftc.teamcode.ftc16072.actions.RotateAction;
import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationPose;

import java.util.Arrays;

@Autonomous
public class PreloadAuto extends VisionAutoBase {
    QQAction currentAction;

    //parking
    //0,0 is bottom left, field relative
    QQAction right1 = new DriveToAction("left", new NavigationPose(84, 12, 0))
            .setNext(new DriveToAction("forward", new NavigationPose(84, 48)));
    QQAction right2 = new DriveToAction("forward", new NavigationPose(108, 48, 0));
    QQAction right3 = new DriveToAction("left", new NavigationPose(132, 12, 0))
            .setNext(new DriveToAction("forward", new NavigationPose(132, 48)));
    QQAction right = new ConditionalAction("drive to parking zone", Arrays.asList(right1, right2, right3), () -> parkingZone - 1);

    QQAction left1 = new DriveToAction("left", new NavigationPose(12, 12, 0))
            .setNext(new DriveToAction("forward", new NavigationPose(12, 48)));
    QQAction left2 = new DriveToAction("forward", new NavigationPose(36, 48));
    QQAction left3 = new DriveToAction("right", new NavigationPose(60, 12))
            .setNext(new DriveToAction("forward", new NavigationPose(60, 48)));
    QQAction left = new ConditionalAction("drive to parking zone", Arrays.asList(left1, left2, left3), () -> parkingZone - 1);

    //primary and secondary goal positions
    QQAction primaryLeft = new DriveToAction("left primary goal", new NavigationPose(36, 72,0))
            .setNext(new RotateAction(90, AngleUnit.DEGREES));
    QQAction primaryRight = new DriveToAction("right primary goal", new NavigationPose(108, 72, 0))
            .setNext(new RotateAction(-90, AngleUnit.DEGREES));
    QQAction primary = new ConditionalAction("primary goal", Arrays.asList(primaryLeft, primaryRight), () -> isLeft ? 0 : 1);

    QQAction secondaryLeft = new DriveToAction("left secondary goal", new NavigationPose(60, 12,0))
            .setNext(new DriveToAction("forward", new NavigationPose(60, 48)))
            .setNext(new RotateAction(90, AngleUnit.DEGREES));
    QQAction secondaryRight = new DriveToAction("right secondary goal", new NavigationPose(84, 12,0))
            .setNext(new DriveToAction("forward", new NavigationPose(84, 48)))
            .setNext(new RotateAction(-90, AngleUnit.DEGREES));
    QQAction secondary = new ConditionalAction("secondary goal", Arrays.asList(secondaryLeft, secondaryRight), () -> isLeft ? 0 : 1);

    @Override
    public void start() {
        currentAction = new GripClaw()
                .setNext(new DualAction("lift and drive to goal", new LiftHigh(),
                        new ConditionalAction("drive to goal", Arrays.asList(primary, secondary), () -> isPrimary ? 0 : 1)))
                .setNext(new ReleaseClaw())
                .setNext(new ConditionalAction("park", Arrays.asList(left, right), () -> isLeft ? 0 : 1));
    }

    @Override
    public void loop() {
        if (currentAction != null) {
            currentAction = currentAction.run(this);
        }
    }
}