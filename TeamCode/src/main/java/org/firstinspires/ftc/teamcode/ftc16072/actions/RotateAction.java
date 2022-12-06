package org.firstinspires.ftc.teamcode.ftc16072.actions;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ftc16072.OpModes.QQOpMode;

public class RotateAction extends QQAction{
    double angleDEG;
    boolean firstTime = true;

    @Override
    public QQAction run(QQOpMode opMode) {
        if (firstTime) {
            opMode.robot.mecanumDrive.setEncodeOffsets();
            firstTime = false;
        }
        opMode.nav.rotateTo(angleDEG, AngleUnit.DEGREES);
        double angles = opMode.nav.getHeading();
        if (angles > angleDEG) {
            opMode.robot.mecanumDrive.drive(0, 0, 0);
            return nextAction;
        }
        return this;
    }

    public RotateAction(double degrees, AngleUnit au) {
        super();
        angleDEG = au.toDegrees(degrees);

    }
}
