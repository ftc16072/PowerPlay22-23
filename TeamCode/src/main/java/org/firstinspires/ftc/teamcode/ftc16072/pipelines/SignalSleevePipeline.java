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
        Scalar lower_blue = new Scalar(93.5,0,182.8);
        Scalar upper_blue = new Scalar(144.5,93.5,194.1);
        Scalar lower_orange = new  Scalar(144.5,165.8,68);
        Scalar upper_orange = new Scalar(185.5,165.8,68);
        Scalar lower_yellow = new Scalar(191.3,141.7,51);
        Scalar upper_yellow = new Scalar(230.9,148.8,97.8);

        Core.inRange(input, lower_blue, upper_blue,input);
        Mat amount = hsvMat.submat(rect1);

        double rectValue = Core.sumElems(amount).val[0]/rect1.area()/255; // percentage

        //Core.bitwise_and(submat, submat,outmat1,outmat);

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
