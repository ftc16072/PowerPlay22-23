package org.firstinspires.ftc.teamcode.ftc16072.pipelines;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class DrawRectanglePipeline extends OpenCvPipeline {
    //looks for a specific color
    public Scalar rectangleColor = new Scalar(0,255,0);

    //defines the size of the rectangle
    public Rect rect1 = new Rect(140,100, 50, 50);

    @Override
    //override cuz compiler bad
    public Mat processFrame(Mat input) {
        Imgproc.rectangle(input, rect1, rectangleColor);
        //draws rectangle of size rect 1 and color color
        return input;
    }
}
