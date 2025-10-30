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
 
@Autonomous(name="Motor Test")
public class MotorTester extends RobotControl {
    private int step = 0;
    private final double MAX_SPEED = 0.5;
    private final double DURATION_PER_STEP = 1.0;
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
                print("Executing", "front left drive");
                frontLeftDrive.setPower(MAX_SPEED);
                break;
            case 1:
                print("Executing", "front right drive");
                frontLeftDrive.setPower(0);
                frontRightDrive.setPower(MAX_SPEED);
                break;
            case 2:
                print("Executing", "back left drive");
                frontRightDrive.setPower(0);
                backLeftDrive.setPower(MAX_SPEED);
                break;
            case 3:
                print("Executing", "back right drive");
                backLeftDrive.setPower(0);
                backRightDrive.setPower(MAX_SPEED);
                break;
            case 5:
            case 6:
            case 7:
            case 8:
                print("Holding...");
                backRightDrive.setPower(0);
                break;
            case 9:
            case 10:
            case 11:
            case 12:
                frontLeftDrive.setPower(MAX_SPEED);
                frontRightDrive.setPower(MAX_SPEED);
                backLeftDrive.setPower(MAX_SPEED);
                backRightDrive.setPower(MAX_SPEED);
                break;
            case 13:
            case 14:
                frontLeftDrive.setPower(MAX_SPEED);
                frontRightDrive.setPower(MAX_SPEED);
                backLeftDrive.setPower(MAX_SPEED);
                backRightDrive.setPower(MAX_SPEED);
                print("Done!");
                step = 14;
                break;
        }
        
        if (timer.seconds() >= DURATION_PER_STEP) {
            timer.reset();
            step++;
        }
    }
}
