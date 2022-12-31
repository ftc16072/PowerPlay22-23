package org.firstinspires.ftc.teamcode.ftc16072.util;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class MoveDeltas {
    double x_cm;
    double y_cm;
    double theta;

    public MoveDeltas(double forward, double strafe, DistanceUnit du, double angle, AngleUnit au){
        x_cm = du.toCm(forward);
        y_cm = du.toCm(strafe);
        theta = au.toRadians(angle);
    }

    public double getForward(DistanceUnit du) {
        return du.fromCm(y_cm);
    }
    public double getStrafe(DistanceUnit du) {
        return du.fromCm(y_cm);
    }
    public double getAngle(AngleUnit au) {
        return au.fromRadians(theta);
    }
    public void setAngle(double angle, AngleUnit au) {
        theta = au.toRadians(angle);
    }


}
