package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.ftc16072.Robot;
import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationMecanum;

@TeleOp()
public class Teleop extends OpMode {
    Robot robot = new Robot();
    NavigationMecanum nav = new NavigationMecanum(robot);
   // int[] liftLevels = {0,1,2,3,4};
    int liftLevel = 0;
    boolean clawOpen = false;
    boolean wasUp, wasDown;
    boolean LSD, RSD; //LSD=Left stick down, RSD = right stick down


    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    private void overrideLoop(){
        //manual override for lift
        if(gamepad1.dpad_up){
            robot.lift.extend(0.5);
        }else{
            robot.lift.stopMotor();
        }

        if(gamepad1.dpad_down){
            robot.lift.retract(0.5);
        }else{
            robot.lift.stopMotor();
        }
    }
    private void normalLoop(){
        //lift up and down - snapping on levels
        if (gamepad1.dpad_down && !wasDown) {
            //go to previous
        }
        wasDown = gamepad1.dpad_down;

        if (gamepad1.dpad_up && !wasUp) {
            //go to next
        }
        wasUp = gamepad1.dpad_up;

    }

    @Override
    public void loop() {
        if(gamepad1.b){
            overrideLoop();
        }
        else{
            normalLoop();
        }




        //claw
        if(gamepad1.right_bumper){
            if(clawOpen == false){
                robot.claw.grip();
                clawOpen = true;
            }else{
                robot.claw.release();
                clawOpen = false;
            }
        }

        //snap turns
        if(gamepad1.left_stick_button && !LSD){
            nav.snapTurns(-gamepad1.left_stick_y, gamepad1.left_stick_x,true);
        }
        LSD = gamepad1.left_stick_button;
        if(gamepad1.right_stick_button && !RSD){
            nav.snapTurns(-gamepad1.left_stick_y, gamepad1.left_stick_x,false);
        }
        RSD = gamepad1.right_stick_button;

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
