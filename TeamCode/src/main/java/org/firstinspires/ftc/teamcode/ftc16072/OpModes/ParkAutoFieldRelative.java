package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.ftc16072.actions.ConditionalAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveToAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.QQAction;
import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationPose;

import java.util.Arrays;

@Autonomous
public class ParkAutoFieldRelative extends VisionAutoBase {
    QQAction currentAction;

    //0,0 is bottom left, field relative
    QQAction right1 = new DriveToAction("left", new NavigationPose(12,12,0))
            .setNext(new DriveToAction("forward",new NavigationPose(12,12)));
    QQAction right2 = new DriveToAction("forward", new NavigationPose(12,12,0));
    QQAction right3 = new DriveToAction("left", new NavigationPose(12,12,0))
            .setNext(new DriveToAction("forward",new NavigationPose(12,12)));
    QQAction right = new ConditionalAction("drive to parking zone", Arrays.asList(right1, right2, right3), ()->parkingZone-1);

    QQAction left1 = new DriveToAction("left", new NavigationPose(12,12,0))
            .setNext(new DriveToAction("forward",new NavigationPose(12,12)));
    QQAction left2 = new DriveToAction("forward", new NavigationPose(12,12));
    QQAction left3 = new DriveToAction("right", new NavigationPose(12,12))
            .setLast(new DriveToAction("forward",new NavigationPose(12,12)));
    QQAction left = new ConditionalAction("drive to parking zone", Arrays.asList(left1, left2, left3), ()->parkingZone-1);

    @Override
    public void start() {
        currentAction = new ConditionalAction("park", Arrays.asList(left, right), ()->isLeft?1:0);
    }

    @Override
    public void loop() {
        if (currentAction != null) {
            currentAction = currentAction.run(this);
        }
    }
}