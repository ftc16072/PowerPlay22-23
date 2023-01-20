package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.ftc16072.actions.ChangeLiftAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveToAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DualAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.GripClaw;
import org.firstinspires.ftc.teamcode.ftc16072.actions.HorizontalSlides;
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

        return new DriveToAction(description, new NavigationPose((LR * 10), 50, theta));
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

        return new DriveToAction(description, new NavigationPose((LR * x), 53, theta));
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

        return new DriveToAction(description, new NavigationPose((LR * 43), 60, theta));
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
            return new DriveToAction(description, new NavigationPose((LR * 50), 60, theta));
        } else if (parkingZone == 2) {
            return new DriveToAction(description, new NavigationPose((LR * 24), 60, theta));
        } else {
            return new DriveToAction(description, new NavigationPose((LR * 12), 60, theta));
        }
    }


    private QQAction primaryStrategy() {
        robot.mecanumDrive.setMaxSpeed(0.4);
        //drives right and lifts
        return new DualAction("drive to goal and lift high", new ChangeLiftAction("lift high", Lift.Level.HIGH),
                new DriveToAction("drive right", new NavigationPose(LR * 10, 22, 0)))
                //drives forward to goal
                .setNext(primaryGoal(0)
                        //rotates towards goal
                        .setNext(new RotateAction("turn to goal", (LR * 90), AngleUnit.DEGREES)
                                //places cone
                                .setNext(new HorizontalSlides("front", org.firstinspires.ftc.teamcode.ftc16072.mechanisms.HorizontalSlides.Position.FRONT)
                                        .setNext(new delayAction(2)
                                                .setNext(new ChangeLiftAction("lift high placing", Lift.Level.HIGHPLACE)
                                                        .setNext(new delayAction(2)
                                                                .setNext(new ReleaseClaw()
                                                                        .setNext(new delayAction(2)
                                                                                //resets lift
                                                                                .setNext(new ChangeLiftAction("lift high", Lift.Level.HIGH)
                                                                                        .setNext(new delayAction(2)
                                                                                                .setNext(new HorizontalSlides("mid", org.firstinspires.ftc.teamcode.ftc16072.mechanisms.HorizontalSlides.Position.MIDDLE)
                                                                                                        .setNext(new delayAction(2)
                                                                                                                .setNext(new GripClaw()
                                                                                                                        .setNext(new ChangeLiftAction("lift low", Lift.Level.LOW)
                                                                                                                                .setNext(new delayAction(2)
                                                                                                                                        //rotates back to frontward facing position
                                                                                                                                        .setNext(primaryGoal(0, 10)
                                                                                                                                                //drives to parking zone
                                                                                                                                                .setNext(parkingZone(0)
                                                                                                                                                )))))))))))))))));

    }

    private QQAction secondaryStrategy() {
        robot.mecanumDrive.setMaxSpeed(0.4);
        //drives forward and lifts
        return new DualAction("drive to goal and lift high", new ChangeLiftAction("lift high", Lift.Level.HIGH), secondaryGoal(0)
                //rotates towards goal
                .setNext(new RotateAction("turn to goal", (LR * 90), AngleUnit.DEGREES)
                        //places cone
                        .setNext(new HorizontalSlides("front", org.firstinspires.ftc.teamcode.ftc16072.mechanisms.HorizontalSlides.Position.FRONT)
                                .setNext(new delayAction(2)
                                        .setNext(new ChangeLiftAction("lift high placing", Lift.Level.HIGHPLACE)
                                                .setNext(new delayAction(2)
                                                        .setNext(new ReleaseClaw()
                                                                .setNext(new delayAction(2)
                                                                        //resets lift
                                                                        .setNext(new ChangeLiftAction("lift high", Lift.Level.HIGH)
                                                                                .setNext(new delayAction(2)
                                                                                        .setNext(new HorizontalSlides("mid", org.firstinspires.ftc.teamcode.ftc16072.mechanisms.HorizontalSlides.Position.MIDDLE)
                                                                                                .setNext(new delayAction(2)
                                                                                                        .setNext(new GripClaw()
                                                                                                                .setNext(new ChangeLiftAction("lift low", Lift.Level.LOW)
                                                                                                                        .setNext(new delayAction(2)
                                                                                                                                //rotates back to frontward facing position
                                                                                                                                .setNext(secondaryGoal(0)
                                                                                                                                        //drives to parking zone
                                                                                                                                        .setNext(parkingZone(0)
                                                                                                                                        )))))))))))))))));
    }


    @Override
    public void start() {
        super.start();
        if (isLeft) {
            LR = -1;
        } else {
            LR = 1;
        }
        telemetry.addData("Left lift position", robot.lift.leftLiftMotor.getCurrentPosition());
        telemetry.addData("Right lift position", robot.lift.rightLiftMotor.getCurrentPosition());


        if (isPrimary) {
            currentAction = primaryStrategy();
        } else
            currentAction = secondaryStrategy();


    }
}
