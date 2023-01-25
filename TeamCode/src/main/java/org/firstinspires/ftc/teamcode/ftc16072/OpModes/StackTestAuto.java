package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.ftc16072.actions.ChangeLiftAction;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.Lift;

@Autonomous
public class StackTestAuto extends VisionAutoBase {
    @Override
    public void start(){
        currentAction = new ChangeLiftAction("move lift up", Lift.Level.CONE_FIVE_STACK);
    }
}
