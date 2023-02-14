package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.ftc16072.actions.BasedOnZone;
import org.firstinspires.ftc.teamcode.ftc16072.actions.ChangeLiftAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveToAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DualAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.GripClaw;
import org.firstinspires.ftc.teamcode.ftc16072.actions.HorizontalSlides;
import org.firstinspires.ftc.teamcode.ftc16072.actions.QQAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.ReleaseClaw;
import org.firstinspires.ftc.teamcode.ftc16072.actions.RotateAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.delayAction;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.HorizontalSlides.Position;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.Lift;
import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationPose;

@Autonomous
public class RightStackAuto extends VisionAutoBase {
    @Override
    public void init(){
        super.init();
        isLeft = false;
    }
    private QQAction stackToJunction(QQAction before, double theta){
        return before.setNext(new HorizontalSlides("move out to grab cones", Position.FRONT))
                .setNext(new delayAction(0.75))
                .setNext(new GripClaw())
                .setNext(new delayAction(0.5))
                .setNext(new DualAction("lift cone off stack and back up",
                        new ChangeLiftAction("lift cone of stack", Lift.Level.MIDDLE),
                        new DriveToAction("drive to junction",new NavigationPose(31.5,60,-90))))
                .setNext(new DualAction("turn lift and slides",
                        new RotateAction("turn so back faces junction",theta,AngleUnit.DEGREES),
                        new DualAction("move slides and lift",
                                new HorizontalSlides("move slides back to place on high junction", Position.BACK),
                                new ChangeLiftAction("move lift to high", Lift.Level.HIGH))))
                .setNext(new DriveToAction("back up to junction",new NavigationPose(26.5,67,-135)))
                .setNext(new delayAction(0.5))
                .setNext(new ReleaseClaw())
                .setNext(new delayAction(0.5))
                .setNext(new GripClaw())
                .setNext(new HorizontalSlides("bring slides to middle", Position.MIDDLE))
                .setNext(new DriveToAction("drive backward",new NavigationPose(31.5,60,-135)))
                .setNext(new DualAction("turn and lower lift",
                        new RotateAction("turn to cones",-90, AngleUnit.DEGREES),
                        new ChangeLiftAction("lower lift to cones", Lift.Level.CONE_FOUR_STACK)))
                .setNext(new RotateAction("turn to cones",-90, AngleUnit.DEGREES));
    }
    public void start(){
        super.start();
        currentAction = new DualAction("drive forward and lift",
                new DriveToAction("deposit signal sleeve",new NavigationPose(31.5,70,0)),
                new ChangeLiftAction("lift to high", Lift.Level.HIGH));


        QQAction insert = currentAction
                .setNext(new DriveToAction("drive to high goal",new NavigationPose(31.5,60,0)))
                .setNext(new RotateAction("turn to junction", 45,AngleUnit.DEGREES))
                .setNext(new DualAction("extend slides and turn",
                        new HorizontalSlides("bring to front",Position.FRONT),
                        new DriveToAction("move up to junction", new NavigationPose(28,63,45))))
                .setNext(new ReleaseClaw())
                .setNext(new delayAction(0.5))
                .setNext(new DualAction("slides to mid, lift to stack, back away from junction",
                        new HorizontalSlides("slides to mid",Position.MIDDLE),
                        new DualAction("lift to stack, back away from junction",
                                new ChangeLiftAction("lift to stack", Lift.Level.CONE_FIVE_STACK),
                                new DriveToAction("back away from junction",new NavigationPose(31.5,60,45)))))

                .setNext(new RotateAction("rotate towards stack", -90, AngleUnit.DEGREES))
                .setNext(new DriveToAction("drive to stack",new NavigationPose(47,62,-90)));
        insert = stackToJunction(insert,-135)
                .setNext(new ReleaseClaw())
                .setNext(new DriveToAction("drive up to cones",new NavigationPose(49,62,-90)));
        stackToJunction(insert, -140)
                .setNext(new DualAction("lift to ground",
                        new BasedOnZone("park",new DriveToAction("park left",new NavigationPose(49,65,-90)),new delayAction(0.5),new DriveToAction("park right",new NavigationPose(15,65,-90))),
                        new ChangeLiftAction("lift to ground", Lift.Level.BOTTOM)));
    }
}
