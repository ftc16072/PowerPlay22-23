package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ftc16072.util.MoveDeltas;

public class OdometryTestOpmode extends VisionAutoBase{
  //  Robot robot;
    public void start(){
        super.start();


    }

    public void loop(){
        super.loop();
        MoveDeltas position = robot.odometry.getDistance(false);
        telemetry.addData("forward encoder",position.getForward(DistanceUnit.INCH));
        telemetry.addData("strafe encoder",position.getStrafe(DistanceUnit.INCH));
    }
}
