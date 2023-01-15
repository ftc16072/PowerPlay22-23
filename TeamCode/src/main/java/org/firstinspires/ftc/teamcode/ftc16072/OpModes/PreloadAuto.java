package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveBackwardAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveForwardAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveLeftAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveToAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DualAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.GripClaw;
import org.firstinspires.ftc.teamcode.ftc16072.actions.LiftHigh;
import org.firstinspires.ftc.teamcode.ftc16072.actions.QQAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.ReleaseClaw;
import org.firstinspires.ftc.teamcode.ftc16072.actions.delayAction;
import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationPose;
import org.firstinspires.ftc.teamcode.ftc16072.util.RobotPose;

@Autonomous
public class PreloadAuto extends VisionAutoBase {


    //parking
    private QQAction getZone() {

        if (isLeft) {
            if (isPrimary) {
                if (parkingZone == 1) {
                    return new DriveToAction("rotate", new NavigationPose(-12, 48, 0))
                            .setNext(new DriveToAction("1L - left", new NavigationPose(-12, 36, 0))
                                    .setNext(new DriveToAction("1L - forward", new NavigationPose(-60, 36))));
                } else if (parkingZone == 2) {
                    return new DriveToAction("rotate", new NavigationPose(-12, 48, 0))
                            .setNext(new DriveToAction("2LP - forward", new NavigationPose(-12,60))
                            .setNext(new DriveToAction("2L - forward", new NavigationPose(-36, 60))));
                } else {
                    return new DriveToAction("rotate", new NavigationPose(-12, 48, 0));
                }
            } else {
                if (parkingZone == 1) {
                    return new DriveToAction("rotate", new NavigationPose(-36, 72, 0))
                            .setNext(new DriveToAction("1L - left", new NavigationPose(-12, 36, 0))
                                    .setNext(new DriveToAction("1L - forward", new NavigationPose(-60, 36))));
                } else if (parkingZone == 2) {
                    return new DriveToAction("rotate", new NavigationPose(-36, 72, 0))
                            .setNext(new DriveToAction("2L - forward", new NavigationPose(-36, 36)));
                } else {
                    return new DriveToAction("rotate", new NavigationPose(-36, 72, 0))
                            .setNext(new DriveToAction("3L - right", new NavigationPose(-12, 10))
                                    .setLast(new DriveToAction("3L - forward", new NavigationPose(-12, 36))));
                }
            }
        }
        if (isPrimary) {
            if (parkingZone == 1) {
                return new DriveToAction("1R- left", new NavigationPose(12, 10, 0))
                        .setNext(new DriveToAction("1R - forward", new NavigationPose(12, 36)));
            } else if (parkingZone == 2) {
                return new DriveToAction("2R - forward", new NavigationPose(36, 36, 0));
            } else {
                return new DriveToAction("3R - right", new NavigationPose(60, 10, 0))
                        .setNext(new DriveToAction("3R - forward", new NavigationPose(60, 36)));
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
                return new DriveToAction("PL - right", new NavigationPose(-12, 10, 0))
                        .setNext(new DriveToAction("PL - forward", new NavigationPose(-12, 48)))
                        .setLast(new DriveToAction("rotate -90", new NavigationPose(-12, 48, -90)));
            } else {
                return new DriveToAction("LS - forward", new NavigationPose(-36, 72, 0))
                        .setLast(new DriveToAction("rotate -90", new NavigationPose(-36, 72, -90)));
            }
        }
        if (isPrimary) {
            return new DriveToAction("RP - forward", new NavigationPose(36, 72, 0))
                    .setLast(new DriveToAction("rotate 90", new NavigationPose(36, 72, 90)));
        }
        return new DriveToAction("RS - left", new NavigationPose(12, 10, 0))
                .setNext(new DriveToAction("RS - forward", new NavigationPose(12, 48)))
                .setLast(new DriveToAction("rotate 90", new NavigationPose(12, 48, 90)));
    }


    @Override
    public void start() {
        super.start();
        //currentAction = new DualAction("lift and drive to goal", new LiftHigh(), getGoal());
        currentAction = new GripClaw()
                .setNext(new delayAction(0.5)
                        .setNext(new DualAction("lift and drive to goal", new LiftHigh(), getGoal())
                                .setNext(new ReleaseClaw()
                                        .setNext(getZone()))));
    }

}