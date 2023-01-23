package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ftc16072.Robot;
import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationMecanum;
import org.firstinspires.ftc.teamcode.ftc16072.util.RobotPose;

public abstract class QQOpMode extends OpMode {
    public Robot robot = new Robot();
    public NavigationMecanum nav = new NavigationMecanum(robot);
    public int parkingZone = 1;


    @Override
    public void init() {
        robot.init(hardwareMap);
        nav.setCurrentPosition(new RobotPose(0, 0, DistanceUnit.INCH, 0, AngleUnit.DEGREES));

    }

    @Override
    public void loop() {
        nav.updatePose();
       robot.lift.update();
       // robot.liftAuto.update();
    }
}
