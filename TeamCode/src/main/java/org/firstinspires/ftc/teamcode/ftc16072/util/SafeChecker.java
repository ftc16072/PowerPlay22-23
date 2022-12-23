package org.firstinspires.ftc.teamcode.ftc16072.util;

import android.util.Log;

import org.firstinspires.ftc.teamcode.ftc16072.Robot;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.HorizontalSlides;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.Lift;

public class SafeChecker {
    Robot robot;

    public SafeChecker(Robot robot) {
        this.robot = robot;
    }

    public boolean moveHorizontalSlides(HorizontalSlides.Position position) { //horizontal slide level lock handler
         //checks if safe to move horizontal slides
        if(robot.lift.isSafe() || robot.horizontalSlides.isSafeToGoTo(position)) {
            robot.horizontalSlides.goToPosition(position);
            return false;
        }
        return true;

    }

    public boolean moveHorizontalSlidesManually(double position) { //manual horizontal slide handler
        if(robot.lift.isSafe() || robot.horizontalSlides.isSafeToGoTo(position)) {
            robot.horizontalSlides.goTo(position);
            return false;
        }
        Log.d("QQ", String.format("Lift %b HS: %b (%f)", robot.lift.isSafe(), robot.horizontalSlides.isSafeToGoTo(position), position));
        return true;
    }

    public boolean moveVerticalLiftManually(int change) { //manual vertical lift handler
        if (change > 0) { //if going up
            robot.lift.adjustPosition(change); //do it, since going up shouldn't raise any problems
            return false;
        } else { //if going down
            if (robot.horizontalSlides.isSafe() || robot.lift.isSafeToAdjust(change)) { //check if slides are in middle position or above
                robot.lift.adjustPosition(change);
                return false;
            }
        }
        return true;
    }


    public boolean moveVerticalLift(Lift.Level level) { //vertical lift level lock handler
        if (level == Lift.Level.GROUND || level == Lift.Level.INTAKE) { //if moving to ground or intake
            if (!robot.horizontalSlides.isSafe()) { //make sure slides are in middle position or above
                return true;
            }

        }
        robot.lift.goTo(level); //move lift since conditions passed
        return false;
    }


}
