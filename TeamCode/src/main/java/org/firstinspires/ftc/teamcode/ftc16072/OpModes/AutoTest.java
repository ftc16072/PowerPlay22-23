package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveToAction;
import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationPose;
import org.firstinspires.ftc.teamcode.ftc16072.util.RobotPose;

@Autonomous
public class AutoTest extends VisionAutoBase {
    @Override
    public void start() {
        super.start();
        RobotPose robotPose = nav.getCurrentPosition();

        NavigationPose dest = new NavigationPose(robotPose.getX(DistanceUnit.INCH), robotPose.getY(DistanceUnit.INCH), 0);
        currentAction = new DriveToAction("test", dest);
    }
}