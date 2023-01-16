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
//need to make a new method called getCone that goes to pick up a cone and then park
        if (isLeft) {
            if (isPrimary) {
                //left, primary, parking zones 1-3
                if (parkingZone == 1) {
                    return new DriveToAction("1LP - rotate", new NavigationPose(-12, 48, 0))
                            .setNext(new DriveToAction("1LP - left", new NavigationPose(-12, 60, 0))
                                    .setNext(new DriveToAction("1LP - forward", new NavigationPose(-60, 55, 0))
                                            .setNext(new DriveToAction("1LP - forward", new NavigationPose(-60, 55, 90)))));
                } else if (parkingZone == 2) {
                    return new DriveToAction("2LP - rotate", new NavigationPose(-12, 48, 0))
                            .setNext(new DriveToAction("2LP - forward", new NavigationPose(-12, 60, 0))
                                    .setNext(new DriveToAction("1LP - forward", new NavigationPose(-60, 55, 0))
                                            .setNext(new DriveToAction("1LP - forward", new NavigationPose(-60, 55, 90))
                                                    .setNext(new DriveToAction("1LP - forward", new NavigationPose(-60, 55, 0))
                                                            .setNext(new DriveToAction("2LP - forward", new NavigationPose(-36, 55)))))));
                } else {
                    return new DriveToAction("3LP - rotate", new NavigationPose(-12, 48, 0))
                            .setNext(new DriveToAction("3LP", new NavigationPose(-12, 60))
                                    .setNext(new DriveToAction("3LP", new NavigationPose(-60, 55, 0))
                                            .setNext(new DriveToAction("3LP", new NavigationPose(-60, 55, 90))
                                                    .setNext(new DriveToAction("3LP", new NavigationPose(-60, 55, 0))
                                                            .setNext(new DriveToAction("3LP", new NavigationPose(-12, 55)))))));
                }
            } else {
                //left, secondary, parking zones 1-3
                if (parkingZone == 1) {
                    return new DriveToAction("1LS - rotate", new NavigationPose(-36, 72, 0))
                            .setNext(new DriveToAction("1LS - left", new NavigationPose(-36, 55, 0))
                                    .setNext(new DriveToAction("1LS - forward", new NavigationPose(-60, 55))));
                } else if (parkingZone == 2) {
                    return new DriveToAction("2LS - rotate", new NavigationPose(-36, 72, 0))
                            .setNext(new DriveToAction("2LS - forward", new NavigationPose(-36, 55, 0)));
                } else {
                    return new DriveToAction("3LS - rotate", new NavigationPose(-36, 72, 0))
                            .setNext(new DriveToAction("3LS - right", new NavigationPose(-36, 55, 0))
                                    .setNext(new DriveToAction("3LS - right", new NavigationPose(-12, 57))));
                }
            }
        }
        if (isPrimary) {
            //right, primary, parking zones 1-3
            if (parkingZone == 1) {
                return new DriveToAction("1RP - rotate", new NavigationPose(36, 72, 0))
                        .setNext(new DriveToAction("1RP - left", new NavigationPose(36, 60, 0))
                                .setNext(new DriveToAction("1RP - forward", new NavigationPose(12, 60))));
            } else if (parkingZone == 2) {
                return new DriveToAction("2RP - forward", new NavigationPose(36, 72, 0))
                        .setNext(new DriveToAction("2RP - backward", new NavigationPose(36, 55, 0)));
            } else {
                return new DriveToAction("3RS - right", new NavigationPose(36, 72, 0))
                        .setNext(new DriveToAction("3RP - forward", new NavigationPose(36, 60, 0))
                                .setNext(new DriveToAction("3RP - right", new NavigationPose(60, 55))));
            }
        }
        //right, secondary, parking zones 1-3
        if (parkingZone == 1) {
            return new DriveToAction("1RS- left", new NavigationPose(12, 48, 0));

        } else if (parkingZone == 2) {
            return new DriveToAction("2RS - forward", new NavigationPose(12, 48, 0))
                    .setNext(new DriveToAction("1RS - forward", new NavigationPose(12, 60))
                            .setNext(new DriveToAction("1RS - right", new NavigationPose(36, 55))));
        }
        return new DriveToAction("3RS - right", new NavigationPose(12, 48, 0))
                .setNext(new DriveToAction("3RS - forward", new NavigationPose(12, 60))
                        .setNext(new DriveToAction("2RS - right", new NavigationPose(60, 55))));

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