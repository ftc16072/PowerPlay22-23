package org.firstinspires.ftc.teamcode.ftc16072.actions;

import org.firstinspires.ftc.teamcode.ftc16072.OpModes.QQOpMode;
public class delayAction extends QQAction{
    double delayTime;
    double delayTill;

    public delayAction(double seconds){
        delayTime = seconds;
    }

    @Override
    public QQAction run(QQOpMode opmode){
        if(delayTill==0){
            delayTill = opmode.time + delayTime;
        }
        if(opmode.time>= delayTill){
            return nextAction;
        } else{
            return this;
        }
    }
}