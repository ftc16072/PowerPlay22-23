package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ftc16072.Robot;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveRightAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.QQAction;
import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationMecanum;

@Autonomous
public class ParkAuto extends QQOpMode {
    NavigationMecanum nav = new NavigationMecanum(robot);
    QQAction currentAction;
    @Override
    public void init(){
        super.init();
        currentAction = new DriveRightAction(36, DistanceUnit.INCH);
    }


    @Override
    public void loop() {
        if(currentAction!=null){
            currentAction = currentAction.run(this);
        }
    }
}
