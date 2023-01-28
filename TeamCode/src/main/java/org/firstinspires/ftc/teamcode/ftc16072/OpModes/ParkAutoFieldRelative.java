package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveToAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.QQAction;
import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationPose;

@Autonomous
public class ParkAutoFieldRelative extends VisionAutoBase {
    //0,0 is center bottom of the field from driver perspective, field relative

    private QQAction getZone() {
        if (isLeft) {
            if (parkingZone == 1) {
                return new DriveToAction("1L - left", new NavigationPose(-50, 24, 0))
                        .setLast(new DriveToAction("1L - forward", new NavigationPose(-50, 48)));
            } else if (parkingZone == 2) {
                return new DriveToAction("2L - forward", new NavigationPose(-36 +5.5, 48));
            } else {
                return new DriveToAction("3L - right", new NavigationPose(-11, 24))
                        .setLast(new DriveToAction("3L - forward", new NavigationPose(-12, 48)));
            }
        }
        if (parkingZone == 1) {
            return new DriveToAction("1R- left", new NavigationPose(13, 24, 0))
                    .setLast(new DriveToAction("1R - forward", new NavigationPose(12, 48)));
        } else if (parkingZone == 2) {
            return new DriveToAction("2R - forward", new NavigationPose(36 -5.5, 48, 0));
        }
        return new DriveToAction("3R - right", new NavigationPose(50, 24, 0))
                .setLast(new DriveToAction("3R - forward", new NavigationPose(50, 48)));
    }


    @Override
    public void start() {
        super.start();
       /* QQAction right1 = new DriveToAction("1R- left", new NavigationPose(12, 10, 0))
                .setNext(new DriveToAction("1R - forward", new NavigationPose(12, 36)));
        QQAction right2 = new DriveToAction("2R - forward", new NavigationPose(36, 36, 0));
        QQAction right3 = new DriveToAction("3R - right", new NavigationPose(60, 10, 0))
                .setNext(new DriveToAction("3R - forward", new NavigationPose(60, 36)));

        QQAction right = null;
        if (parkingZone == 1) right = right1;
        if (parkingZone == 2) right = right2;
        if (parkingZone == 3) right = right3;

        QQAction left1 = new DriveToAction("1L - left", new NavigationPose(-60, 10, 0))
                .setNext(new DriveToAction("1L - forward", new NavigationPose(-60, 36)));
        QQAction left2 = new DriveToAction("2L - forward", new NavigationPose(-36, 36));
        QQAction left3 = new DriveToAction("3L - right", new NavigationPose(-12, 10))
                .setLast(new DriveToAction("3L - forward", new NavigationPose(-12, 36)));

        QQAction left = null;
        if (parkingZone == 1) left = left1;
        if (parkingZone == 2) left = left2;
        if (parkingZone == 3) left = left3;

        if (isLeft) {
            currentAction = left;
        } else {
            currentAction = right;
        }*/
        currentAction = getZone();
    }
}