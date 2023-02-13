package org.firstinspires.ftc.teamcode.ftc16072.OpModes;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.ftc16072.OpModes.VisionAutoBase;
import org.firstinspires.ftc.teamcode.ftc16072.actions.ChangeLiftAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveToAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DualAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.QQAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.RotateAction;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.Lift;
import org.firstinspires.ftc.teamcode.ftc16072.util.MoveDeltas;
import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationPose;
import org.firstinspires.ftc.teamcode.ftc16072.util.RobotPose;

import java.util.Currency;

@Autonomous
public class OdometryTestOpmode extends VisionAutoBase {


    public void start() {
        super.start();

        currentAction = new DriveToAction("test", new NavigationPose(30.5 , 60, 0));
        QQAction insert = currentAction
                .setNext(new RotateAction("turn to junction", -45, AngleUnit.DEGREES))
                .setNext(new DriveToAction("test", new NavigationPose(30.5 , 22, 0)));


    }



}
