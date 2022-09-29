package org.firstinspires.ftc.teamcode.ftc16072.tests;

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class TestGyro extends QQTest {
    BNO055IMU gyro;

    public TestGyro(BNO055IMU gyro, String description) {
        super(description);
        this.gyro = gyro;
    }

    @Override
    public void run(boolean on, Telemetry telemetry) {
        Orientation angles;

        angles = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);
        telemetry.addData("Heading: ", angles.firstAngle);

    }
}
