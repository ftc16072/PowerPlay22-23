package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.Lift;
import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationMecanum;

@TeleOp()
public class LiftClaw extends QQOpMode {
    boolean wasDown;
    boolean wasUp;

    @Override
    public void loop() {
        if (gamepad1.a){
            robot.claw.grip();
        }else if (gamepad1.b){
            robot.claw.release();
        }
        if (gamepad1.dpad_down && !wasDown){
            robot.lift.goTo(Lift.Level.INTAKE);
            telemetry.addData("Going to", "Intake");
        } else if (gamepad1.dpad_up && !wasUp){
            robot.lift.goTo(Lift.Level.HIGH);
            telemetry.addData("Going to", "High");
        }
        wasDown = gamepad1.dpad_down;
        wasUp = gamepad1.dpad_up;

        telemetry.addData("Lift motor position", robot.lift.liftMotor.getCurrentPosition());
        telemetry.addData("Lift motor target", robot.lift.liftMotor.getTargetPosition());
        telemetry.addData("Lift PIDF", robot.lift.liftMotor.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION));


    }
}
