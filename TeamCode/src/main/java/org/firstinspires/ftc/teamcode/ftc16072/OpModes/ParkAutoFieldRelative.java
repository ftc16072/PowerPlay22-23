package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.ftc16072.actions.ConditionalAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveToAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.QQAction;
import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationPose;

import java.util.Arrays;

@Autonomous
public class ParkAutoFieldRelative extends VisionAutoBase {
    //0,0 is bottom left, field relative
/*
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
*/
    @Override
    public void start() {
        super.start();
        QQAction right1 = new DriveToAction("1R- left", new NavigationPose(12,12,0))
                .setNext(new DriveToAction("1R - forward",new NavigationPose(12,12)));
        QQAction right2 = new DriveToAction("2R - forward", new NavigationPose(12,12,0));
        QQAction right3 = new DriveToAction("2R - left", new NavigationPose(12,12,0))
                .setNext(new DriveToAction("2R - forward",new NavigationPose(12,12)));

        QQAction right = null;
        if (parkingZone == 1) right = right1;
        if (parkingZone == 2) right = right2;
        if (parkingZone == 3) right = right3;

        QQAction left1 = new DriveToAction("1L - left", new NavigationPose(12,12,0))
                .setNext(new DriveToAction("1L - forward",new NavigationPose(12,12)));
        QQAction left2 = new DriveToAction("2L - forward", new NavigationPose(12,12));
        QQAction left3 = new DriveToAction("3L - right", new NavigationPose(12,12))
                .setLast(new DriveToAction("3L - forward",new NavigationPose(12,12)));

        QQAction left = null;
        if (parkingZone == 1) left = left1;
        if (parkingZone == 2) left = left2;
        if (parkingZone == 3) left = left3;

        if(isLeft){
            currentAction = left;
        }else{
            currentAction = right;
        }
    }
}