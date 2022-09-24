package org.firstinspires.ftc.teamcode.ftc16072.util;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.ftc16072.Robot;

public class NavigationMecanum {
    public Robot robot;
    public double TURN_TOLERANCE = 5.0;

    public NavigationMecanum(Robot robot){
        this.robot = robot;
    }

    public void driveFieldRelative(double forward, double right, double rotateSpeed){
        double heading = robot.gyro.getHeading(AngleUnit.RADIANS);
        Polar drive = new Polar(right, forward);
        drive.rotate(-heading, AngleUnit.RADIANS);

        robot.mecanumDrive.drive(drive.getY(), drive.getX(), rotateSpeed);
    }


    public boolean snapTurnCW(){
        double heading = robot.gyro.getHeading(AngleUnit.DEGREES);
        return false;
    }

    public boolean snapTurnCCW(){
        return false;
    }
}
