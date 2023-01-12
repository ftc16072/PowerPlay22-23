package org.firstinspires.ftc.teamcode.ftc16072.util;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class NavigationPose extends RobotPose{
    double xToleranceCm;
    double yToleranceCm;
    double angleTolerance;
    double minSpeed;
    double maxSpeed;

    public NavigationPose(double x, double xTolerance, double y, double yTolerance, DistanceUnit du, double minTransSpeed, double maxTransSpeed, double angle, double angleTolerance, AngleUnit au) {
        super(x, y, du, angle, au);
        xToleranceCm = du.toCm(xTolerance);
        yToleranceCm = du.toCm(yTolerance);
        this.angleTolerance = au.toRadians(angleTolerance);
        minSpeed = minTransSpeed;
        maxSpeed = maxTransSpeed;
    }

    public NavigationPose(double x, double xTolerance, double y, double yTolerance, DistanceUnit du, double angle, double angleTolerance, AngleUnit au){
        this(x, xTolerance, y, yTolerance, du, .1, 1, angle, angleTolerance, au);
    }

    public NavigationPose(double x, double y, double theta){
        this(x, 0.5, y, 0.5, DistanceUnit.INCH, theta, 2, AngleUnit.DEGREES);
    }
    public NavigationPose(double x, double y){
        this(x, y,0);
    }

    public boolean inDistanceTolerance(RobotPose otherPose){
        //figuring out the tolerance in order to navigate the robot
        boolean xIsIn = Math.abs(otherPose.getX(DistanceUnit.CM) - x_cm) <= xToleranceCm;
        boolean yIsIn = Math.abs(otherPose.getY(DistanceUnit.CM) - y_cm) <= yToleranceCm;
        return xIsIn && yIsIn;
    }
    public boolean inAngleTolerance(RobotPose otherPose){
        //figuring out the tolerance in order to navigate the robot
        return Math.abs(otherPose.getAngle(AngleUnit.RADIANS) - theta) <= angleTolerance;
    }

    public double getMinSpeed(){
        return minSpeed;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }
}
