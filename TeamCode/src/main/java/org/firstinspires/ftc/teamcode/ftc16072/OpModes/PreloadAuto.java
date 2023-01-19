package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveToAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.GripClaw;
import org.firstinspires.ftc.teamcode.ftc16072.actions.QQAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.delayAction;
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

        return new DriveToAction(description, new NavigationPose((LR * 12), 48, theta));
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

        return new DriveToAction(description, new NavigationPose((LR * 36), 72, theta));
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

        return new DriveToAction(description, new NavigationPose((LR * 55), 55, theta));
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
            return new DriveToAction(description, new NavigationPose((LR * 60), 55, theta));
        } else if (parkingZone == 2) {
            return new DriveToAction(description, new NavigationPose((LR * 36), 55, theta));
        } else {
            return new DriveToAction(description, new NavigationPose((LR * 12), 55, theta));
        }
    }

    private QQAction primaryStrategy() {
        return
                //drives to primary goal
                new DriveToAction("description", new NavigationPose(LR * 12, 10, 0)) //1
                        .setNext(primaryGoal(0)//2
                                .setNext(primaryGoal(LR * 90)//2
                                        .setNext(primaryGoal(0)//2
                                                .setNext(new delayAction(1)
                                                        //drives to cone stack
                                                        .setNext(new DriveToAction("description", new NavigationPose(LR * 12, 60, 0))//3
                                                                .setNext(coneStack(0)//4
                                                                        .setNext(coneStack(-1 * LR * 90)//4
                                                                                .setNext(coneStack(0)//4
                                                                                        .setNext(new delayAction(1)
                                                                                                //drives to parking zone
                                                                                                .setNext(parkingZone(0)//5
                                                                                                ))))))))));
    }

    private QQAction secondaryStrategy() {
        return
                //drives to secondary goal
                secondaryGoal(0)//1
                        .setNext(secondaryGoal(LR * 90)//1
                                .setNext(secondaryGoal(0)//1
                                        .setNext(new delayAction(1)
                                                //drives to cone stack
                                                .setNext(new DriveToAction("description", new NavigationPose(LR * 36, 55, 0))//2
                                                        .setNext(coneStack(0)//3
                                                                .setNext(coneStack(-1 * LR * 90)//3
                                                                        .setNext(coneStack(0)//3
                                                                                .setNext(new delayAction(1)
                                                                                        //drives to parking zone
                                                                                        .setNext(parkingZone(0)//4
                                                                                        )))))))));
    }

    @Override
    public void start() {
        super.start();
        if (isLeft) {
            LR = -1;
        } else {
            LR = 1;
        }

        if(isPrimary) {
            currentAction = primaryStrategy();
        }else
            currentAction = secondaryStrategy();

    }
}
