package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.internal.android.dx.ssa.DomFront;
import org.firstinspires.ftc.teamcode.ftc16072.actions.ChangeLiftAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.HorizontalSlides;

@Autonomous
public class StackTestAuto extends VisionAutoBase {
    @Override
    public void start(){
        return new ChangeLiftAction("move lift up", 400 )
                .setNext(HorizontalSlides("Move slides out", ))
    }
}
