package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveToAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.QQAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.RotateAction;
import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationPose;

@Autonomous
public class PreloadAuto extends VisionAutoBase {


    //parking
    private QQAction getZone() {
        if (isLeft) {
            if (parkingZone == 1) {
                return new DriveToAction("1L - left", new NavigationPose(-60, 10, 0))
                        .setNext(new DriveToAction("1L - forward", new NavigationPose(-60, 36)));
            } else if (parkingZone == 2) {
                return new DriveToAction("2L - forward", new NavigationPose(-36, 36));
            } else {
                return new DriveToAction("3L - right", new NavigationPose(-12, 10))
                        .setLast(new DriveToAction("3L - forward", new NavigationPose(-12, 36)));
            }
        }
        if (parkingZone == 1) {
            return new DriveToAction("1R- left", new NavigationPose(12, 10, 0))
                    .setNext(new DriveToAction("1R - forward", new NavigationPose(12, 36)));
        } else if (parkingZone == 2) {
            return new DriveToAction("2R - forward", new NavigationPose(36, 36, 0));
        }
        return new DriveToAction("3R - right", new NavigationPose(60, 10, 0))
                .setNext(new DriveToAction("3R - forward", new NavigationPose(60, 36)));
    }

    //primary and secondary goal positions
    private QQAction getGoal() {
        if (isLeft) {
            if (isPrimary) {
                return new DriveToAction("PL - right", new NavigationPose(-12, 10))
                        .setNext(new DriveToAction("PL - forward", new NavigationPose(-12, 48)))
                        .setNext(new RotateAction(-90, AngleUnit.DEGREES));
            } else {
                return new DriveToAction("LS - forward", new NavigationPose(-36, 72, 0))
                        .setNext(new RotateAction(-90, AngleUnit.DEGREES));
            }
        }
        if (isPrimary) {
            return new DriveToAction("RP - forward", new NavigationPose(36, 72, 0))
                    .setNext(new RotateAction(90, AngleUnit.DEGREES));
        }
        return new DriveToAction("RS - left", new NavigationPose(12, 10, 0))
                .setNext(new DriveToAction("RS - forward", new NavigationPose(12, 48)))
                .setLast(new RotateAction(90, AngleUnit.DEGREES));
    }


    /*
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
    QQAction secondary = new ConditionalAction("secondary goal", Arrays.asList(secondaryLeft, secondaryRight), () -> isLeft ? 0 : 1);*/

    @Override
    public void start() {
        super.start();
        currentAction = getGoal();
        /*currentAction = new GripClaw()
                .setNext(new DualAction("lift and drive to goal", new LiftHigh(), getGoal()))
                .setNext(new ReleaseClaw())
                .setLast(getZone());*/
    }


}