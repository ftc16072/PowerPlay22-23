package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.ftc16072.Robot;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.Mechanism;
import org.firstinspires.ftc.teamcode.ftc16072.tests.QQTest;

import java.util.List;

@TeleOp()
public class TestWiring extends OpMode {
    Robot robot = new Robot();
    boolean wasDown, wasRight;
    List<Mechanism> mechanismList;
    List<QQTest> testList;
    int currentMechanism;
    int currentTest;

    @Override
    public void init() {
        robot.init(hardwareMap);
        mechanismList = robot.getMechanismList();
    }

    @Override
    public void loop() {
        if(gamepad1.dpad_down && !wasDown){
            currentMechanism += 1;
            if (currentMechanism >= mechanismList.size()){
                currentMechanism = 0;
            }
            currentTest = 0;
        }
        wasDown = gamepad1.dpad_down;
        testList = mechanismList.get(currentMechanism).getTests();

        if(gamepad1.dpad_right && !wasRight){
            currentTest += 1;
            if (currentTest >= testList.size()){
                currentTest = 0;
            }
        }
        telemetry.addLine("Press A to run test");
        telemetry.addData("Mechanism", mechanismList.get(currentMechanism).toString());
        telemetry.addData("Test", testList.get(currentTest).getDescription());

        testList.get(currentTest).run(gamepad1.a, telemetry);

    }
}
