package org.firstinspires.ftc.teamcode.ftc16072.util;

import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ftc16072.Robot;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.MecanumDrive;

public class NavigationMecanum {
    public static RobotPose currentPosition;
    public Robot robot;
    public double TURN_TOLERANCE = 1;
    public double desiredHeading;
    public final double PI = Math.PI;
    double TRANSLATE_KP = 0.02;//0.05
    final double ROTATE_KP = 2;//2
    final double MAX_ROTATE_SPEED = 0.8;//0.8
    final double MIN_ROTATE_SPEED = 0.2;//0.2
    public double offReset = 0;
    public NavigationMecanum(Robot robot) {
        this.robot = robot;
        if(currentPosition == null){
            currentPosition = new RobotPose(0,0, DistanceUnit.INCH, 0, AngleUnit.DEGREES);
        }
    }


    public void driveFieldRelative(double forward, double right, double rotateSpeed) {
        double heading = robot.gyro.getHeading(AngleUnit.RADIANS);
        Polar drive = new Polar(right, forward);
        drive.rotate(-heading, AngleUnit.RADIANS);

        robot.mecanumDrive.drive(drive.getY(), drive.getX(), rotateSpeed);
    }
    public void driveFieldRelativeAngle(double forward, double right, double angle){
        double rotateSpeed;
        double desired_angle = angle;
        double angle_in = desired_angle - Math.PI / 2;  // convert to robot coordinates

        rotateSpeed = AngleUnit.normalizeRadians(getHeading(AngleUnit.RADIANS) - angle_in);

        double MAX_ROTATE = 0.7; //This is to shrink how fast we can rotate so we don't fly past the angle
        rotateSpeed = Range.clip(rotateSpeed, -MAX_ROTATE, MAX_ROTATE);

        driveFieldRelative(forward, right, rotateSpeed);
    }

    public boolean checkIfInRange(double DH){
        double heading = robot.gyro.getHeading(AngleUnit.DEGREES)-(90);
        if(DH != 180){
            return Math.abs(DH - heading) < TURN_TOLERANCE;
        } else{
            return Math.abs(180 - heading) < TURN_TOLERANCE || Math.abs(-180 - heading) < TURN_TOLERANCE;
        }

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

    public double getHeading(){
        return robot.gyro.getHeading(AngleUnit.DEGREES);
    }

    public double getHeading(AngleUnit au){
        return robot.gyro.getHeading(au);
    }

    private double getForwardFromOrthogonal(Polar orthogonal){
        double theta = orthogonal.getTheta(AngleUnit.RADIANS);
        double r = orthogonal.getR();
        if(theta>=PI/4&&theta<=3*PI/4){ //45-135
            return r;
        }
        if(theta<=-PI/4&&theta>=-3*PI/4){ //-45 to -135
            return -r;
        }
        return 0;
    }

    private double getRightFromOrthogonal(Polar orthogonal){
        double theta = orthogonal.getTheta(AngleUnit.RADIANS);
        double r = orthogonal.getR();
        if((theta<(-3*PI/4) && theta>=-PI) || (theta>(3*PI)/4 && theta<PI)){ //-135 to -180 or 135 to 180
            return -r;
        }
        if((theta>(-PI/4) && theta<=0) || (theta<PI/4 && theta>0)){ //-45 to 0 or 45 to 0
            return r;
        }
        return 0;
    }
    public void driveOrthogonalAngle(double joystickX, double joystickY,double theta){
        Polar orthogonal = new Polar(joystickX, joystickY);

        driveFieldRelativeAngle(getForwardFromOrthogonal(orthogonal), getRightFromOrthogonal(orthogonal), theta);
    }
    public void driveOrthogonal(double joystickX, double joystickY){
        Polar orthogonal = new Polar(joystickX, joystickY);

        driveFieldRelative(getForwardFromOrthogonal(orthogonal), getRightFromOrthogonal(orthogonal),0);
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

    public void setCurrentPosition(RobotPose pose) {
        currentPosition = pose;
        robot.mecanumDrive.setEncodeOffsets();
    }


    public boolean rotateTo(double angle, AngleUnit au) {
        double rotateSpeed;
        double MIN_TURNING_SPEED = 0.1;//0.1
        double KP_ANGLE = 0.015;//0.008
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

    public boolean driveTo(NavigationPose desiredPose){
        Polar drive;
        double rotateSpeed;
        boolean hasDistanceOffset;
        boolean hasAngleOffset;

        if (desiredPose.inDistanceTolerance(currentPosition)){
            drive = new Polar(0, AngleUnit.RADIANS, 0, DistanceUnit.CM);
            hasDistanceOffset= true;
        } else {
            Polar distance = currentPosition.getTranslateDistance(desiredPose);

            double newR = Math.min(Math.max((distance.getR(DistanceUnit.CM) * TRANSLATE_KP), desiredPose.getMinSpeed()), desiredPose.getMaxSpeed());

            drive = new Polar(distance.getTheta(AngleUnit.RADIANS), AngleUnit.RADIANS, newR, DistanceUnit.CM);
            hasDistanceOffset = false;
        }

        if(desiredPose.inAngleTolerance(currentPosition)){
            rotateSpeed = 0.0;
            hasAngleOffset = true;
        } else {
            double angleDelta = desiredPose.getAngleDistance(currentPosition, AngleUnit.RADIANS);
            rotateSpeed = Math.signum(angleDelta) * Math.max(Math.min(Math.abs(angleDelta * ROTATE_KP), MAX_ROTATE_SPEED), MIN_ROTATE_SPEED);
            hasAngleOffset = false;
        }

        if(hasDistanceOffset && hasAngleOffset){
            driveFieldRelative(0, 0, 0);
            return true;
        }
        //System.out.printf("%s -> %s: %f %f\n", currentPosition, desiredPose, drive.getX(), drive.getY());
        driveFieldRelative(drive.getY(), drive.getX(),rotateSpeed);

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
    public void updatePose() {
        MoveDeltas movement = robot.mecanumDrive.getDistance(true);

        currentPosition.setAngle(getHeading(AngleUnit.RADIANS), AngleUnit.RADIANS);
        currentPosition.updatePose(movement);
    }
    public void updatePoseFromOdometry(){

    }

    public static RobotPose getCurrentPosition() {
        return currentPosition;
    }


}
