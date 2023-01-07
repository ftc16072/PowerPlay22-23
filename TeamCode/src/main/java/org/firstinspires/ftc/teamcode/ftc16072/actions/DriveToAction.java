package org.firstinspires.ftc.teamcode.ftc16072.actions;


import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ftc16072.OpModes.QQOpMode;
import org.firstinspires.ftc.teamcode.ftc16072.Robot;
import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationMecanum;
import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationPose;
import org.firstinspires.ftc.teamcode.ftc16072.util.RobotPose;

public class DriveToAction extends QQAction{
    NavigationPose desiredPose;


    public DriveToAction(String description, NavigationPose desiredPose){
        super(description);
        this.desiredPose = desiredPose;
    }

    @Override
    public QQAction run(QQOpMode opMode) {
        RobotPose pose = NavigationMecanum.currentPosition;
        opMode.telemetry.addData("forward", pose.getY(DistanceUnit.INCH));
        opMode.telemetry.addData("strafe", pose.getX(DistanceUnit.INCH));
        opMode.telemetry.addData("imu", pose.getAngle(AngleUnit.DEGREES));

        boolean done = opMode.nav.driveTo(desiredPose);

        return done? nextAction : this;
    }
}
