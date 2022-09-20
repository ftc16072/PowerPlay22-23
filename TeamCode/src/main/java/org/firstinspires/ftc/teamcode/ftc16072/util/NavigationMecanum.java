package org.firstinspires.ftc.teamcode.ftc16072.util;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.ftc16072.Robot;

public class NavigationMecanum {
    public Robot robot;

    public NavigationMecanum(Robot robot){
        this.robot = robot;
    }

    public void driveFieldRelative(double forward, double right, double rotateSpeed){
        double heading = robot.gyro.getHeading(AngleUnit.RADIANS);
        Polar drive = new Polar(right, forward);
        drive.rotate(-heading, AngleUnit.RADIANS);

        robot.mecanumDrive.drive(drive.getY(), drive.getX(), rotateSpeed);
    }

    public void snapTurns(double forward, double right, boolean isLeft){
        Polar drive = new Polar(right, forward);
        /**if(isLeft) {
            drive.rotate(-Math.PI / 2, AngleUnit.RADIANS);
        }else{
            drive.rotate(Math.PI / 2, AngleUnit.RADIANS);
        }**/
        drive.rotate(90,AngleUnit.DEGREES);

    }

}
