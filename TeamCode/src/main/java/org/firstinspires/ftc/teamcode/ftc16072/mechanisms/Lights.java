package org.firstinspires.ftc.teamcode.ftc16072.mechanisms;

import  android.icu.util.RangeValueIterator;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ftc16072.tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.tests.TestBlinkin;
import org.firstinspires.ftc.teamcode.ftc16072.tests.TestGyro;

import java.sql.Driver;
import java.util.Arrays;
import java.util.List;

public class Lights extends Mechanism {
    RevBlinkinLedDriver blinkin;

    public void init(HardwareMap hwMap){
        blinkin = hwMap.get(RevBlinkinLedDriver.class,"lights");
    }

    @Override
    public List<QQTest> getTests() {
        return Arrays.asList(
                new TestBlinkin(blinkin, "REV Blinkin", RevBlinkinLedDriver.BlinkinPattern.RED,RevBlinkinLedDriver.BlinkinPattern.BLACK)
        );

    }


    public void turn_yellow(){ // Intake
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.YELLOW);

    }
    public void turn_purple(){

        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.HOT_PINK);


    }
    public void flash_purple()  {
        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.LIGHT_CHASE_RED);


    }
    public void turn_off(){

        blinkin.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLACK); //half and half


    }

}
