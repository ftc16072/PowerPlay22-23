package org.firstinspires.ftc.teamcode.ftc16072.actions;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ftc16072.OpModes.QQOpMode;

public class DriveLeftAction extends QQAction {
    double distanceCM;
    boolean firstTime = true;

    @Override
    public QQAction run(QQOpMode opMode) {
        if (firstTime) {
            opMode.robot.mecanumDrive.setEncodeOffsets();
            firstTime = false;
        }
        opMode.robot.mecanumDrive.drive(0, -0.5, 0);
        double[] distances = opMode.robot.mecanumDrive.getDistanceCM();
        if (-distances[1] > distanceCM) {
            opMode.robot.mecanumDrive.drive(0, 0, 0);
            return nextAction;
        }
        return this;
    }

    public DriveLeftAction(double distance, DistanceUnit du) {
        super();
        distanceCM = du.toCm(distance);

    }

}
