package org.firstinspires.ftc.teamcode.ftc16072.actions;


import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ftc16072.OpModes.QQOpMode;
import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationPose;
import org.firstinspires.ftc.teamcode.ftc16072.util.Polar;
import org.firstinspires.ftc.teamcode.ftc16072.util.RobotPose;

public class DriveToAction extends QQAction {
    NavigationPose desiredPose;


    public DriveToAction(String description, NavigationPose desiredPose) {
        super(description);
        this.desiredPose = desiredPose;
    }

    @Override
    public QQAction run(QQOpMode opMode) {
        RobotPose pose = nav.getCurrentPosition();

        //opMode.telemetry.addData("desired x", desiredPose.getX(DistanceUnit.INCH));
        // opMode.telemetry.addData("desired y", desiredPose.getY(DistanceUnit.INCH));
        opMode.telemetry.addData("forward", pose.getY(DistanceUnit.INCH));
         opMode.telemetry.addData("strafe", pose.getX(DistanceUnit.INCH));
         opMode.telemetry.addData("imu", pose.getAngle(AngleUnit.DEGREES));
        Polar p = pose.getTranslateDistance(desiredPose);
        //  opMode.telemetry.addData("distance x", p.getX());
        //opMode.telemetry.addData("distance y", p.getY());
        boolean done = opMode.nav.driveTo(desiredPose);


        return done ? nextAction : this;
    }
}
