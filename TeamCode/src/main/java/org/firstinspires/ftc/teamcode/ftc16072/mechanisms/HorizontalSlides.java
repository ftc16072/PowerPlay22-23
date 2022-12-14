package org.firstinspires.ftc.teamcode.ftc16072.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.ftc16072.tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.tests.TestServo;

import java.util.Arrays;
import java.util.List;


public class HorizontalSlides extends Mechanism {
    public static final int MIN_SERVO_MS = 500;
    public static final int MAX_SERVO_MS = 2500;
    private Servo pulleyServo;
    //    private ColorRangeSensor coneDetector;
    public final int BACK_SERVO_POSITION = 700 ; // 700
    public final int MIDDLE_SERVO_POSITION = 1905; // 1000
    public final int FRONT_SERVO_POSITION = 2225; // 1400
    //dpad right = front
    //dpad middle = middle
    //dpad left =  back
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

    public void goToPosition(Position position) {

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
        if (pos< -1.0) {
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
            goToPosition(Position.MIDDLE);
        } else if(position==Position.MIDDLE){
            goToPosition(Position.FRONT);
        }
    }

    public void goToNextBackward(Position position){
        if(position==Position.MIDDLE){
            goToPosition(Position.BACK);
        } else if(position==Position.FRONT){
            goToPosition(Position.MIDDLE);
        }
    }

    public double getSlidesPosition() { return pulleyServo.getPosition(); }

    public boolean isSafe(){
        return getSlidesPosition() > 0.602222; //checks if slides are higher than middle position
    }
}
