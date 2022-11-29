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
    private final int MIDDLE_SERVO_POSITION = 1000;
    private final int FRONT_SERVO_POSITION = 1400;
    private static Position currentPos = Position.BACK;

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

    /*\
     * @param  pos  range from -1 (fully back) to 1 (fully front)
     */
    public void goTo(double pos) {
        if (pos < -1.0) {
            pos = -1.0;
        }
        if (pos > 1.0) {
            pos = 1.0;
        }
        if (pos > 0) {
            double pulleyPos = convertMs((int)(MIDDLE_SERVO_POSITION + (FRONT_SERVO_POSITION - MIDDLE_SERVO_POSITION) * pos));
            pulleyServo.setPosition(pulleyPos);
        } else {
            double pulleyPos = convertMs((int)(BACK_SERVO_POSITION + (MIDDLE_SERVO_POSITION - BACK_SERVO_POSITION) * (1 + pos)));
            pulleyServo.setPosition(pulleyPos);
        }
    }
    public void goToNextForward(Position position){
        if(position==Position.BACK){
            goTo(Position.MIDDLE);
        } else if(position==Position.MIDDLE){
            goTo(Position.FRONT);
        }
    }

    public void goToNextBackward(Position position){
        if(position==Position.MIDDLE){
            goTo(Position.BACK);
        } else if(position==Position.FRONT){
            goTo(Position.MIDDLE);
        }
    }
}
