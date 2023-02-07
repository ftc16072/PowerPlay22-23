package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ftc16072.actions.QQAction;
import org.firstinspires.ftc.teamcode.ftc16072.util.RobotPose;


import java.util.ArrayList;

abstract public class AutoBase extends QQOpMode {
    QQAction currentAction;
    boolean isLeft; //have to initialize
    boolean isPrimary; //primary - closer high goal, secondary - further
    boolean aPressed;
    boolean xPressed;

    @Override
    public void init() {
        super.init();
    }


    @Override
    public void init_loop(){
        super.init_loop();

        if(gamepad1.a && !aPressed) {
            isLeft = !isLeft;
        }
        aPressed = gamepad1.a;

        if(gamepad1.x && !xPressed){
            isPrimary = !isPrimary;
        }
        xPressed = gamepad1.x;
        telemetry.addData("isLeft", isLeft);
        telemetry.addData("isPrimary", isPrimary);
        telemetry.addData("Gyro Heading:", robot.gyro.getHeading(AngleUnit.DEGREES));

    }

    @Override
    public void start() {
        double startXLocation = isLeft? -36 +5.5 : 36 -5.5;
        nav.setCurrentPosition(new RobotPose(startXLocation,22, DistanceUnit.INCH, 0, AngleUnit.DEGREES));
    }

    @Override
    public void loop() {
        super.loop();
        if (currentAction != null) {
            telemetry.addData("Action", currentAction.getDescription());
            currentAction = currentAction.run(this);
        }

        telemetry.addData("Gyro Heading:", robot.gyro.getHeading(AngleUnit.DEGREES));

        RobotPose robotPose = nav.getCurrentPosition();
        telemetry.addData("Robot Position X:", robotPose.getX(DistanceUnit.INCH));
        telemetry.addData("Robot Position Y:", robotPose.getY(DistanceUnit.INCH));

        telemetry.addData("Left lift position", robot.lift.leftLiftMotor.getCurrentPosition());
        telemetry.addData("Right lift position", robot.lift.rightLiftMotor.getCurrentPosition());
    }

}
