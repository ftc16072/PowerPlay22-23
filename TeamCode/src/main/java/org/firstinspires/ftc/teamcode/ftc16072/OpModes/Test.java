package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveToAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.delayAction;
import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationPose;

@Autonomous
public class Test extends VisionAutoBase {

    @Override
    public void start() {
        super.start();
        currentAction = new DriveToAction("PL - right", new NavigationPose(-20, 10, 0))
                .setNext(new delayAction(1)
                        .setNext(new DriveToAction("PL - forward", new NavigationPose(-20, 48))
                                .setNext(new delayAction(1)
                                        .setNext(new DriveToAction("rotate -90", new NavigationPose(-20, 48, 0))))));
    }
}
