package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.HorizontalSlides;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.Lift;
import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationMecanum;
import org.firstinspires.ftc.teamcode.ftc16072.util.SafeChecker;

@TeleOp()
public class Teleop extends QQOpMode {
    NavigationMecanum nav = new NavigationMecanum(robot);
    SafeChecker sc = new SafeChecker(robot);
    private boolean wasLeftTriggered;
    private boolean wasRightTriggered;
    private boolean isTurning = false;
    private boolean isInOrthogonal = false;
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
// left dpad = horizontal lift back position
    // up dpad = horizontal lift middle position
    // right dpad = horizontal lift front position
// left stick = manual horizontal lift
// right stick = manual vertical lift
// right trigger = claw toggle

    public void driving_loop(Gamepad gamepad){
//        double rotateSpeed = 0;
//        if(gamepad.right_trigger < 0.2){
//            rotateSpeed = gamepad1.right_stick_x*0.75;
//        } else if(gamepad.right_trigger >= 0.2){
//            rotateSpeed = 0;
//        }
        double rotateSpeed = 0;
        if(gamepad.right_trigger < 0.2){
            isInOrthogonal = false;
            rotateSpeed = gamepad1.right_stick_x*0.75;
        } else if(gamepad.right_trigger >= 0.2){
            telemetry.addData("here", "orthogonal driving");
            nav.driveOrthogonal(gamepad.left_stick_x, gamepad.left_stick_y);
            isInOrthogonal = true;
            rotateSpeed = 0;
        }

//        if(gamepad.){
//            //diagonal toggle - should only rotate if toggle is pressed
//            if(gamepad.left_trigger > 0.2){
//                nav.driveOrthogonal(gamepad.left_stick_x*0.75, -gamepad.left_stick_y*0.75);
//            } else if(gamepad.left_trigger <= 0.2){
//                nav.driveOrthogonal(0,0);
//            }
//        }

        if (gamepad.y) { //resets lift and slide mechs
            sc.reset();
        }
        if (gamepad.dpad_up){
            desiredHeading = 90;
            isTurning = true;
        } else if (gamepad.dpad_left){
            desiredHeading = 180;
            isTurning = true;
        } else if (gamepad.dpad_right){
            desiredHeading = 0;
            isTurning = true;

        } else if (gamepad.dpad_down){
            desiredHeading = -90;
            isTurning = true;
        }

//        if(dpadIsPressed){
//            isTurning = true;
//        }

        if(isTurning && !isInOrthogonal){
            telemetry.addData("here", "snap turns");
            boolean doneTurning = nav.rotateTo(desiredHeading, AngleUnit.DEGREES);
            //if(nav.checkIfInRange(desiredHeading)){//check if has reached desired range
            if (doneTurning==true){
                isTurning = false;
            }
            //isTurning = false;

            //}
        } else if(!isInOrthogonal){
            telemetry.addData("here", "field relative driving");
            nav.driveFieldRelative(-gamepad1.left_stick_y*0.75, gamepad1.left_stick_x*0.75, rotateSpeed);
        }

//          if(!nav.checkIfInRange(desiredHeading) && isTurning){
//              nav.rotateTo(desiredHeading, AngleUnit.DEGREES, 1/90);
//          }

    }

    public void manipulator_loop(Gamepad gamepad){
        if (gamepad.a) {
            sc.moveVerticalLift(Lift.Level.INTAKE);
            //robot.lift.goTo(Lift.Level.INTAKE);
        } else if (gamepad.x) {
            sc.moveVerticalLift(Lift.Level.LOW);
            //robot.lift.goTo(Lift.Level.LOW);
        } else if (gamepad.y) {
            sc.moveVerticalLift(Lift.Level.MIDDLE);
            //robot.lift.goTo(Lift.Level.MIDDLE);
        } else if (gamepad.b) {
            sc.moveVerticalLift(Lift.Level.HIGH);
            //robot.lift.goTo(Lift.Level.HIGH);
        } else if (gamepad.left_bumper){
            sc.moveVerticalLift(Lift.Level.GROUND);
        } else if (gamepad.right_stick_y >0.1) {
            sc.moveVerticalLiftManually(CHANGE_AMOUNT);
            //robot.lift.adjustPosition(CHANGE_AMOUNT);
        } else if (gamepad.right_stick_y < -0.1) {
            sc.moveVerticalLiftManually(-CHANGE_AMOUNT);
            //robot.lift.adjustPosition(-CHANGE_AMOUNT);


        } else if (gamepad.right_trigger>0.2){
            telemetry.addData("right trigger release", gamepad.right_trigger);
            robot.claw.release();

        } else if (gamepad.right_trigger<=0.2){
            telemetry.addData("right trigger grip", gamepad.right_trigger);
            robot.claw.grip();




        } else if (gamepad.left_stick_x > 0.1){
            sc.moveHorizontalSlidesManually(CHANGE_AMOUNT);
        } else if (gamepad.left_stick_x < -0.1){
            sc.moveHorizontalSlidesManually(-CHANGE_AMOUNT);
        } else if (gamepad.dpad_right){
            sc.moveHorizontalSlides(HorizontalSlides.Position.FRONT);
        } else if (gamepad.dpad_left){
            sc.moveHorizontalSlides(HorizontalSlides.Position.BACK);
        } else if (gamepad.dpad_up){
            sc.moveHorizontalSlides(HorizontalSlides.Position.MIDDLE);
        }
        robot.lift.update();
    }


    @Override
    public void loop() {
        driving_loop(gamepad1);
        manipulator_loop(gamepad2);
//        boolean doneTurning;
//        //driver controls`
//        //nav.driveFieldRelative(-gamepad1.left_stick_y*0.75, gamepad1.left_stick_x*0.75, gamepad1.right_stick_x*0.75);
//
//
//        if(gamepad1.dpad_left){
//            nav.driveFieldRelative(-gamepad1.left_stick_y*0.75, gamepad1.left_stick_x*0.75, gamepad1.right_stick_x*0.75);
//        }
//        else{
//            nav.driveOrthogonal(gamepad1.left_stick_x*0.75, -gamepad1.left_stick_y*0.75);
//        }
//
//        if (gamepad1.a) {
//            robot.lift.goTo(Lift.Level.INTAKE);
//        } else if (gamepad1.x) {
//            robot.lift.goTo(Lift.Level.LOW);
//        } else if (gamepad1.y) {
//            robot.lift.goTo(Lift.Level.MIDDLE);
//        } else if (gamepad1.b) {
//            robot.lift.goTo(Lift.Level.HIGH);
//        } else if (gamepad1.dpad_up) {
//            robot.lift.adjustPosition(CHANGE_AMOUNT);
//        } else if (gamepad1.dpad_down) {
//            robot.lift.adjustPosition(-CHANGE_AMOUNT);
//        } else {
//            if (wasUp || wasDown) {
//                robot.lift.stopMotor();
//            }
//        }
//        wasUp = gamepad1.dpad_up;
//        wasDown = gamepad1.dpad_down;
//
//        if (gamepad1.right_bumper) {
//            robot.claw.grip();
//        } else if (gamepad1.left_bumper) {
//            robot.claw.release();
//        }
//        if (!wasRightTriggered && (gamepad1.right_trigger > 0.5)) {
//            if (!isTurning) {
//                desiredHeading = nav.getSnapCW();
//            }
//            isTurning = true;
//        } else if (!wasLeftTriggered && (gamepad1.left_trigger > 0.5)) {
//            if (!isTurning) {
//                desiredHeading = nav.getSnapCCW();
//            }
//            isTurning = true;
//        }
//        wasRightTriggered = (gamepad1.right_trigger > 0.5);
//        wasLeftTriggered = (gamepad1.left_trigger > 0.5);
//
//        if (isTurning) {
//            doneTurning = nav.rotateTo(desiredHeading, AngleUnit.DEGREES);
//            if (doneTurning) {
//                isTurning = false;
//            }
//        }

//        robot.lift.update();

//        if(gamepad1.right_trigger > 0.5){
//            nav.rotateTo(-90, AngleUnit.DEGREES);
//        }
//        else if(gamepad1.right_trigger> 0.5){
//            nav.rotateTo(90,AngleUnit.DEGREES);
//        }
    }
}
