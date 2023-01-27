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
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.Lift;
import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationPose;
import org.firstinspires.ftc.teamcode.ftc16072.util.RobotPose;

@Autonomous
public class StackTestAuto extends AutoBase {
    @Override
    public void start(){
        super.start();
        nav.setCurrentPosition(new RobotPose(-30.5, 70, DistanceUnit.INCH, 0, AngleUnit.DEGREES));
        currentAction = new RotateAction("rotate towards stack", 90,AngleUnit.DEGREES)
                .setNext(new DualAction("drive to stack and lift and open claw",new DriveToAction("drive to stack",new NavigationPose(-47,70,90)),new DualAction("lift and open claw",new ChangeLiftAction("lift to top of stack", Lift.Level.CONE_FIVE_STACK),new ReleaseClaw()))
                                .setNext(new HorizontalSlides("move out to grab cones", org.firstinspires.ftc.teamcode.ftc16072.mechanisms.HorizontalSlides.Position.FRONT)
                                        .setNext(new delayAction(0.5)
                                                .setNext(new GripClaw()
                                                        .setNext(new delayAction(0.5)
                                                            .setNext(new ChangeLiftAction("lift cone of stack", Lift.Level.MIDDLE)
                                                                            .setNext(new DriveToAction("drive to junction",new NavigationPose(-30.5,70,90))
                                                                                    .setNext(new RotateAction("turn so back faces junction",135,AngleUnit.DEGREES)
                                                                                            .setNext(new HorizontalSlides("move slides back to place on high junction", org.firstinspires.ftc.teamcode.ftc16072.mechanisms.HorizontalSlides.Position.BACK)
                                                                                                    .setNext(new ChangeLiftAction("move lift to high", Lift.Level.HIGH)
                                                                                                                    .setNext(new DriveToAction("back up to junction",new NavigationPose(-26.5,74,135))
                                                                                                                            .setNext(new delayAction(0.5)
                                                                                                                                    .setNext(new ReleaseClaw()
                                                                                                                                        .setNext(new delayAction(0.5)
                                                                                                                                                .setNext(new GripClaw()
                                                                                                                                                        .setNext(new HorizontalSlides("bring slides to middle", org.firstinspires.ftc.teamcode.ftc16072.mechanisms.HorizontalSlides.Position.MIDDLE)
                                                                                                                                                                .setNext(new ChangeLiftAction("lower lift to cones", Lift.Level.CONE_FOUR_STACK)
                                                                                                                                                                        .setNext(new DriveToAction("drive foward",new NavigationPose(-30.5,70,135))
                                                                                                                                                                                .setNext(new RotateAction("turn to cones",90, AngleUnit.DEGREES)
                                                                                                                                                                                        .setNext(new DriveToAction("drive up to cones",new NavigationPose(-47,70,90))
                                                                                                                        ))))))))))))))))))));
    }
}
