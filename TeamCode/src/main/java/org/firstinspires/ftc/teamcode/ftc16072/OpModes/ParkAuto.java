package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ftc16072.Robot;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveBackwardAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveForwardAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveRightAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.QQAction;
import org.firstinspires.ftc.teamcode.ftc16072.pipelines.SignalSleevePipeline;
import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationMecanum;

@Autonomous
public class ParkAuto extends ConeDetection {
    //NavigationMecanum nav = new NavigationMecanum(robot);
    QQAction currentAction;


    @Override
    public void start(){
        int parkingZone = super.signalSleevePipeline.numberOfDots;
        if(parkingZone==1){
            currentAction = new DriveForwardAction(24,DistanceUnit.INCH);
            currentAction.setLast(new DriveRightAction(36, DistanceUnit.INCH)); // goes to 1st parking zone
        }
        else if(parkingZone==2){
            currentAction = new DriveRightAction(36, DistanceUnit.INCH); // goes to 2nd parking zone
        }
        else if(parkingZone == 3){
            currentAction = new DriveBackwardAction(24,DistanceUnit.INCH);
            currentAction.setLast(new DriveRightAction(36, DistanceUnit.INCH)); // goes to 3rd parking zone
        }
        else{
            currentAction = new DriveRightAction(36, DistanceUnit.INCH); // goes to 1st parking zone
        }
    }


    @Override
    public void loop() {
        if(currentAction!=null){
            currentAction = currentAction.run(this);
        }
    }
}
