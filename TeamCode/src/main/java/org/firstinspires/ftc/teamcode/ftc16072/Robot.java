package org.firstinspires.ftc.teamcode.ftc16072;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.Gyro;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.HorizontalSlides;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.Lift;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.MecanumDrive;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.Mechanism;

import java.util.Arrays;
import java.util.List;

public class Robot {
    public MecanumDrive mecanumDrive = new MecanumDrive();
    public Gyro gyro = new Gyro();
    public Claw claw = new Claw();
    public Lift lift = new Lift();
    public HorizontalSlides horizontalSlides = new HorizontalSlides();
    public static boolean isRed = false;

    List<Mechanism> mechanismList = Arrays.asList(
            mecanumDrive,
            gyro,
            claw,
            horizontalSlides,
            lift
    );

    public void init(HardwareMap hwMap) {
        for (Mechanism mechanism : mechanismList) {
            mechanism.init(hwMap);
        }

        Claw.ConeType coneType = claw.getConeType();
        if (coneType == Claw.ConeType.RED){
            isRed = true;
        }
        else if (coneType == Claw.ConeType.BLUE){
            isRed = false;
        }


    }

    public List<Mechanism> getMechanismList() {
        return mechanismList;
    }

}
