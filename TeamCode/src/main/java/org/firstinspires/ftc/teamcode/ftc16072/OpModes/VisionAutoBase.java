package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ftc16072.Robot;
import org.firstinspires.ftc.teamcode.ftc16072.actions.QQAction;
import org.firstinspires.ftc.teamcode.ftc16072.util.RobotPose;

abstract public class VisionAutoBase extends QQOpMode {
    //SignalSleevePipeline signalSleevePipeline = new SignalSleevePipeline();
    //QQAprilTag aprilTagPipeline = new QQAprilTag(0.015, 578.272, 578.272, 402.145, 221.506);
    QQAction currentAction;
    boolean isLeft; //have to initialize
    boolean isPrimary; //primary - closer high goal, secondary - further
    boolean bPressed;
    boolean xPressed;
    boolean yPressed;
    boolean test;

    @Override
    public void init() {
        super.init();

    }

    @Override
    public void init_loop() {
        super.init_loop();


        telemetry.addData("Parking Zone", parkingZone);
        telemetry.addData("Placing Left", isLeft);
        telemetry.addData("Primary Strategy", isPrimary);
        telemetry.addData("Red Alliance", Robot.isRed);

        //nav.setCurrentPosition(new RobotPose());

        if(gamepad1.b && !bPressed) {
            isLeft = !isLeft;
        }
        bPressed = gamepad1.b;

        if(gamepad1.x && !xPressed){
            isPrimary = !isPrimary;
        }
        xPressed = gamepad1.x;

        if(gamepad1.y && !yPressed){
            parkingZone += 1;
            if(parkingZone >= 4){
                parkingZone = 1;
            }
        }
        yPressed = gamepad1.y;


    }

    @Override
    public void start() {
        int startXLocation = isLeft? -40 : 32;
        //NavigationMecanum.currentPosition = new RobotPose(startXLocation,8, DistanceUnit.INCH, 0, AngleUnit.DEGREES);
        nav.setCurrentPosition(new RobotPose(startXLocation,8, DistanceUnit.INCH, 0, AngleUnit.DEGREES));
    }

    @Override
    public void loop() {
        super.loop();
        if (currentAction != null) {
            currentAction = currentAction.run(this);
            telemetry.addData("Action", currentAction.getDescription());
        }
    }
}