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
    private Color color;
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

    public enum Color{
        BLUE,
        ORANGE,
        YELLOW
    }

    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, hsvMat, Imgproc.COLOR_RGB2HSV);
        Imgproc.rectangle(input, rect1, rectangleColor);
        Mat blueMask = new Mat();
        Mat orangeMask = new Mat();
        Mat yellowMask = new Mat();
        Scalar lower_blue = new Scalar(93.5,0,182.8);
        Scalar upper_blue = new Scalar(144.5,93.5,194.1);
        Scalar lower_orange = new  Scalar(144.5,165.8,68);
        Scalar upper_orange = new Scalar(185.5,165.8,68);
        Scalar lower_yellow = new Scalar(191.3,141.7,51);
        Scalar upper_yellow = new Scalar(230.9,148.8,97.8);

        Core.inRange(input, lower_blue, upper_blue,blueMask);
        Mat amountBlue = hsvMat.submat(rect1);
        Core.inRange(input, lower_orange, upper_orange,orangeMask);
        Mat amountOrange = hsvMat.submat(rect1);
        Core.inRange(input, lower_yellow, upper_yellow,yellowMask);
        Mat amountYellow = hsvMat.submat(rect1);

        double rectValue1 = Core.sumElems(amountBlue).val[0]/rect1.area()/255;
        double rectValue2 = Core.sumElems(amountOrange).val[0]/rect1.area()/255;
        double rectValue3 = Core.sumElems(amountYellow).val[0]/rect1.area()/255;// percentage

        if (rectValue1 > rectValue2 && rectValue1 > rectValue3) {
            color = Color.BLUE;
        }
        else if (rectValue2 > rectValue3) {
            color = Color.ORANGE;
        }
        else {
            color = Color.YELLOW;
        }
        getNumberOfDots();
        return input;
    }

    private String getValues(Scalar color) {
        return "" + color.val[0] + " " + color.val[1] + " " + color.val[2];
    }

    public void getNumberOfDots() {
        if (color == Color.BLUE) {
            numberOfDots = 3;
        }
        if (color == Color.ORANGE) {
            numberOfDots = 2;
        }
        if (color == Color.YELLOW) {
            numberOfDots = 1;
        }
    }
}
