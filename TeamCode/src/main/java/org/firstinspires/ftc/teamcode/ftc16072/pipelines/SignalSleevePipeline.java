package org.firstinspires.ftc.teamcode.ftc16072.pipelines;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class SignalSleevePipeline extends OpenCvPipeline {
    //creates a rectangle to look in
    public Rect rect1 = new Rect(0, 0, 320, 240);
    private Color color;
    public Scalar lower_orange = new  Scalar(144.5,165.8,68);
    public Scalar upper_orange = new Scalar(185.5,165.8,68);
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
    public int numberOfDots = 0;
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

        Mat submat = input.submat(rect1);
        Mat outmat = new Mat();
        Mat outmat1 = new Mat();
        Mat outmat2 = new Mat();
        Scalar lower_blue = new Scalar(93.5,0,182.8);
        Scalar upper_blue = new Scalar(144.5,93.5,194.1);

        Scalar lower_yellow = new Scalar(198.6,154.6,81.2, 0.0);
        Scalar upper_yellow = new Scalar(248.2,255.0,120.7,255.0);


        Core.inRange(submat, lower_blue, upper_blue,outmat);
        Core.inRange(submat, lower_orange, upper_orange,outmat1);
        Core.inRange(submat, lower_yellow, upper_yellow,outmat2);

        double blueVal = Core.sumElems(outmat).val[0];
        double orangeVal = Core.sumElems(outmat1).val[0];
        double yellowVal = Core.sumElems(outmat2).val[0];// percentage


        if (blueVal > orangeVal && blueVal > yellowVal){
            numberOfDots=3;
        } else if(orangeVal > blueVal && orangeVal > yellowVal){
            numberOfDots=2;
        } else if(yellowVal > blueVal && yellowVal > orangeVal){
            numberOfDots=1;
        }
        else
            numberOfDots = 0;

        return outmat1;
    }


    private int getNumberOfDots() {
        return numberOfDots;
    }
}
