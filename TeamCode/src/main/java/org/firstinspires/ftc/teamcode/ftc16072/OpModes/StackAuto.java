package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ftc16072.actions.ChangeLiftAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveToAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DualAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.GripClaw;
import org.firstinspires.ftc.teamcode.ftc16072.actions.HorizontalSlides;
import org.firstinspires.ftc.teamcode.ftc16072.actions.ReleaseClaw;
import org.firstinspires.ftc.teamcode.ftc16072.actions.RotateAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.delayAction;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.HorizontalSlides.Position;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.Lift;
import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationPose;
import org.firstinspires.ftc.teamcode.ftc16072.util.RobotPose;

@Autonomous
public class StackAuto extends AutoBase {
    @Override
    public void init(){
        super.init();
        isLeft = true;
    }
    public void start(){
        super.start();
        //currentAction = new DriveToAction("drive to high goal",new NavigationPose(-31.5,58,0))
               // .SetNext(new RotateAction("turn to junction", -45,AngleUnit.DEGREES))
        ;

       nav.setCurrentPosition(new RobotPose(-30.5, 70, DistanceUnit.INCH, 0, AngleUnit.DEGREES));

        currentAction = new RotateAction("rotate towards stack", 90, AngleUnit.DEGREES);
        currentAction.setNext(new DualAction("drive to stack and lift and open claw",
                        new DriveToAction("drive to stack",new NavigationPose(-47,70,90)),
                        new DualAction("lift and open claw",
                                new ChangeLiftAction("lift to top of stack", Lift.Level.CONE_FIVE_STACK),
                                new ReleaseClaw())))
                .setNext(new HorizontalSlides("move out to grab cones", Position.FRONT))
                .setNext(new delayAction(0.5))
                .setNext(new GripClaw())
                .setNext(new delayAction(0.5))
                .setNext(new DualAction("lift cone off stack and back up",
                        new ChangeLiftAction("lift cone of stack", Lift.Level.MIDDLE),
                        new DriveToAction("drive to junction",new NavigationPose(-30.5,70,90))))
                .setNext(new DualAction("turn lift and slides",
                        new RotateAction("turn so back faces junction",135,AngleUnit.DEGREES),
                        new DualAction("move slides and lift",
                                new HorizontalSlides("move slides back to place on high junction", Position.BACK),
                                new ChangeLiftAction("move lift to high", Lift.Level.HIGH))))
                .setNext(new DriveToAction("back up to junction",new NavigationPose(-26.5,74,135)))
                .setNext(new delayAction(0.5))
                .setNext(new ReleaseClaw())
                .setNext(new delayAction(0.5))
                .setNext(new GripClaw())
                .setNext(new HorizontalSlides("bring slides to middle", Position.MIDDLE))
                .setNext(new DualAction("lower lift and drive backward",
                        new ChangeLiftAction("lower lift to cones", Lift.Level.CONE_FOUR_STACK),
                        new DriveToAction("drive backward",new NavigationPose(-30.5,70,135))))
                .setNext(new RotateAction("turn to cones",90, AngleUnit.DEGREES))
                .setNext(new DriveToAction("drive up to cones",new NavigationPose(-47,70,90)));
    }
}
