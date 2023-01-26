package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ftc16072.actions.ChangeLiftAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveToAction;
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
                .setNext(new DriveToAction("drive to stack",new NavigationPose(-42,70,90))
                );
//       currentAction = new DriveToAction("drive to stack", new NavigationPose(-32,70,0));


//                        .setNext(new ChangeLiftAction("lift to top of stack", Lift.Level.CONE_FIVE_STACK)
//                            .setNext( new ReleaseClaw()
//                                .setNext(new HorizontalSlides("move out to grab cones", org.firstinspires.ftc.teamcode.ftc16072.mechanisms.HorizontalSlides.Position.FRONT)
//                                        .setNext(new delayAction(2)
//                                                .setNext(new GripClaw()
//                                                        .setNext(new delayAction(0.5)
//                                                            .setNext(new ChangeLiftAction("lift cone of stack", Lift.Level.MIDDLE)
//                                                                    ))))))));
    }
}
