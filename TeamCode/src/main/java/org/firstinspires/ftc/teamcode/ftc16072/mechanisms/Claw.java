package org.firstinspires.ftc.teamcode.ftc16072.mechanisms;

import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.ftc16072.tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.tests.TestColorSensor;
import org.firstinspires.ftc.teamcode.ftc16072.tests.TestServo;

import java.util.Arrays;
import java.util.List;

public class Claw extends Mechanism{
    private Servo clawServo;
    private ColorRangeSensor coneDetector;
    private static double GRIPPED = 0.5;
    private static double RELEASED = 0.0;
//make final when values fixed
    public enum State{
        EMPTY,
        LOADING,
        LOADED,
        GRIPPED
    }

    private State state;

    public State getState(){
        return state;
    }

    @Override
    public void init(HardwareMap hwMap) {
        clawServo = hwMap.get(Servo.class, "claw");
        coneDetector = hwMap.get(ColorRangeSensor.class, "cone_detector");
        state = State.GRIPPED;
        grip();
/*
   stateDiagram-v2
[*] --> Gripped
    Empty --> Loading :seen cone
    Loading --> Loaded :not seen cone
    Loaded --> Gripped :grip
    Gripped --> Empty: ungrip and lowered
    */
    }

    @Override
    public List<QQTest> getTests() {
        return Arrays.asList(
                new TestServo(clawServo, "claw", 0.2, 0),
                new TestColorSensor(coneDetector, "cone_detector")
        );
    }
    public void grip(){
        clawServo.setPosition(GRIPPED);
        state = State.GRIPPED;
    }
    public void release(){
        clawServo.setPosition(RELEASED);
    }
    public double getClawPosition(){
        return clawServo.getPosition();
    }

}