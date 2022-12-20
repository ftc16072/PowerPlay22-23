package org.firstinspires.ftc.teamcode.ftc16072.util;

import org.firstinspires.ftc.teamcode.ftc16072.Robot;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.HorizontalSlides;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.Lift;

public class SafeChecker {
    Robot robot;
    boolean isSafe;

    public SafeChecker(Robot robot) {
        this.robot = robot;
    }

    public boolean moveHorizontalSlides(HorizontalSlides.Position position) { //horizontal slide level lock handler
         //checks if safe to move horizontal slides
            robot.horizontalSlides.goToPosition(position);
            return true;

        //return false;
    }

    public boolean moveHorizontalSlidesManually(double position) { //manual horizontal slide handler

            robot.horizontalSlides.goTo(position);
            return true;

    }

    public boolean moveVerticalLiftManually(int change) { //manual vertical lift handler
        if (change > 0) { //if going up
            robot.lift.adjustPosition(change); //do it, since going up shouldn't raise any problems
            return true;
        } else { //if going down
            if (robot.horizontalSlides.isSafe()) { //check if slides are in middle position or above
                robot.lift.adjustPosition(change);
                return true;
            }
        }
        return false;
    }


    public boolean moveVerticalLift(Lift.Level level) { //vertical lift level lock handler
        if (level == Lift.Level.GROUND || level == Lift.Level.INTAKE) { //if moving to ground or intake
            if (!robot.horizontalSlides.isSafe()) { //make sure slides are in middle position or above
                return false;
            }

        }
        robot.lift.goTo(level); //move lift since conditions passed
        return true;
    }

    public boolean reset() { //resets lift and slide mechs
            robot.lift.goTo(Lift.Level.INTAKE);//move intake first
            robot.horizontalSlides.goToPosition(HorizontalSlides.Position.FRONT);//then slides

        return true;
    }

}
