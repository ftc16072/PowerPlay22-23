package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.Lift;
import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationMecanum;

@TeleOp()
public class Teleop extends QQOpMode {
    NavigationMecanum nav = new NavigationMecanum(robot);
    private boolean wasLeftTriggered;
    private boolean wasRightTriggered;
    private boolean isTurning;
    private boolean wasUp;
    private boolean wasDown;
    private double desiredHeading;
    private final int CHANGE_AMOUNT = 5;


// Control scheme
// left stick = strafing
// right stick = turning


// a = intake position(lift)
// x = low position(lift)
// y = medium position(lift)
// b = high position(lift)
// d pad up = manual lift up
// d pad down = manual lift down
// d pad right = stop manual lift
// right bumper = grip (claw)
// left bumper = release (claw)

    // right trigger = turn 90 degrees robot CW
// left trigger = turn 90 degrees robot CCW
    @Override
    public void loop() {
        boolean doneTurning;
        //driver controls
        nav.driveFieldRelative(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
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
