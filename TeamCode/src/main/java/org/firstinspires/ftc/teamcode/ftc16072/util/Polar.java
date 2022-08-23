package org.firstinspires.ftc.teamcode.ftc16072.util;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Polar {
    private double x;
    private double y;
    private double theta;
    private double r;

    private void set(double theta,double r){
        this.r = r;
        this.theta = theta;
        x = r * Math.cos(theta);
        y = r * Math.sin(theta);
    }

    public Polar(double angle, AngleUnit au, double r){
        set(au.toRadians(angle),r);
    }

    public Polar(double x, double y){
        this.x = x;
        this.y = y;
        theta = Math.atan2(this.y, this.x);
        r = Math.hypot(this.x, this.y);
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public double getTheta(AngleUnit au){
        return au.fromRadians(theta);
    }

    public double getR(){
        return r;
    }

    public void rotate(double angle, AngleUnit au){
        set(theta + au.toRadians(angle),r);
    }

    public void scaleR(double scale){
        r *= scale;
    }


}
