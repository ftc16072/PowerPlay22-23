package org.firstinspires.ftc.teamcode.ftc16072.pipelines;

import static org.opencv.core.CvType.CV_8UC1;

import org.checkerframework.checker.units.qual.C;
import org.opencv.calib3d.StereoBM;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;
import org.opencv.calib3d.StereoMatcher;
import org.opencv.core.Core;

public class Stereographic extends OpenCvPipeline {
    Mat output = new Mat(); //change type to 8-bit when creating

    public Mat stereoMadness(Mat inputLeft, Mat inputRight) {

       StereoBM stereo = StereoBM.create(16,15);
       stereo.compute(inputLeft, inputRight, output);
       return output;
    }


    @Override
    public Mat processFrame(Mat input) {
        return stereoMadness(input,input);
    }


}
