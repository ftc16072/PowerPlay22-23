package org.firstinspires.ftc.teamcode.ftc16072.util;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.ftc16072.Robot;

public class NavigationMecanum {
    public Robot robot;
    public double TURN_TOLERANCE = 1.0;
    public double desiredHeading;

    public NavigationMecanum(Robot robot) {
        this.robot = robot;
    }

    public void driveFieldRelative(double forward, double right, double rotateSpeed) {
        double heading = robot.gyro.getHeading(AngleUnit.RADIANS)-(Math.PI/2);
        Polar drive = new Polar(right, forward);
        drive.rotate(-heading, AngleUnit.RADIANS);

        robot.mecanumDrive.drive(drive.getY(), drive.getX(), rotateSpeed);
    }

    public double getSnapCCW() {
        double heading = robot.gyro.getHeading(AngleUnit.DEGREES);

        if ((heading >= (90 - TURN_TOLERANCE)) && (heading <= (90 + TURN_TOLERANCE))) {
            desiredHeading = 180;
        } else if ((heading >= (180 - TURN_TOLERANCE)) && (heading <= (-180 + TURN_TOLERANCE))) {
            desiredHeading = -90;
        } else if ((heading >= (-90 - TURN_TOLERANCE)) && (heading <= (-90 + TURN_TOLERANCE))) {
            desiredHeading = 0;
        } else if ((heading >= (0 - TURN_TOLERANCE)) && (heading <= (0 + TURN_TOLERANCE))) {
            desiredHeading = 90;
        } else if (heading >= 0 && heading < 90) {
            desiredHeading = 90;
        } else if (heading >= 90 && heading < 180) {
            desiredHeading = 180;
        } else if (heading >= -180 && heading < -90) {
            desiredHeading = -90;
        } else if (heading >= -90 && heading < 0) {
            desiredHeading = 0;
        }
        return desiredHeading;
    }



    public double getSnapCW() {
        double heading = robot.gyro.getHeading(AngleUnit.DEGREES);

        if ((heading >= (90 - TURN_TOLERANCE)) && (heading <= (90 + TURN_TOLERANCE))) {
            desiredHeading = 0;
        } else if ((heading >= (180 - TURN_TOLERANCE)) && (heading <= (-180 + TURN_TOLERANCE))) {
            desiredHeading = 90;
        } else if ((heading >= (-90 - TURN_TOLERANCE)) && (heading <= (-90 + TURN_TOLERANCE))) {
            desiredHeading = -180;
        } else if ((heading >= (0 - TURN_TOLERANCE)) && (heading <= (0 + TURN_TOLERANCE))) {
            desiredHeading = -90;
        } else if (heading > 0 && heading <= 90) {
            desiredHeading = 0;
        } else if (heading > 90 && heading <= 180) {
            desiredHeading = 90;
        } else if (heading > -180 && heading <= -90) {
            desiredHeading = -180;
        } else if (heading > -90 && heading <= 0) {
            desiredHeading = -90;
        }
        return desiredHeading;
    }

    public boolean rotateTo(double angle, AngleUnit au) {
        double rotateSpeed;
        double MIN_TURNING_SPEED = 0.1;
        double KP_ANGLE = 0.1;
        double rotateDiff = AngleUnit.normalizeDegrees(robot.gyro.getHeading(AngleUnit.DEGREES) - au.toDegrees(angle));

        if (Math.abs(rotateDiff) < TURN_TOLERANCE) {
            robot.mecanumDrive.drive(0, 0, 0);
            return true;
        } else {
            rotateSpeed = KP_ANGLE * rotateDiff;
            if (Math.abs(rotateSpeed) < MIN_TURNING_SPEED) {
                rotateSpeed = Math.signum(rotateSpeed) * MIN_TURNING_SPEED;
            }
            robot.mecanumDrive.drive(0, 0, rotateSpeed);
        }

        return false;
    }
}
