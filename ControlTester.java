/*
Copyright 2025 FIRST Tech Challenge Team 25790

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.robot.Robot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file contains a minimal example of a Linear "OpMode". An OpMode is a 'program' that runs
 * in either the autonomous or the TeleOp period of an FTC match. The names of OpModes appear on
 * the menu of the FTC Driver Station. When an selection is made from the menu, the corresponding
 * OpMode class is instantiated on the Robot Controller and executed.
 *
 * Remove the @Disabled annotation on the next line or two (if present) to add this OpMode to the
 * Driver Station OpMode list, or add a @Disabled annotation to prevent this OpMode from being
 * added to the Driver Station.
 */
 
@Autonomous(name="Robot Control Test")
public class ControlTester extends RobotControl {
    // Private Config
    private final double DURATION_PER_STEP = 1;
    private final double MAX_SPEED = 0.3;
    
    // Private Helpers
    private int step = 0;
    private ElapsedTime timer = new ElapsedTime();
    
    @Override
    public void opInit() {
        step = 0;
        timer = new ElapsedTime();
    }
    
    @Override
    public void opLoop() {
        print("Step", step);
        switch (step) {
            default:
                print("Executing", "well... nothing");
                break;
            case 0:
                print("Executing", "move forward");
                moveRobot(0, 1, 0, MAX_SPEED);
                break;
            case 1:
                print("Executing", "move left");
                moveRobot(-1, 0, 0, MAX_SPEED);
                break;
            case 2:
                print("Executing", "move back");
                moveRobot(0, -1, 0, MAX_SPEED);
                break;
            case 3:
                print("Executing", "move right");
                moveRobot(1, 0, 0, MAX_SPEED);
                break;
            case 5:
                print("Executing", "hold");
                moveRobot(0, 0, 0, 0);
                break;
            case 6:
                print("Executing", "move forward left");
                moveRobot(-1, 1, 0, MAX_SPEED);
                break;
            case 7:
                print("Executing", "move forward right");
                moveRobot(1, 1, 0, MAX_SPEED);
                break;
            case 8:
                print("Executing", "move backward right");
                moveRobot(1, -1, 0, MAX_SPEED);
                break;
            case 9:
                print("Executing", "move backward left");
                moveRobot(-1, -1, 0, MAX_SPEED);
                break;
            case 10:
                print("Executing", "hold");
                moveRobot(0, 0, 0, 0);
                break;
            case 11:
            case 12:
                print("Executing", "rotate ccw");
                moveRobot(0, 0, -1, MAX_SPEED);
                break;
            case 13:
                print("Executing", "hold");
                moveRobot(0, 0, 0, 0);
                break;
            case 14:
            case 15:
                print("Executing", "rotate cw");
                moveRobot(0, 0, 1, MAX_SPEED);
                break;
            case 16:
            case 17:
                print("Done!");
                moveRobot(0, 0, 0, 0);
                step = 16;
                break;
        }
        
        if (timer.seconds() >= DURATION_PER_STEP) {
            timer.reset();
            step++;
        }
    }
}
