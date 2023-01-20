package org.firstinspires.ftc.teamcode.ftc16072.OpModes;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.ftc16072.Robot;
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
    OpenCvWebcam webcamLeft;
    OpenCvWebcam webcamRight;
    //SignalSleevePipeline signalSleevePipeline = new SignalSleevePipeline();
    QQAprilTag aprilTagPipelineLeft = new QQAprilTag(0.015, 578.272, 578.272, 402.145, 221.506);
    QQAprilTag aprilTagPipelineRight = new QQAprilTag(0.015, 578.272, 578.272, 402.145, 221.506);

    QQAction currentAction;
    boolean isLeft;
    boolean isPrimary; //primary - closer high goal, secondary - further
    boolean bPressed;
    boolean xPressed;
    boolean yPressed;
    boolean test;


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
                webcamLeft.startStreaming(320,240, OpenCvCameraRotation.UPSIDE_DOWN);
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

    public int find_parking(QQAprilTag pipeline){
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
            parkingZone = 3; //neither camera found tag, picked 3 as default
        }
        telemetry.addData("Parking", parkingZone);
        if(gamepad1.a && !aPressed) {

        telemetry.addData("Parking Zone", parkingZone);
        telemetry.addData("Placing Left", isLeft);
        telemetry.addData("Primary Strategy", isPrimary);
        telemetry.addData("Red Alliance", Robot.isRed);

        //nav.setCurrentPosition(new RobotPose());

        if(gamepad1.b && !bPressed) {
            isLeft = !isLeft;
        }
        bPressed = gamepad1.b;

        if(gamepad1.x && !xPressed){
            isPrimary = !isPrimary;
        }
        xPressed = gamepad1.x;

        if(gamepad1.y && !yPressed){
            parkingZone += 1;
            if(parkingZone >= 4){
                parkingZone = 1;
            }
        }
        yPressed = gamepad1.y;


        telemetry.addData("isLeft", isLeft);
        telemetry.addData("isPrimary", isPrimary);
    }

    @Override
    public void start() {
        webcamLeft.stopStreaming();
        webcamRight.stopStreaming();
        //control hub
        double startXLocation = isLeft? -36 +5.5 : 36 -5.5;
        nav.setCurrentPosition(new RobotPose(startXLocation,22, DistanceUnit.INCH, 0, AngleUnit.DEGREES));
    }

    @Override
    public void loop() {
        super.loop();
        if (currentAction != null) {
            telemetry.addData("Action", currentAction.getDescription());
            currentAction = currentAction.run(this);
            telemetry.addData("Action", currentAction.getDescription());
        }
        RobotPose robotPose = nav.getCurrentPosition();
        telemetry.addData("Robot Position X:", robotPose.getX(DistanceUnit.INCH));
        telemetry.addData("Robot Position Y:", robotPose.getY(DistanceUnit.INCH));

        telemetry.addData("Left lift position", robot.lift.leftLiftMotor.getCurrentPosition());
        telemetry.addData("Right lift position", robot.lift.rightLiftMotor.getCurrentPosition());
    }

}
