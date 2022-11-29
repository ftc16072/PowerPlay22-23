package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.Lift;
import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationMecanum;
import org.firstinspires.ftc.teamcode.ftc16072.util.SafeChecker;

@TeleOp()
public class Teleop extends QQOpMode {
    NavigationMecanum nav = new NavigationMecanum(robot);
    SafeChecker sc = new SafeChecker(robot);
    private boolean wasLeftTriggered;
    private boolean wasRightTriggered;
    private boolean isTurning;
    private boolean wasUp;
    private boolean wasDown;
    private double desiredHeading;
    private final int CHANGE_AMOUNT = 5;


// Gamepad 1
// y = reset mechanisms
// left trigger = diagonal toggle
// left stick == driving
// right stick = turning
// gamepad 2
// a = intake position
// x = low position
// y = medium position
// b = high position
// left bumper = ground position
// left dpad = moves horizontal slides backward
//right dpad = moves horizontal slides forward
// left stick = manual horizontal lift
// right stick = manual vertical lift
// right trigger = claw

    public void driving_loop(Gamepad gamepad){

    }

    public void manipulator_loop(Gamepad gamepad){
        if (gamepad.a) {
            sc.moveVerticalLift(Lift.Level.INTAKE);
            //robot.lift.goTo(Lift.Level.INTAKE);
        } else if (gamepad.x) {
            robot.lift.goTo(Lift.Level.LOW);
        } else if (gamepad.y) {
            robot.lift.goTo(Lift.Level.MIDDLE);
        } else if (gamepad.b) {
            robot.lift.goTo(Lift.Level.HIGH);
        } else if (gamepad.right_stick_y > 0.1) {
            robot.lift.adjustPosition(CHANGE_AMOUNT);
        } else if (gamepad.right_stick_y < 0.1) {
            robot.lift.adjustPosition(-CHANGE_AMOUNT);
        } else if (gamepad.right_trigger>0.2){
            robot.claw.release();
        } else if (gamepad.right_trigger<=0.2){
            robot.claw.grip();
        } else if (gamepad.left_stick_x > 0.1){

        }
    }


    @Override
    public void loop() {
        driving_loop(gamepad1);
        manipulator_loop(gamepad2);
        boolean doneTurning;
        //driver controls`
        //nav.driveFieldRelative(-gamepad1.left_stick_y*0.75, gamepad1.left_stick_x*0.75, gamepad1.right_stick_x*0.75);


        if(gamepad1.dpad_left){
            nav.driveFieldRelative(-gamepad1.left_stick_y*0.75, gamepad1.left_stick_x*0.75, gamepad1.right_stick_x*0.75);
        }
        else{
            nav.driveOrthogonal(gamepad1.left_stick_x*0.75, -gamepad1.left_stick_y*0.75);
        }

        if (gamepad1.a) {
            robot.lift.goTo(Lift.Level.INTAKE);
        } else if (gamepad1.x) {
            robot.lift.goTo(Lift.Level.LOW);
        } else if (gamepad1.y) {
            robot.lift.goTo(Lift.Level.MIDDLE);
        } else if (gamepad1.b) {
            robot.lift.goTo(Lift.Level.HIGH);
        } else if (gamepad1.dpad_up) {
            robot.lift.adjustPosition(CHANGE_AMOUNT);
        } else if (gamepad1.dpad_down) {
            robot.lift.adjustPosition(-CHANGE_AMOUNT);
        } else {
            if (wasUp || wasDown) {
                robot.lift.stopMotor();
            }
        }
        wasUp = gamepad1.dpad_up;
        wasDown = gamepad1.dpad_down;

        if (gamepad1.right_bumper) {
            robot.claw.grip();
        } else if (gamepad1.left_bumper) {
            robot.claw.release();
        }
        if (!wasRightTriggered && (gamepad1.right_trigger > 0.5)) {
            if (!isTurning) {
                desiredHeading = nav.getSnapCW();
            }
            isTurning = true;
        } else if (!wasLeftTriggered && (gamepad1.left_trigger > 0.5)) {
            if (!isTurning) {
                desiredHeading = nav.getSnapCCW();
            }
            isTurning = true;
        }
        wasRightTriggered = (gamepad1.right_trigger > 0.5);
        wasLeftTriggered = (gamepad1.left_trigger > 0.5);

        if (isTurning) {
            doneTurning = nav.rotateTo(desiredHeading, AngleUnit.DEGREES);
            if (doneTurning) {
                isTurning = false;
            }
        }

        robot.lift.update();

//        if(gamepad1.right_trigger > 0.5){
//            nav.rotateTo(-90, AngleUnit.DEGREES);
//        }
//        else if(gamepad1.right_trigger> 0.5){
//            nav.rotateTo(90,AngleUnit.DEGREES);
//        }
    }
}
