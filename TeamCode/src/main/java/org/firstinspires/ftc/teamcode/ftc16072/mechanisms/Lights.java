package org.firstinspires.ftc.teamcode.ftc16072.mechanisms;

import android.icu.util.RangeValueIterator;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ftc16072.tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.tests.TestBlinkin;
import org.firstinspires.ftc.teamcode.ftc16072.tests.TestGyro;

import java.sql.Driver;
import java.util.Arrays;
import java.util.List;

public class Lights extends Mechanism {
    RevBlinkinLedDriver lights;
    boolean pink = false;
    public void init(HardwareMap hwMap){
        lights = hwMap.get(RevBlinkinLedDriver.class ,"lights");
        turn_red();

    }

    @Override
    public List<QQTest> getTests() {
        return Arrays.asList(
                new TestBlinkin(lights, "REV Blinkin", RevBlinkinLedDriver.BlinkinPattern.RED,RevBlinkinLedDriver.BlinkinPattern.BLACK)
        );

    }

    public void turn_pink(){ // Intake
        lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.HOT_PINK);
        pink = true;
    }
    public void turn_blue(){
        if (pink == true){
            lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);//make halfhalf

        }
        else{
            lights.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE); //keep full
        }

    }
    public void turn_green(){
        if (pink == true) {
            lights.setPattern((RevBlinkinLedDriver.BlinkinPattern.GREEN)); //half and half
        }
        else{
            lights.setPattern((RevBlinkinLedDriver.BlinkinPattern.GREEN)); //keep full
        }
    }
    public void turn_red(){
        if (pink == true){
            lights.setPattern((RevBlinkinLedDriver.BlinkinPattern.RED)); //half and half
        }
        else{
            lights.setPattern((RevBlinkinLedDriver.BlinkinPattern.RED)); //keep full
        }

    }

}
