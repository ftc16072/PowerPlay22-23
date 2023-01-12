package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveToAction;
import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationPose;

@Autonomous
public class AutoTest extends VisionAutoBase {
   // QQAction currentAction;
    @Override
    public void init(){
        super.init();
    }

    @Override
    public void start() {
        currentAction = new DriveToAction("test", new NavigationPose(12,0,0));
    }
}