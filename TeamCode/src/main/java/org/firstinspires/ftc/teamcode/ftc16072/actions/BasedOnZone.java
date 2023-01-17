package org.firstinspires.ftc.teamcode.ftc16072.actions;

import org.firstinspires.ftc.teamcode.ftc16072.OpModes.QQOpMode;

public class BasedOnZone extends QQAction{

    QQAction z1;
    QQAction z2;
    QQAction z3;
    public BasedOnZone(String description, QQAction zone1, QQAction zone2, QQAction zone3) {
        super(description);
        z1 = zone1;
        z2 = zone2;
        z3 = zone3;
    }

    @Override
    public QQAction run(QQOpMode opMode) {
        switch(opMode.parkingZone){
            case 3:
                return z3;
            case 2:
                return z2;
            default:
            case 1:
                return z1;
        }
    }
}
