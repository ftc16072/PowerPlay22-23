package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveBackwardAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveForwardAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveLeftAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveRightAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.QQAction;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.MecanumDrive;
import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationMecanum;

@Autonomous
public class RedParkAuto extends QQOpMode{
    NavigationMecanum nav = new NavigationMecanum(robot);
    QQAction currentAction;
    int parkingZone = 1;

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void start(){
        if(parkingZone==1){
            currentAction = new DriveForwardAction(24, DistanceUnit.INCH);
            currentAction.setLast(new DriveLeftAction(36, DistanceUnit.INCH)); // goes to 1st parking zone
        }
        if(parkingZone==2){
            currentAction = new DriveLeftAction(36, DistanceUnit.INCH); //goes to 2nd parking zone
        }
        if(parkingZone==3){
            currentAction = new DriveBackwardAction(24,DistanceUnit.INCH);
            currentAction.setLast(new DriveLeftAction(36, DistanceUnit.INCH)); // goes to 3rd parking zone
        }
    }

    @Override
    public void loop(){
        if(currentAction!=null){
            currentAction = currentAction.run(this);
        }
    }
}
