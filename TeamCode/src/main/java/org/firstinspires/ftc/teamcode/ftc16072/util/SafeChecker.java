package org.firstinspires.ftc.teamcode.ftc16072.util;

import org.firstinspires.ftc.teamcode.ftc16072.Robot;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.HorizontalSlides;
import org.firstinspires.ftc.teamcode.ftc16072.mechanisms.Lift;

public class SafeChecker {
    Robot robot;
    public SafeChecker(Robot robot){
        this.robot = robot;
    }

    public boolean moveHorizontalSlides(HorizontalSlides.Position position){ //horizontal slide level lock handler
        if (position == HorizontalSlides.Position.BACK){ //if going TO back position
            if(!robot.lift.isSafe()){ //checks if lift is above ground level
                return false;
            }
        } else if (robot.horizontalSlides.getSlidesPosition() == robot.horizontalSlides.BACK_SERVO_POSITION){ //if coming FROM back position
            if(!robot.lift.isSafe()){ //checks if lift is above ground level
                return false;
            }
        }
        robot.horizontalSlides.goTo(position); //moves slides since conditions passed
        return true;
    }

    public boolean moveHorizontalSlidesManually(double position){ //manual horizontal slide handler
        if(position>0){ //if going forward
            robot.horizontalSlides.goTo(position); //do it, since forward shouldn't raise any problems
            return true;
        } else{ //if going backward
            if(!robot.lift.isSafe()){ //check if lift is above ground level
                return false;
            }
            robot.horizontalSlides.goTo(position); //move slides since conditions passed
            return true;
        }
    }
    public boolean moveVerticalLiftManually(int change){ //manual vertical lift handler
        if(change > 0){ //if going up
            robot.lift.adjustPosition(change); //do it, since going up shouldn't raise any problems
            return true;
        } else{ //if going down
            if(!robot.horizontalSlides.isSafe()){ //check if slides are in middle position or above
                return false;
            }
            robot.lift.adjustPosition(change); //move lift since conditions passed
            return true;
        }
    }


    public boolean moveVerticalLift (Lift.Level level){ //vertical lift level lock handler
        if(level== Lift.Level.GROUND || level== Lift.Level.INTAKE){ //if moving to ground or intake
            if(!robot.horizontalSlides.isSafe()){ //make sure slides are in middle position or above
                return false;
            }

        }
        robot.lift.goTo(level); //move lift since conditions passed
        return true;
    }

    public boolean reset(){ //resets lift and slide mechs
        if(robot.lift.isSafe()){ //if the lift is currently in low, middle, or high positions
            robot.horizontalSlides.goTo(HorizontalSlides.Position.FRONT); //move slides first
            robot.lift.goTo(Lift.Level.INTAKE); //then intake
            return true;
        } else{ //if the lift is in intake or ground positions
            robot.lift.goTo(Lift.Level.INTAKE);//move intake first
            robot.horizontalSlides.goTo(HorizontalSlides.Position.FRONT);//then slides
            return true;
        }
    }

}
