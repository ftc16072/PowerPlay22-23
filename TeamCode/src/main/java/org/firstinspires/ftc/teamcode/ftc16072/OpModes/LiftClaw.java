package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.Lift;
import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationMecanum;


@TeleOp()
public class LiftClaw extends QQOpMode {
    public static final int CHANGE_AMOUNT = 5;
    boolean wasDown;
    boolean wasUp;
    FtcDashboard ftcDashboard = FtcDashboard.getInstance();

    @Override
    public void loop() {
        if (gamepad1.right_bumper){
            robot.claw.grip();
        }else if (gamepad1.left_bumper){
            robot.claw.release();
        }

        if (gamepad1.a){
            robot.lift.goTo(Lift.Level.INTAKE);
        }
        else if (gamepad1.x){
            robot.lift.goTo(Lift.Level.LOW);
        }
        else if (gamepad1.y){
            robot.lift.goTo(Lift.Level.MIDDLE);
        }
        else if (gamepad1.b){
            robot.lift.goTo(Lift.Level.HIGH);
        }
        else if (gamepad1.dpad_up){
            robot.lift.adjustPosition(CHANGE_AMOUNT);
        }
        else if (gamepad1.dpad_down){
            robot.lift.adjustPosition(-CHANGE_AMOUNT);
        }
        else{
            if(wasUp || wasDown) {
                robot.lift.stopMotor();
            }
        }
        wasUp = gamepad1.dpad_up;
        wasDown = gamepad1.dpad_down;

    /*
        if (gamepad1.dpad_up){
            robot.lift.extend(0.5);
        }
        else if(gamepad1.dpad_down){
            robot.lift.retract(0.5);
        }
        else{
            robot.lift.stopMotor();
        }
*/
        robot.lift.update();

        TelemetryPacket packet = new TelemetryPacket();
        packet.put("Lift target", robot.lift.desiredPosition);
        packet.put("Lift Pos", robot.lift.liftMotor.getCurrentPosition());
        ftcDashboard.sendTelemetryPacket(packet);
    }
}
