package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveForwardAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveLeftAction;
import org.firstinspires.ftc.teamcode.ftc16072.actions.DriveRightAction;

@Autonomous
public class ParkAuto extends VisionAutoBase {
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

}
