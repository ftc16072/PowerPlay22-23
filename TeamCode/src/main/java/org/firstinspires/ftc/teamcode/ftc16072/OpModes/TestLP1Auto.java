package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.ftc16072.actions.ChangeLiftAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveToAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DualAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.GripClaw;
import org.firstinspires.ftc.teamcode.ftc16072.actions.ReleaseClaw;
import org.firstinspires.ftc.teamcode.ftc16072.actions.delayAction;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.Lift;
import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationPose;

@Autonomous
public class TestLP1Auto extends VisionAutoBase {
    @Override
    public void start() {

        super.start();
        currentAction = new GripClaw()
                .setNext(new delayAction(0.5)
                        .setNext(new ChangeLiftAction("Lift high", Lift.Level.HIGH)
                                .setNext(new DriveToAction("PL - right", new NavigationPose(-12, 10, 0))
                                        .setNext(new DriveToAction("PL - forward", new NavigationPose(-12, 48))
                                                .setNext(new DriveToAction("rotate -90", new NavigationPose(-12, 48, -90))
                                                        .setNext(new ReleaseClaw()
                                                                .setNext(new ChangeLiftAction("lift to low", Lift.Level.LOW)
                                                                        .setNext(new DriveToAction("1LP - rotate", new NavigationPose(-12, 48, 0))
                                                                                .setNext(new DriveToAction("1LP - left", new NavigationPose(-12, 60, 0))
                                                                                        .setNext(new DriveToAction("1LP - forward", new NavigationPose(-60, 55, 0))
                                                                                                .setNext(new DriveToAction("1LP - forward", new NavigationPose(-60, 55, 90))
                                                                                                        .setNext(new GripClaw()))))))))))));


    }
}
