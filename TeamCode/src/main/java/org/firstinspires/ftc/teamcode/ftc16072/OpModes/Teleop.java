package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.ftc16072.Robot;
import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationMecanum;

@TeleOp()
public class DriveOnly extends OpMode {
    Robot robot = new Robot();
    NavigationMecanum nav = new NavigationMecanum(robot);
   // int[] liftLevels = {0,1,2,3,4};
    int liftLevel = 0;
    boolean clawOpen = false;


    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {
        //lift up and down - snapping on levels
        if (gamepad1.dpad.left.isPressed()) {
          liftLevel -= 1;
        }

        if (gamepad1.dpad.right.isPressed()) {
            liftLevel += 1;
        }


        if (liftLevel%5 == 0) {
            robot.lift.goToIntake(0.5);
        } else if (liftLevel%5 == 1) {
            robot.lift.goToGround(0.5);
        } else if (liftLevel%5 == 2) {
            robot.lift.goToLow(0.5);
        } else if (liftLevel%5 == 3) {
            robot.lift.goToMiddle(0.5);
        } else if (liftLevel%5 == 4) {
            robot.lift.goToHigh(0.5);
        }

        if(gamepad.dpad.up.isPressed()){
            robot.lift.extend(0.5);
        }else{
            robot.lift.stopMotor();
        }

        if(gamepad.dpad.down.isPressed()){
            robot.lift.retract(0.5);
        }else{
            robot.lift.stopMotor();
        }

        //claw
        if(gamepad1.RightTrigger){
            if(clawOpen == false){
                robot.claw.grip();
                clawOpen = true;
            }else{
                robot.claw.release();
                clawOpen = false;
            }
        }

        //snap turns
        if(gamepad.LeftStickButton){
            snapTurns(true);
        }
        if(gamepad.RightStickButton){
            snapTurns(false);
        }

        //driver controls
        nav.driveFieldRelative(-gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x);
        if (gamepad1.a) {
            robot.mecanumDrive.setEncodeOffsets();
        }

        double[] distances = robot.mecanumDrive.getDistance();
        telemetry.addData("Distance driven forward", distances[0]);
        telemetry.addData("distance strafed", distances[1]);

    }
}
