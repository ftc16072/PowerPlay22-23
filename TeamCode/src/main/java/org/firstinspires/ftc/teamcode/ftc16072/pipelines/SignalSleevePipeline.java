package org.firstinspires.ftc.teamcode.ftc16072.pipelines;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class SignalSleevePipeline extends OpenCvPipeline {
    //creates a rectangle to look in
    public Rect rect1 = new Rect(175, 120, 10, 10);

    /*int i = 0;
    for (y = 0, y <= 4,y++ ){

        for (x = 0, y <= 4, x++) {
            rectArray[i] = new Rect(115+x*20,75+y*20,10,10);
            i++;
        }
    }  */
    //creates the color for the rectangle
    public Scalar rectangleColor = new Scalar(255, 255);
    //hue saturation brightness
    Mat hsvMat = new Mat();
    public int numberOfDots;
    public String values;


    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, hsvMat, Imgproc.COLOR_RGB2HSV);
        Imgproc.rectangle(input, rect1, rectangleColor);
        Mat submat = input.submat(rect1);
        Mat outmat = new Mat();
        Mat outmat1 = new Mat();
        Core.inRange(submat, (), (),outmat)

        Core.bitwise_and(submat, submat,outmat1,outmat)

        return input;
    }

    private String getValues(Scalar color) {
        return "" + color.val[0] + " " + color.val[1] + " " + color.val[2];
    }

    private int getNumberOfDots(Scalar[] color) {
        int dots = 0;
        for(Scalar testCol : color) {
            double hue = testCol.val[0];
            //put conditional in here
        }
        return dots;

    }
}
