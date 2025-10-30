package org.firstinspires.ftc.teamcode;

import java.util.List;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.robotcore.external.navigation.Rotation;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

public class RobotControl extends LinearOpMode {
    // Private Config
    private final double ROTATION_POWER = 0.8;
    private ElapsedTime elapsedTime = null;
    private ElapsedTime resetCounter = null;
    
    // Private Objects
    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTag;
    
    // Private waiters
    // private static ElapsedTime heightWaiter = new ElapsedTime();
    
    // Public Objects
    public List<AprilTagDetection> aprilTagDetections;
    
    // Position Variables
    public double posX = 0;
    public double posY = 0;
    public double detectionId = -1;
    
    // DC Motors
    public DcMotor frontLeftDrive   = null;
    public DcMotor frontRightDrive  = null;
    public DcMotor backLeftDrive    = null;
    public DcMotor backRightDrive   = null;
    
    public DcMotor spinnerDrive     = null;
    public DcMotor leftSlide        = null;
    public DcMotor rightSlide       = null;
    
    // Servo Motors
    public Servo   servo            = null;
    
    // Initial Values for DC Motors
    public double frontLeftPosition  = 0;
    // public double frontRightPosition = 0;
    // public double initialHeightLeft  = 0;
    // public double initialHeightRight = 0;
    
    Gamepad controller1     = new Gamepad();
    Gamepad controller2     = new Gamepad();

    Gamepad controller1Prev = new Gamepad();
    Gamepad controller2Prev = new Gamepad();

    
    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        
        // Initialize DC Motors
        frontLeftDrive   = hardwareMap.get(DcMotor.class, "front_left_drive");
        frontRightDrive  = hardwareMap.get(DcMotor.class, "front_right_drive");
        backLeftDrive    = hardwareMap.get(DcMotor.class, "back_left_drive");
        backRightDrive   = hardwareMap.get(DcMotor.class, "back_right_drive");
        
        spinnerDrive     = hardwareMap.get(DcMotor.class, "spinner");
        
        leftSlide        = hardwareMap.get(DcMotor.class, "left_slide");
        leftSlide        .setDirection(DcMotor.Direction.REVERSE);
        leftSlide        .setMode(DcMotor.RunMode.RESET_ENCODERS);
        leftSlide        .setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        
        rightSlide       = hardwareMap.get(DcMotor.class, "right_slide");
        rightSlide       .setDirection(DcMotor.Direction.FORWARD);
        rightSlide       .setMode(DcMotor.RunMode.RESET_ENCODERS);
        rightSlide       .setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        
        frontLeftDrive   .setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive  .setDirection(DcMotor.Direction.FORWARD);
        backLeftDrive    .setDirection(DcMotor.Direction.REVERSE);
        backRightDrive   .setDirection(DcMotor.Direction.FORWARD);
        
        // Initialize Servo Motors
        // armClaw          = hardwareMap.get(Servo.class,   "arm_claw");
        
        // armClaw          .setDirection(Servo.Direction.FORWARD);
        // armClaw          .scaleRange(0, 0.3);
        
        // Initialize April Tags
        aprilTag = AprilTagProcessor.easyCreateWithDefaults();
        visionPortal = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam 1"), aprilTag);
        
        elapsedTime = new ElapsedTime();
        resetCounter = new ElapsedTime();
        
        opInit();
        
        // Wait for the START input
        waitForStart();
        
        elapsedTime.reset();
        
        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");
            controller1Prev.copy(controller1);
            controller2Prev.copy(controller2);
            controller1    .copy(gamepad1);
            controller2    .copy(gamepad2);
            
            if (getElapsedTimeSeconds() <= 2.0) {
                leftSlide.setPower(-0.3);
                rightSlide.setPower(-0.3);
            } else if (getElapsedTimeSeconds() <= 2.1) {
                leftSlide.setPower(0);
                rightSlide.setPower(0);
            }
            
            aprilTagDetections = aprilTag.getDetections();
            for (AprilTagDetection detection : aprilTagDetections) {
                if (detection.metadata != null) {
                    // Left April Tag of Standing
                    // if (detection.id == 13 || detection.id == 16) {
                        posX = detection.ftcPose.x;
                        posY = detection.ftcPose.y;
                        detectionId = detection.id;
                    // }
                }
            }
            if (aprilTagDetections.size() == 0) {
                detectionId = -1;
            }
            opLoop();
            telemetry.update();
        }
    }
    
    public void opInit() {
        // Implementation In Op Modes
    }
    
    public void opLoop() {
        // Implementation In Op Modes
    }
    
    public void moveRobot(double powerX, double powerY, double turnX) {
        moveRobot(powerX, powerY, turnX, 0.4);
    }
    
    public void moveRobot(double powerX, double powerY, double turnX, double maxPower) {
        frontLeftDrive .setPower((powerY + powerX + (turnX * ROTATION_POWER)) * maxPower * 1.0);
        frontRightDrive.setPower((powerY - powerX - (turnX * ROTATION_POWER)) * maxPower * 1.0);
        backLeftDrive  .setPower((powerY - powerX + (turnX * ROTATION_POWER)) * maxPower * 1.0);
        backRightDrive .setPower((powerY + powerX - (turnX * ROTATION_POWER)) * maxPower * 1.0);
        // posX -= powerX * 0.1;
        // posY -= powerY * 0.1;
    }
    
    public void doSpinner(boolean spin) {
        spinnerDrive.setPower(spin ? 0.3 : 0);
    }
    
    public void setLifted(boolean lifted) {
        double MIN_VALUE = -30;
        double MAX_VALUE = 3300;
        double height = lifted ? 1.0 : 0.0;
        double currentLeft = leftSlide.getCurrentPosition();
        double currentRight = rightSlide.getCurrentPosition();
        double targetPosition = (height * (MAX_VALUE - MIN_VALUE)) + MIN_VALUE;
        double powerLeft  = Math.min((targetPosition - currentLeft) / 1000, 1.0);
        double powerRight = Math.min((targetPosition - currentRight) / 1000, 1.0);
        leftSlide .setPower(powerLeft);
        rightSlide.setPower(powerRight);
        
        if (!lifted) {
            resetCounter.reset();
        }
        
        if (resetCounter.seconds() >= 1 && !lifted) {
            print("reset");
            leftSlide        .setMode(DcMotor.RunMode.RESET_ENCODERS);
            leftSlide        .setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            rightSlide       .setMode(DcMotor.RunMode.RESET_ENCODERS);
            rightSlide       .setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }
    
    private int sign(double value) {
        return value >= 0 ? 1 : -1;
    }
    
    public void printTimes() {
        print();
        print("Time elapsed", getElapsedTimeSeconds());
        print("Time remaining", getRemainingTimeSeconds());
    }
    
    public void printControllerPrevStatuses() {
        print();
        print();
        print("Controller 1");
        print("a",                  controller1Prev.a);
        print("b",                  controller1Prev.b);
        print("x",                  controller1Prev.x);
        print("y",                  controller1Prev.y);
        print("dpad_up",            controller1Prev.dpad_up);
        print("dpad_left",          controller1Prev.dpad_left);
        print("dpad_down",          controller1Prev.dpad_down);
        print("dpad_right",         controller1Prev.dpad_right);
        print("left_stick_x",       controller1Prev.left_stick_x);
        print("left_stick_y",       controller1Prev.left_stick_y);
        print("left_stick_button",  controller1Prev.left_stick_button);
        print("right_stick_x",      controller1Prev.right_stick_x);
        print("right_stick_y",      controller1Prev.right_stick_y);
        print("right_stick_button", controller1Prev.right_stick_button);
        print("left_bumper",        controller1Prev.left_bumper);
        print("right_bumper",       controller1Prev.right_bumper);
        print("right_trigger",      controller1Prev.right_trigger);
        print("left_trigger",       controller1Prev.left_trigger);
        print();
        print();
        print("Controller 2");
        print("a",                  controller2Prev.a);
        print("b",                  controller2Prev.b);
        print("x",                  controller2Prev.x);
        print("y",                  controller2Prev.y);
        print("dpad_up",            controller2Prev.dpad_up);
        print("dpad_left",          controller2Prev.dpad_left);
        print("dpad_down",          controller2Prev.dpad_down);
        print("dpad_right",         controller2Prev.dpad_right);
        print("left_stick_x",       controller2Prev.left_stick_x);
        print("left_stick_y",       controller2Prev.left_stick_y);
        print("left_stick_button",  controller2Prev.left_stick_button);
        print("right_stick_x",      controller2Prev.right_stick_x);
        print("right_stick_y",      controller2Prev.right_stick_y);
        print("right_stick_button", controller2Prev.right_stick_button);
        print("left_bumper",        controller2Prev.left_bumper);
        print("right_bumper",       controller2Prev.right_bumper);
        print("left_trigger",       controller2Prev.left_trigger);
        print("right_trigger",      controller2Prev.right_trigger);
    }
    
    public void printControllerStatuses() {
        print();
        print();
        print("Controller 1");
        print("a",                  controller1.a);
        print("b",                  controller1.b);
        print("x",                  controller1.x);
        print("y",                  controller1.y);
        print("dpad_up",            controller1.dpad_up);
        print("dpad_left",          controller1.dpad_left);
        print("dpad_down",          controller1.dpad_down);
        print("dpad_right",         controller1.dpad_right);
        print("left_stick_x",       controller1.left_stick_x);
        print("left_stick_y",       controller1.left_stick_y);
        print("left_stick_button",  controller1.left_stick_button);
        print("right_stick_x",      controller1.right_stick_x);
        print("right_stick_y",      controller1.right_stick_y);
        print("right_stick_button", controller1.right_stick_button);
        print("left_bumper",        controller1.left_bumper);
        print("right_bumper",       controller1.right_bumper);
        print("right_trigger",      controller1.right_trigger);
        print("left_trigger",       controller1.left_trigger);
        print();
        print();
        print("Controller 2");
        print("a",                  controller2.a);
        print("b",                  controller2.b);
        print("x",                  controller2.x);
        print("y",                  controller2.y);
        print("dpad_up",            controller2.dpad_up);
        print("dpad_left",          controller2.dpad_left);
        print("dpad_down",          controller2.dpad_down);
        print("dpad_right",         controller2.dpad_right);
        print("left_stick_x",       controller2.left_stick_x);
        print("left_stick_y",       controller2.left_stick_y);
        print("left_stick_button",  controller2.left_stick_button);
        print("right_stick_x",      controller2.right_stick_x);
        print("right_stick_y",      controller2.right_stick_y);
        print("right_stick_button", controller2.right_stick_button);
        print("left_bumper",        controller2.left_bumper);
        print("right_bumper",       controller2.right_bumper);
        print("left_trigger",       controller2.left_trigger);
        print("right_trigger",      controller2.right_trigger);
    }
    
    public void vibrateSoft() {
        gamepad1.rumble(30);
        gamepad1.setLedColor(255,0,0,1000);
    }
    
    public void vibrate() {
        gamepad1.rumble(1000);
        gamepad1.setLedColor(255,0,0,1000);
    }
    
    public void vibrateBlips() {
        vibrateBlips(3);
    }
    
    public void vibrateBlips(int blips) {
        gamepad1.rumbleBlips(blips);
    }
    
    public double getElapsedTimeSeconds() {
        return elapsedTime.seconds();
    }
    
    public double getRemainingTimeSeconds() {
        return Math.max((2.0 * 60) - elapsedTime.seconds(), 0);
    }
    
    public void print() {
        print("");
    }
    
    public void print(String message) {
        telemetry.addLine(message);
    }
    
    public void print(String message, Object data) {
        telemetry.addData(message, data);
    }
    
    public void print(String message, Object ...data) {
        telemetry.addData(message, new String(new char[data.length]).replace("\0", "%s "), data);
    }
    
    public void print(String message, String template, Object ...data) {
        telemetry.addData(message, template, data);
    }
}
