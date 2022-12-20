package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.ftc16072.pipelines.DrawRectanglePipeline;
import org.firstinspires.ftc.teamcode.ftc16072.pipelines.QQAprilTag;
import org.firstinspires.ftc.teamcode.ftc16072.pipelines.SignalSleevePipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

abstract public class ConeDetection extends QQOpMode {
    OpenCvWebcam webcam;
    //SignalSleevePipeline signalSleevePipeline = new SignalSleevePipeline();
    QQAprilTag aprilTagPipeline = new QQAprilTag(0.167, 578.272, 578.272, 402.145, 221.506);

    @Override
    public void init() {
        super.init();
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        webcam.setPipeline(aprilTagPipeline);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(320,240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {

            }
        });
    }

    @Override
    public void start() {
        webcam.stopStreaming();
    }

    @Override
    public void loop() {
        //signalSleevePipeline.numberOfDots
    }
}
