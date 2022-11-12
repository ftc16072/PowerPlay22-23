package org.firstinspires.ftc.teamcode.ftc16072.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.ftc16072.tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.tests.TestServo;

import java.util.Arrays;
import java.util.List;


public class HorizontalSlides extends Mechanism {
    public static final int MIN_SERVO_MS = 500;
    public static final int MAX_SERVO_MS = 2500;
    private Servo pulleyServo;
    //    private ColorRangeSensor coneDetector;
    private final int BACK_SERVO_POSITION = 700;
    private final int FRONT_SERVO_POSITION = 1000;
    private final int MIDDLE_SERVO_POSITION = 1400;

    public enum Position {
        BACK,
        MIDDLE,
        FRONT
    }

    @Override
    public void init(HardwareMap hwMap) {
        pulleyServo = hwMap.get(Servo.class, "horizontal");

    }

    private double convertMs(int numMs) {
        return ((double) (numMs - MIN_SERVO_MS)) / ((double) (MAX_SERVO_MS - MIN_SERVO_MS));
    }

    @Override
    public List<QQTest> getTests() {
        return Arrays.asList(
                new TestServo(pulleyServo, "pulley back", convertMs(BACK_SERVO_POSITION), convertMs(MIDDLE_SERVO_POSITION)),
                new TestServo(pulleyServo, "pulley front", convertMs(FRONT_SERVO_POSITION), convertMs(MIDDLE_SERVO_POSITION))
        );
    }

    public void goTo(Position position) {
        switch (position) {
            case BACK:
                pulleyServo.setPosition(convertMs(BACK_SERVO_POSITION));
                break;
            case MIDDLE:
                pulleyServo.setPosition(convertMs(MIDDLE_SERVO_POSITION));
                break;
            case FRONT:
                pulleyServo.setPosition(convertMs(FRONT_SERVO_POSITION));
                break;
        }
    }


}
