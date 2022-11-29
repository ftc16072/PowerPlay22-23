package org.firstinspires.ftc.teamcode.ftc16072.util;

import org.firstinspires.ftc.teamcode.ftc16072.Robot;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.HorizontalSlides;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.Lift;

public class SafeChecker {
    Robot robot;
    public SafeChecker(Robot robot){
        this.robot = robot;
    }
    public boolean moveHorizontalSlidesNextForward(HorizontalSlides.Position position){
        if(!robot.lift.isSafe()){
            return false;
        }
        robot.horizontalSlides.goToNextForward(position);
        return true;
    }
    public boolean moveHorizontalSlidesNextBackward(HorizontalSlides.Position position){
        if(!robot.lift.isSafe()){
            return false;
        }
        robot.horizontalSlides.goToNextBackward(position);
        return true;
    }
    public boolean moveHorizontalSlidesManually(double position){
        if(!robot.lift.isSafe()){
            return false;
        }
        robot.horizontalSlides.goTo(position);
        return true;
    }
    public boolean moveVerticalLiftManually(int change){
        if (!robot.lift.isSafe()){
            return false;
        }
        robot.lift.adjustPosition(change);
        return true;
    }


    public boolean moveVerticalLift (Lift.Level level){
        if(!robot.lift.isSafe()){
            return false;
        }
        robot.lift.goTo(level);
        return true;
    }

}
