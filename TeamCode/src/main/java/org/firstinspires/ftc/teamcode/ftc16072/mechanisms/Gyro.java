package org.firstinspires.ftc.teamcode.ftc16072.mechanisms;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.ftc16072.tests.QQTest;

import java.util.List;

public class Gyro extends Mechanism{
    private BNO055IMU imu;
    @Override
    public void init(HardwareMap hwMap) {
        imu = hwMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters params = new BNO055IMU.Parameters();
        params.calibrationDataFile = "BNO055IMUCalibration.json";
        imu.initialize(params);
    }

    public double getHeading(AngleUnit au){
        Orientation angles;

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);

        return au.fromRadians(angles.firstAngle);
    }

    @Override
    public List<QQTest> getTests() {
        return null;
    }
}
