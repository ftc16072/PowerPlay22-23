package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ftc16072.Robot;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveBackwardAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveForwardAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveRightAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.QQAction;
import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationMecanum;

@Autonomous
public class ParkAuto extends QQOpMode {
    NavigationMecanum nav = new NavigationMecanum(robot);
    QQAction currentAction;
    int parkingZone = 1;
    @Override
    public void init(){
        super.init();

    }
    @Override
    public void start(){
        if(parkingZone==1){
            currentAction = new DriveForwardAction(24,DistanceUnit.INCH);
            currentAction.setLast(new DriveRightAction(36, DistanceUnit.INCH));
        }
        else if(parkingZone==2){
            currentAction = new DriveRightAction(36, DistanceUnit.INCH);
        }
        else{
            currentAction = new DriveBackwardAction(24,DistanceUnit.INCH);
            currentAction.setLast(new DriveRightAction(36, DistanceUnit.INCH));
        }
    }


    @Override
    public void loop() {
        if(currentAction!=null){
            currentAction = currentAction.run(this);
        }
    }
}
