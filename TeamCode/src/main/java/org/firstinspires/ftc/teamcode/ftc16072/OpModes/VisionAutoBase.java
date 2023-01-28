package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ftc16072.actions.QQAction;

import org.firstinspires.ftc.teamcode.ftc16072.pipelines.QQAprilTagPipeline;

import org.firstinspires.ftc.teamcode.ftc16072.util.RobotPose;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

import java.util.ArrayList;

abstract public class VisionAutoBase extends AutoBase {
    OpenCvWebcam webcamLeft;
    OpenCvWebcam webcamRight;
    //SignalSleevePipeline signalSleevePipeline = new SignalSleevePipeline();

    QQAprilTagPipeline aprilTagPipelineLeft = new QQAprilTagPipeline(0.015, 578.272, 578.272, 402.145, 221.506);
    QQAprilTagPipeline aprilTagPipelineRight = new QQAprilTagPipeline(0.015, 578.272, 578.272, 402.145, 221.506);



    @Override
    public void init() {
        super.init();
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        int[] viewportContainerIds = OpenCvCameraFactory.getInstance()
                .splitLayoutForMultipleViewports(
                        cameraMonitorViewId, //The container we're splitting
                        2, //The number of sub-containers to create
                        OpenCvCameraFactory.ViewportSplitMethod.VERTICALLY); //Whether to split the container vertically or horizontally
        webcamLeft = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam Left"), viewportContainerIds[0]);
        webcamRight = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam Right"), viewportContainerIds[1]);

        webcamLeft.setPipeline(aprilTagPipelineLeft);
        webcamRight.setPipeline(aprilTagPipelineRight);
        webcamLeft.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcamLeft.startStreaming(320,240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {

            }
        });
        webcamRight.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcamRight.startStreaming(320,240, OpenCvCameraRotation.UPSIDE_DOWN);
            }

            @Override
            public void onError(int errorCode) {

            }
        });
    }

    public int find_parking(QQAprilTagPipeline pipeline){
        ArrayList<AprilTagDetection> tagsSeen = pipeline.getLatestDetections();
        for (AprilTagDetection currTag : tagsSeen){
            switch (currTag.id){
                case 1:
                    return 1;
                case 2:
                    return 2;
                case 3:
                    return 3;
            }
        }
        return 0;
    }

    @Override
    public void init_loop(){
        super.init_loop();
        int leftParking = find_parking(aprilTagPipelineLeft);
        int rightParking = find_parking(aprilTagPipelineRight);
        telemetry.addData("Parking Zone Left", leftParking);
        telemetry.addData("Parking Zone Right", rightParking);
        if (leftParking != 0){
            parkingZone = leftParking;
        }
        else if (rightParking != 0){
            parkingZone = rightParking;
        }
        else{
            parkingZone = 1; //neither camera found tag, picked 3 as default
        }
        telemetry.addData("Parking", parkingZone);
    }

    @Override
    public void start() {
        super.start();
        webcamLeft.stopStreaming();
        webcamRight.stopStreaming();
    }
}
