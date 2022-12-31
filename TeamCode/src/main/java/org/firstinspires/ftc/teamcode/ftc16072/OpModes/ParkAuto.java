package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveForwardAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveLeftAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveRightAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.QQAction;
import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationMecanum;
import org.openftc.apriltag.AprilTagDetection;

import java.util.ArrayList;

@Autonomous
public class ParkAuto extends ConeDetection {
    //NavigationMecanum nav = new NavigationMecanum(robot);
    QQAction currentAction;
    NavigationMecanum nav = new NavigationMecanum(robot);
    int parkingZone = 0;
    ArrayList<AprilTagDetection> tagsSeen;
    @Override
    public void init_loop() {
        super.init_loop();

        tagsSeen = super.aprilTagPipeline.getLatestDetections();
        //telemetry.addData("Parking Zone: ", parkingZone);
        for (AprilTagDetection currTag : tagsSeen){
            switch (currTag.id){
                case 1:{
                    parkingZone = 1;
                    break;
                }
                case 2:{
                    parkingZone = 2;
                    break;
                }
                default:
                case 3:{
                    parkingZone = 3;
                    break;
                }
            }
        }
        telemetry.addData("Parking Zone", parkingZone);
    }

    @Override
    public void start() {
        if (parkingZone == 1) {
            currentAction = new DriveLeftAction(20, DistanceUnit.INCH);
            currentAction.setLast(new DriveForwardAction(24, DistanceUnit.INCH)); // goes to 1st parking zone
        } else if (parkingZone == 2) {
            currentAction = new DriveForwardAction(36, DistanceUnit.INCH); // goes to 2nd parking zone

        } else if (parkingZone == 3) {
            currentAction = new DriveRightAction(20, DistanceUnit.INCH);
            currentAction.setNext(new DriveForwardAction(24, DistanceUnit.INCH)); // goes to 3rd parking zone
        }
    }


    @Override
    public void loop() {
        if (currentAction != null) {
            currentAction = currentAction.run(this);
        }
    }
}
