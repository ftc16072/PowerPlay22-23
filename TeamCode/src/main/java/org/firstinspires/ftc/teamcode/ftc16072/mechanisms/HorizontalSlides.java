package org.firstinspires.ftc.teamcode.ftc16072.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.ftc16072.tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.tests.TestServo;

import java.util.Arrays;
import java.util.List;


public class HorizontalSlides extends Mechanism {
    private Servo pulleyServo;
    public final double BACK_SERVO_POSITION = 0.05 ;
    public final double MIDDLE_SERVO_POSITION = 0.650;
    public final double FRONT_SERVO_POSITION = 0.8625;
    public final double UNSAFE_FRONT = 0.42;
    public final double UNSAFE_BACK = 0.15;

    public enum Position {
        BACK,
        MIDDLE,
        FRONT
    }

    @Override
    public void init(HardwareMap hwMap) {
        pulleyServo = hwMap.get(Servo.class, "horizontal");
    }

    @Override
    public List<QQTest> getTests() {
        return Arrays.asList(
                new TestServo(pulleyServo, "pulley back", BACK_SERVO_POSITION, MIDDLE_SERVO_POSITION),
                new TestServo(pulleyServo, "pulley front", FRONT_SERVO_POSITION, MIDDLE_SERVO_POSITION)
        );
    }

    public void goToPosition(Position position) {

        switch (position) {
            case BACK:
                pulleyServo.setPosition(BACK_SERVO_POSITION);
                break;
            case MIDDLE:
                pulleyServo.setPosition(MIDDLE_SERVO_POSITION);
                break;
            case FRONT:
                pulleyServo.setPosition(FRONT_SERVO_POSITION);
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
            double pulleyPos = (MIDDLE_SERVO_POSITION + (FRONT_SERVO_POSITION - MIDDLE_SERVO_POSITION) * pos);
            pulleyServo.setPosition(pulleyPos);
        } else {
            double pulleyPos = (BACK_SERVO_POSITION + (MIDDLE_SERVO_POSITION - BACK_SERVO_POSITION) * (1 + pos));
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
        double pos = getSlidesPosition();
        return (pos < UNSAFE_BACK) || (pos > UNSAFE_FRONT);
    }
    private boolean rangesIntersect(double x1, double x2, double y1, double y2){
        return x1<=y2 && y1<=x2;
    }

    public boolean isSafeToGoTo(double desiredPos){
        double currentPos = getSlidesPosition();
        boolean result = false;
        if(currentPos <= desiredPos){
            result = !rangesIntersect(UNSAFE_BACK, UNSAFE_FRONT, currentPos, desiredPos);
        }else{
            result = !rangesIntersect(UNSAFE_BACK, UNSAFE_FRONT, desiredPos, currentPos);
        }
        return result;
    }
    public boolean isSafeToGoTo(Position position){
        switch(position){
            case BACK: return isSafeToGoTo(BACK_SERVO_POSITION);
            case FRONT: return isSafeToGoTo(FRONT_SERVO_POSITION);
            case MIDDLE: return isSafeToGoTo(MIDDLE_SERVO_POSITION);
        }
        return false;
    }
}
