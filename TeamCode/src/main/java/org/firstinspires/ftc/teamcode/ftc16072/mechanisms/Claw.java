package org.firstinspires.ftc.teamcode.ftc16072.mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ftc16072.tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.tests.TestColorSensor;
import org.firstinspires.ftc.teamcode.ftc16072.tests.TestServo;

import java.util.Arrays;
import java.util.List;


/**
 * Claw --- class containing our methods for our claw mechanism
 */
@Config
public class Claw extends Mechanism {
    public static final double CONE_IN_GRIP = 4;
    private Servo clawServo;
    private ColorRangeSensor coneDetector;
    public enum ConeType{
        BLUE,
        RED,
        NONE,
    }
    public static double GRIPPED_SERVO_POSITION = 0.15;
    public static double RELEASED_SERVO_POSITION = 0.30;

    //make final when values fixed
    @SuppressWarnings("unused")
    public enum State {
        EMPTY,
        LOADING,
        LOADED,
        GRIPPED
    }

    private State state;

    /**
     * Accessor method the current state of the claw
     * @return the current state of the claw
     */
    @SuppressWarnings("unused")
    public State getState() {
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
                new TestServo(clawServo, "claw", RELEASED_SERVO_POSITION, GRIPPED_SERVO_POSITION),
                new TestColorSensor(coneDetector, "cone_detector")
        );
    }

    /**
     * accessor for cone type
     * @return what color cone is in our robot if there is a cone
     */
    public ConeType getConeType(){
        if(!(coneDetector.getDistance(DistanceUnit.INCH) < CONE_IN_GRIP)){
            return ConeType.NONE;
        }
        if (coneDetector.blue() > coneDetector.red()){
            return ConeType.BLUE;
        }
        return ConeType.RED;
    }

    /**
     * checks if there is a cone to grip
     * @return boolean - whether cone is graspable
     */
    public boolean isGripable(){
        return getConeType() != ConeType.NONE;
    }

    /**
     * grips
     */
    public void grip() {
        clawServo.setPosition(GRIPPED_SERVO_POSITION);
        state = State.GRIPPED;
    }

    /**
     * releases
     */
    public void release() {
        clawServo.setPosition(RELEASED_SERVO_POSITION);
        state = State.EMPTY;
    }

    /**
     * accessor to the last position
     * @return position of the claw servo
     */
    @SuppressWarnings("unused")
    public double getClawPosition() {
        return clawServo.getPosition();
    }

}
