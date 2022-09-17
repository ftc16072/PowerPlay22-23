package org.firstinspires.ftc.teamcode.ftc16072.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.ftc16072.tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.tests.TestServo;

import java.util.Arrays;
import java.util.List;

public class Claw extends Mechanism{
    public Servo clawServo;
    public double gripped = 0.5;
    public double released = 0.0;
    public boolean seenCone = false;
    public boolean grip = false;
    public boolean loaded = false;

    @Override
    public void init(HardwareMap hwMap) {
        clawServo = hwMap.get(Servo.class, "claw");
/*
    stateDiagram-v2
    [*] --> Empty
    Empty --> Loading :seen cone
    Loading --> Loaded :not seen cone
    Loaded --> Gripped :grip
    Gripped --> Empty: ungrip and lowered
    */
    }

    @Override
    public List<QQTest> getTests() {
        return Arrays.asList(
                new TestServo(clawServo, "claw", 0.2, 0)
        );
    }
    public void grip(){
        clawServo.setPosition(gripped);
        grip = true;
        loaded = true;
    }
    public void release(){
        clawServo.setPosition(released);
    }
    public double getClawPosition(){
        return clawServo.getPosition();
    }
    public boolean seenCone(){
        return seenCone;
        //TODO: Vision work
    }
}
