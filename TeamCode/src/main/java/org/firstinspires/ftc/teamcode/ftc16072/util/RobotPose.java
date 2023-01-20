package org.firstinspires.ftc.teamcode.ftc16072.util;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class RobotPose {
    double x_cm;
    double y_cm;
    double theta;

    public RobotPose(double x, double y, DistanceUnit du, double angle, AngleUnit au){
        x_cm = du.toCm(x);
        y_cm = du.toCm(y);
        theta = au.toRadians(angle);
    }

    public void setX(double x, DistanceUnit du){
        x_cm = du.toCm(x);
    }

    public void setY(double y, DistanceUnit du){
        y_cm = du.toCm(y);
    }
    public void setAngle(double angle, AngleUnit au){
        theta = au.toRadians(angle);
    }

    public double getX(DistanceUnit du) {
        return du.fromCm(x_cm);
    }
    public double getY(DistanceUnit du) {
        return du.fromCm(y_cm);
    }

    public double getAngle(AngleUnit au) {
        return au.fromRadians(theta);
    }

    public Polar getTranslateDistance(RobotPose otherPoint){
        DistanceUnit du = DistanceUnit.CM;
        return new Polar(otherPoint.getX(du) - this.getX(du), (otherPoint.getY(du) - this.getY(du)));
    }
    public double getAngleDistance(RobotPose otherPoint, AngleUnit au){
        return  au.normalize(otherPoint.getAngle(au) - getAngle(au));
    }
    public void updatePose(MoveDeltas moveDeltas){
        theta = AngleUnit.normalizeRadians(theta + moveDeltas.getAngle(AngleUnit.RADIANS));

        Polar translation = new Polar(moveDeltas.getForward(DistanceUnit.CM), moveDeltas.getStrafe(DistanceUnit.CM));
        Polar rotated = translation.rotateCCW(theta, AngleUnit.RADIANS);

        x_cm += rotated.getX();
        y_cm += rotated.getY();
    }
}

