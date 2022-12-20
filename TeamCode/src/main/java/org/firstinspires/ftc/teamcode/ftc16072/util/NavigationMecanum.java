package org.firstinspires.ftc.teamcode.ftc16072.util;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.ftc16072.Robot;

public class NavigationMecanum {
    public Robot robot;
    public double TURN_TOLERANCE = 3.0;
    public double desiredHeading;
    public final double PI = Math.PI;
    public double offReset = 0;
    public NavigationMecanum(Robot robot) {
        this.robot = robot;
    }

    public void driveFieldRelative(double forward, double right, double rotateSpeed) {
        double heading = robot.gyro.getHeading(AngleUnit.RADIANS)-offReset;
        Polar drive = new Polar(right, forward);
        drive.rotate(-heading, AngleUnit.RADIANS);

        robot.mecanumDrive.drive(drive.getY(), drive.getX(), rotateSpeed);
    }

    public boolean checkIfInRange(double DH){
        double heading = robot.gyro.getHeading(AngleUnit.DEGREES)-(90);
        if(DH != 180){
            if(Math.abs(DH-heading) < TURN_TOLERANCE){
                return true;
            }
        } else{
            if(Math.abs(180-heading) < TURN_TOLERANCE || Math.abs(-180-heading) < TURN_TOLERANCE){
                return true;
            }
        }
        return false;
    }
    public void resetGyro(){
        offReset = robot.gyro.getHeading(AngleUnit.DEGREES);
    }
    public double getSnapCCW() {
        double heading = robot.gyro.getHeading(AngleUnit.DEGREES)-offReset;

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

    public double getHeading(){
        return robot.gyro.getHeading(AngleUnit.DEGREES)-offReset;
    }
    public void driveOrthogonal(double joystickX, double joystickY){
        Polar orthogonal = new Polar(joystickX, joystickY);
        double theta = orthogonal.getTheta(AngleUnit.RADIANS);
        double r = Math.sqrt(Math.pow(joystickX,2)+Math.pow(joystickY,2));

        if(theta>=PI/4&&theta<=3*PI/4){ //45-135
            //robot.mecanumDrive.drive(1*r,0,0);
            //robot.mecanumDrive.drive(0,1*r,0);
            //driveFieldRelative(0,1*r,0);
            driveFieldRelative(-1*r,0,0);
        }
        if(theta<=-PI/4&&theta>=-3*PI/4){ //-45 to -135
            //robot.mecanumDrive.drive(-1*r,0,0);
            //robot.mecanumDrive.drive(0,-1*r,0);
            //driveFieldRelative(0,-1*r,0);
            driveFieldRelative(1*r,0,0);
        }
        if((theta<(-3*PI/4) && theta>=-PI) || (theta>(3*PI)/4 && theta<PI)){ //-135 to -180 or 135 to 180
            //robot.mecanumDrive.drive(0,-1*r,0);
            //robot.mecanumDrive.drive(-1*r,0,0);
            //driveFieldRelative(-1*r,0,0);
            driveFieldRelative(0,-1*r,0);
        }
        if((theta>(-PI/4) && theta<=0) || (theta<PI/4 && theta>0)){ //-45 to 0 or 45 to 0
            //robot.mecanumDrive.drive(0,1*r,0);
            //robot.mecanumDrive.drive(1*r,0,0);
            //driveFieldRelative(1*r,0,0);
            driveFieldRelative(0,1*r,0);
        }
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
        double rotateDiff = AngleUnit.normalizeDegrees(robot.gyro.getHeading(AngleUnit.DEGREES)-offReset - au.toDegrees(angle));

        if (Math.abs(rotateDiff) < TURN_TOLERANCE) {
            robot.mecanumDrive.drive(0, 0, 0);
            return true;
        } else {
            rotateSpeed = KP_ANGLE * rotateDiff;
            if (Math.abs(rotateSpeed) < MIN_TURNING_SPEED) {
                rotateSpeed = Math.signum(rotateSpeed) * MIN_TURNING_SPEED;
            }
            robot.mecanumDrive.drive(0, 0, rotateSpeed*0.08);
        }

        return false;
    }



    public boolean snapToClosest(){
        double heading = robot.gyro.getHeading(AngleUnit.DEGREES);
        if(heading >= 45 && heading < 135){
            rotateTo(90, AngleUnit.DEGREES);
            return true;
        } else if(heading >= 135 || heading < -135){
            rotateTo(180, AngleUnit.DEGREES);
            return true;
        } else if(heading >= -135 && heading < -45){
            rotateTo(-90, AngleUnit.DEGREES);
            return true;
        } else if(heading >= -45 && heading<45){
            rotateTo(0, AngleUnit.DEGREES);
            return true;
        }
        return false;
    }

}
