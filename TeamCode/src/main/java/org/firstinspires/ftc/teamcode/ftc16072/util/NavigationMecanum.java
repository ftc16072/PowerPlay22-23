package org.firstinspires.ftc.teamcode.ftc16072.util;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.ftc16072.Robot;

public class NavigationMecanum {
    public Robot robot;
    public double TURN_TOLERANCE = 5.0;
    public double desiredHeading;

    public NavigationMecanum(Robot robot) {
        this.robot = robot;
    }

    public void driveFieldRelative(double forward, double right, double rotateSpeed) {
        double heading = robot.gyro.getHeading(AngleUnit.RADIANS);
        Polar drive = new Polar(right, forward);
        drive.rotate(-heading, AngleUnit.RADIANS);

        robot.mecanumDrive.drive(drive.getY(), drive.getX(), rotateSpeed);
    }


    public boolean snapTurnCCW(boolean isTurning) {
        double heading = robot.gyro.getHeading(AngleUnit.DEGREES);
        if (!isTurning) {
            if (heading > 0 && heading <= 90) {
                desiredHeading = 0;
            } else if (heading > 90 && heading <= 180) {
                desiredHeading = 90;
            } else if (heading > 180 && heading <= 270) {
                desiredHeading = 180;
            } else if (heading > 270 && heading <= 360 || heading == 0) {
                desiredHeading = 270;
            }
        }
        double diffAngle = Math.abs(heading-desiredHeading);
        if(diffAngle<=TURN_TOLERANCE){
            robot.mecanumDrive.drive(0,0,-0.5);
            return false;
        }
        else{
            robot.mecanumDrive.drive(0,0,0);
        }
        return true;
    }

    public boolean snapTurnCW(boolean isTurning) {
        return false;
    }
}
