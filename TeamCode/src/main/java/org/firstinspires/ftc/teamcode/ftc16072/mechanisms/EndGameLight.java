package org.firstinspires.ftc.teamcode.ftc16072.mechanisms;

import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.LED;

import org.firstinspires.ftc.teamcode.ftc16072.tests.QQTest;
import org.firstinspires.ftc.teamcode.ftc16072.tests.TestLED;
import org.firstinspires.ftc.teamcode.ftc16072.tests.TestServo;

import java.util.Arrays;
import java.util.List;

public class EndGameLight extends Mechanism{
    DigitalChannel green;
    DigitalChannel red;

    public enum Color {GREEN,RED,YELLOW,NONE}

    @Override
    public void init(HardwareMap hwMap) {

        green = hwMap.get(DigitalChannel.class,"green");
        red = hwMap.get(DigitalChannel.class,"red");
        green.setMode(DigitalChannel.Mode.OUTPUT);
        red.setMode(DigitalChannel.Mode.OUTPUT);
        green.setState(true);
        red.setState(true);
    }

    @Override
    public List<QQTest> getTests() {
        return Arrays.asList(
                new TestLED(green, "green led"),
                new TestLED(red, "red led"));
    }
    public void setColor(Color color){
        switch (color){
            case RED:
                red.setState(false);
                green.setState(true);
                break;
            case GREEN:
                red.setState(true);
                green.setState(false);
                break;
            case YELLOW:
                red.setState(false);
                green.setState(false);
                break;
            case NONE:
                red.setState(true);
                green.setState(true);

        }
    }
}
