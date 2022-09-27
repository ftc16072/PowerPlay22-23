package org.firstinspires.ftc.teamcode.ftc16072.pipelines;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class SignalSleevePipeline extends OpenCvPipeline {
    //creates a rectangle to look in
    public Rect rect1 = new Rect(175, 120, 20, 20);
    //creates the color for the rectangle
    public Scalar rectangleColor = new Scalar(255, 255);
    //hue saturation brightness
    Mat hsvMat = new Mat();
    public int numberOfDots;
    public String values;


    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, hsvMat, Imgproc.COLOR_RGB2BGR);
        Imgproc.rectangle(input, rect1, rectangleColor);
        Mat submat = input.submat(rect1);
        numberOfDots = getNumberOfDots(Core.mean(submat));
        values = getValues((Core.mean(submat)));
        return input;
    }
    private String getValues(Scalar color){
        return ""+color.val[0]+" "+color.val[1]+" "+color.val[2];
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
