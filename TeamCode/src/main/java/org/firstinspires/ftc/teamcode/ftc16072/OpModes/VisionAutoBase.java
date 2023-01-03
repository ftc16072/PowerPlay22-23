package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.ftc16072.actions.QQAction;
import org.firstinspires.ftc.teamcode.ftc16072.pipelines.QQAprilTag;
import org.firstinspires.ftc.teamcode.ftc16072.util.NavigationPose;
import org.firstinspires.ftc.teamcode.ftc16072.util.RobotPose;
import org.openftc.apriltag.AprilTagDetection;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

import java.util.ArrayList;

abstract public class VisionAutoBase extends QQOpMode {
    OpenCvWebcam webcam;
    //SignalSleevePipeline signalSleevePipeline = new SignalSleevePipeline();
    QQAprilTag aprilTagPipeline = new QQAprilTag(0.015, 578.272, 578.272, 402.145, 221.506);
    ArrayList<AprilTagDetection> tagsSeen;
    QQAction currentAction;
    boolean isLeft; //have to initialize
    boolean isPrimary; //primary - closer high goal, secondary - further
    boolean aPressed;
    boolean xPressed;

    @Override
    public void init() {
        super.init();
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        webcam.setPipeline(aprilTagPipeline);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(320,240, OpenCvCameraRotation.UPSIDE_DOWN);
            }

            @Override
            public void onError(int errorCode) {

            }
        });
    }

    @Override
    public void init_loop() {
        super.init_loop();

        tagsSeen = aprilTagPipeline.getLatestDetections();
        //telemetry.addData("Parking Zone: ", parkingZone);
        for (AprilTagDetection currTag : tagsSeen){
            switch (currTag.id){
                case 1:{
                    parkingZone = 1;
                    break;
                }
                case 2:{
                    parkingZone = 2;
                    break;
                }
                default:
                case 3:{
                    parkingZone = 3;
                    break;
                }
            }
        }
        telemetry.addData("Parking Zone", parkingZone);

        //nav.setCurrentPosition(new RobotPose());

        if(gamepad1.a && !aPressed) {
            isLeft = !isLeft;
        }
        aPressed = gamepad1.a;

        if(gamepad1.x && !xPressed){
            isPrimary = !isPrimary;
        }
        xPressed = gamepad1.x;


    }

    @Override
    public void start() {
        webcam.stopStreaming();

    }

    @Override
    public void loop() {
        if (currentAction != null) {
            currentAction = currentAction.run(this);
            telemetry.addData("Action", currentAction.getDescription());
        }
    }
}
