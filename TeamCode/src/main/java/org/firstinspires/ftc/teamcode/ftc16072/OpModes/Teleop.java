package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.HorizontalSlides;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.Lift;
import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationMecanum;
import org.firstinspires.ftc.teamcode.ftc16072.util.SafeChecker;

@TeleOp()
public class Teleop extends QQOpMode {
    public static final double TRIGGER_THRESHOLD = 0.2;
    NavigationMecanum nav = new NavigationMecanum(robot);
    private boolean wasLeftTriggered;
    private boolean wasRightTriggered;
    private boolean isTurning;
    private boolean wasUp;
    private boolean wasDown;
    private boolean isInOrthogonal;
    private double desiredHeading;
    private SafeChecker sc = new SafeChecker(robot);
    private final int LIFT_CHANGE_AMOUNT = 30;
    private final double HORIZONTAL_SLIDES_CHANGE_AMOUNT = 0.1;
    private boolean dpadIsPressed = false;



// Control scheme
// left stick = strafing
// right stick = turning


    public void driving_loop(Gamepad gamepad) {

        double rotateSpeed = 0;
        if (gamepad.right_trigger < 0.2) {
            isInOrthogonal = false;
            rotateSpeed = gamepad1.right_stick_x * 0.75;
        } else if (gamepad.right_trigger >= 0.2) {
            telemetry.addData("here", "orthogonal driving");
            nav.driveOrthogonal(gamepad.left_stick_x, gamepad.left_stick_y);
            isInOrthogonal = true;
            rotateSpeed = 0;
        }

        if (gamepad.y){
            nav.resetGyro();
            telemetry.addData("gyro reset: ", "yes");
        }
        else
            telemetry.addData("gyro reset:", "no");

// a = intake position(lift)
// x = low position(lift)
// y = medium position(lift)
// b = high position(lift)
// d pad up = manual lift up
// d pad down = manual lift down
// d pad right = stop manual lift
// right bumper = grip (claw)
// left bumper = release (claw)


        if (gamepad.dpad_up) {
            dpadIsPressed = true;
            desiredHeading = 90;
            isTurning = true;
        } else if (gamepad.dpad_left) {
            desiredHeading = 180;
            isTurning = true;

            dpadIsPressed = true;
        } else if (gamepad.dpad_right) {
            desiredHeading = 0;
            isTurning = true;

        } else if (gamepad.dpad_down) {
            desiredHeading = -90;
            isTurning = true;
        }

        if (isTurning && !isInOrthogonal && dpadIsPressed) {
            telemetry.addData("here", "snap turns");
            boolean doneTurning = nav.rotateTo(desiredHeading, AngleUnit.DEGREES);
            if (doneTurning){
                isTurning = false;
            }

        } else if (!isInOrthogonal) {
            telemetry.addData("here", "field relative driving");
            nav.driveFieldRelative(-gamepad1.left_stick_y * 0.75, gamepad1.left_stick_x * 0.75, rotateSpeed);

        }
        wasUp = gamepad1.dpad_up;
        wasDown = gamepad1.dpad_down;



    }

    public void manipulator_loop(Gamepad gamepad) {
        boolean warn = false;
        if (gamepad.a) {
            if (gamepad.right_bumper){
                warn = sc.moveVerticalLift(Lift.Level.BOTTOM);
            }else {
                warn = sc.moveVerticalLift(Lift.Level.INTAKE);
            }
        } else if (gamepad.x) {
            warn = sc.moveVerticalLift(Lift.Level.LOW);
        } else if (gamepad.y) {
            warn = sc.moveVerticalLift(Lift.Level.MIDDLE);
        } else if (gamepad.b) {
            warn = sc.moveVerticalLift(Lift.Level.HIGH);
        } else if (gamepad.right_bumper && gamepad.right_trigger > TRIGGER_THRESHOLD) {
            warn = sc.moveVerticalLift(Lift.Level.GROUND);
        } else if (gamepad.right_stick_y < -0.1) {
            warn = sc.moveVerticalLiftManually(LIFT_CHANGE_AMOUNT);
        } else if (gamepad.right_stick_y > 0.1) {
            warn = sc.moveVerticalLiftManually(-LIFT_CHANGE_AMOUNT);
        }



        if (gamepad.dpad_up) {
            warn = sc.moveHorizontalSlides(HorizontalSlides.Position.FRONT);
        } else if (gamepad.dpad_down) {
            warn = sc.moveHorizontalSlides(HorizontalSlides.Position.BACK);
        } else if (gamepad.dpad_left || gamepad.dpad_right) {
            warn = sc.moveHorizontalSlides(HorizontalSlides.Position.MIDDLE);
        } else if (gamepad.left_bumper) {
            warn = sc.moveHorizontalSlidesManually(gamepad.left_stick_x);
        }

        if (!sc.isCorrectCone()){
            warn = true;
            telemetry.addData("Wrong Cone", robot.claw.getConeType());
            telemetry.addData("Wanted Red", robot.isRed );
        }
        if (gamepad.left_trigger > TRIGGER_THRESHOLD) {
            robot.claw.release();
        }else if (!sc.isCorrectCone()){
            robot.claw.release();
        } else if (robot.claw.isGripable()) {
            robot.claw.grip();
        }
        if (gamepad.right_trigger > TRIGGER_THRESHOLD) {
            robot.claw.grip();
        }

        telemetry.addData("Gamepad", gamepad);
        telemetry.addData("blocked", warn);
        telemetry.addData("Desired Lift", robot.horizontalSlides.getSlidesPosition());
        if(warn){
            gamepad.rumble(100);
        }
        robot.lift.update();
        telemetry.addData("Left lift position", robot.lift.leftLiftMotor.getCurrentPosition());
        telemetry.addData("Right lift position", robot.lift.rightLiftMotor.getCurrentPosition());


    }


    @Override
    public void loop() {
        driving_loop(gamepad1);
        manipulator_loop(gamepad2);


        robot.lift.update();

    }
}
