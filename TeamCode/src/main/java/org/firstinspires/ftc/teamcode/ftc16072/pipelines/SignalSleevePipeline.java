package org.firstinspires.ftc.teamcode.ftc16072.pipelines;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class SignalSleevePipeline extends OpenCvPipeline {
    //creates a rectangle to look in
    public Rect rect1 = new Rect(155, 100, 40, 40);
    //creates the color for the rectangle
    public Scalar rectangleColor = new Scalar(255, 255);
    //hue saturation brightness
    Mat hsvMat = new Mat();
    public int numberOfDots;

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, hsvMat, Imgproc.COLOR_RGB2BGR);
        Imgproc.rectangle(input, rect1, rectangleColor);
        Mat submat = input.submat(rect1);
        numberOfDots = getNumberOfDots(Core.mean(submat));
        return input;
    }

    private int getNumberOfDots(Scalar color) {
        double saturation = color.val[1];
        //identfies number of dots based on color
        if (saturation > 125) {
            //green
            return 3;
        }
        if (saturation < 75) {
            //black
            return 1;
        }
        //red
        return 2;

    }
}
