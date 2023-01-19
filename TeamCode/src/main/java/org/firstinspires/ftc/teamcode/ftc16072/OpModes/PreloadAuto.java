package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.ftc16072.actions.ChangeLiftAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveToAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DualAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.GripClaw;
import org.firstinspires.ftc.teamcode.ftc16072.actions.QQAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.ReleaseClaw;
import org.firstinspires.ftc.teamcode.ftc16072.actions.RotateAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.delayAction;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.Lift;
import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationPose;

@Autonomous
public class PreloadAuto extends VisionAutoBase {

    int LR;

    private QQAction primaryGoal(int theta) {
        int LR;
        String description;

        if (isLeft) {
            LR = -1;
            description = "left primary goal";
        } else {
            LR = 1;
            description = "right primary goal";
        }

        return new DriveToAction(description, new NavigationPose((LR * 10), 48, theta));
    }

    private QQAction primaryGoal(int theta, double x) {
        int LR;
        String description;

        if (isLeft) {
            LR = -1;
            description = "left primary goal";
        } else {
            LR = 1;
            description = "right primary goal";
        }

        return new DriveToAction(description, new NavigationPose((LR * x), 48, theta));
    }

    private QQAction secondaryGoal(int theta) {
        int LR;
        String description;

        if (isLeft) {
            LR = -1;
            description = "left secondary goal";
        } else {
            LR = 1;
            description = "right secondary goal";
        }

        return new DriveToAction(description, new NavigationPose((LR * 32), 65, theta));
    }

    private QQAction coneStack(int theta) {
        //also serves as the 1st parking spot on left side and 3rd parking spot on right side
        int LR;
        String description;

        if (isLeft) {
            LR = -1;
            description = "left cone stack";
        } else {
            LR = 1;
            description = "right cone stack";
        }

        return new DriveToAction(description, new NavigationPose((LR * 43), 57, theta));
    }

    private QQAction parkingZone(int theta) {
        int LR;
        String description;

        if (isLeft) {
            LR = -1;
            description = "left cone stack";
        } else {
            LR = 1;
            description = "right cone stack";
        }

        if ((parkingZone == 1 && isLeft) || (parkingZone == 3 && !isLeft)) {
            //42,42
            //2 - 60,55
            return new DriveToAction(description, new NavigationPose((LR * 45), 55, theta));
        } else if (parkingZone == 2) {
            return new DriveToAction(description, new NavigationPose((LR * 24), 55, theta));
        } else {
            return new DriveToAction(description, new NavigationPose((LR * 12), 55, theta));
        }
    }


    private QQAction primaryStrategy() {
        robot.mecanumDrive.setMaxSpeed(0.4);
        return new DualAction("drive to goal and lift high", new ChangeLiftAction("lift high", Lift.Level.HIGH), new DriveToAction("drive right", new NavigationPose(LR * 10, 22, 0)))
                .setNext(primaryGoal(0)
                        .setNext(new RotateAction("turn to goal", (LR * 90), AngleUnit.DEGREES)
                                .setNext(new ReleaseClaw()
                                        .setNext(new delayAction(2)
                                                .setNext(primaryGoal(0, 10)
                                                        .setNext(new DualAction("drive to cone stack and lift low", new ChangeLiftAction("lift to low", Lift.Level.LOW), new DriveToAction("drive forward", new NavigationPose(LR * 8, 55, 0))
                                                                .setNext(coneStack(0)
                                                                        .setNext(new RotateAction("turn to cone stack", (-1 * LR * 90), AngleUnit.DEGREES)
                                                                                .setNext(new GripClaw()
                                                                                        .setNext(new delayAction(2)
                                                                                                .setNext(coneStack(0)
                                                                                                        .setNext(parkingZone(0)
                                                                                                        )))))))))))));

    }

    private QQAction secondaryStrategy() {
        robot.mecanumDrive.setMaxSpeed(0.4);
        return new DualAction("drive to goal and lift high", new ChangeLiftAction("lift high", Lift.Level.HIGH), secondaryGoal(0)
                .setNext(new RotateAction("turn to goal", (LR * 90), AngleUnit.DEGREES)
                        .setNext(new ReleaseClaw()
                                .setNext(new delayAction(2)
                                        .setNext(secondaryGoal(0)
                                                .setNext(new DualAction("drive to cone stack and lift low", new ChangeLiftAction("lift to low", Lift.Level.LOW), new DriveToAction("drive to cone stack", new NavigationPose(LR * 36, 57, 0)))
                                                        //.setNext(new DriveToAction("drive to cone stack", new NavigationPose(LR * 36, 57, 0))
                                                        .setNext(coneStack(0)
                                                                .setNext((new RotateAction("turn to cone stack", (-1 * LR * 90), AngleUnit.DEGREES)
                                                                        .setNext(new GripClaw()
                                                                                .setNext(new delayAction(2)
                                                                                        .setNext(coneStack(0)
                                                                                                .setNext(parkingZone(0)
                                                                                                )))))))))))));
    }


    @Override
    public void start() {
        super.start();
        if (isLeft) {
            LR = -1;
        } else {
            LR = 1;
        }


        if (isPrimary) {
            currentAction = primaryStrategy();
        } else
            currentAction = secondaryStrategy();

        //currentAction = primaryStrategy();

    }
}
