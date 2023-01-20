package org.firstinspires.ftc.teamcode.ftc16072.actions;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ftc16072.OpModes.QQOpMode;
import org.firstinspires.ftc.teamcode.ftc16072.util.Polar;
import org.firstinspires.ftc.teamcode.ftc16072.util.RobotPose;

public class RotateAction extends QQAction {
    double angleDEG;
    boolean firstTime = true;
    final int TOLERANCE = 5;

    @Override
    public QQAction run(QQOpMode opMode) {

        if (firstTime) {
            opMode.robot.mecanumDrive.setEncodeOffsets();
            firstTime = false;
        }

        opMode.telemetry.addData("heading", opMode.nav.getHeading());
        opMode.telemetry.addData("angleDEG", angleDEG);

        opMode.nav.rotateTo(angleDEG, AngleUnit.DEGREES);
        double angles = opMode.nav.getHeading();
        if (angles > (angleDEG - TOLERANCE) && angles < (angleDEG + TOLERANCE)) {
            opMode.robot.mecanumDrive.drive(0, 0, 0);
            return nextAction;
        }
        return this;
    }

    public RotateAction(String description, double degrees, AngleUnit au) {

        super(description);
        angleDEG = au.toDegrees(degrees);

    }
}
