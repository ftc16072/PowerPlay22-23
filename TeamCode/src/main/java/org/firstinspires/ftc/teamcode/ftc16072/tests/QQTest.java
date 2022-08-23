package org.firstinspires.ftc.teamcode.ftc16072.tests;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public abstract class QQTest {
    private String description;
    QQTest(String description){
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    public abstract void run(boolean on, Telemetry telemetry);

}
