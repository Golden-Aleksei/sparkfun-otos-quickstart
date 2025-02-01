/* Created by Phil Malone. 2023.
    This class illustrates my simplified Odometry Strategy.
    It implements basic straight line motions but with heading and drift controls to limit drift.
    See the readme for a link to a video tutorial explaining the operation and limitations of the code.
 */

package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
 * This OpMode illustrates a teleop OpMode for an Omni robot.
 * An external "Robot" class is used to manage all motor/sensor interfaces, and to assist driving functions.
 * The IMU gyro is used to stabilize the heading when the operator is not requesting a turn.
 */

@TeleOp(name="Sample Teleop", group = "Mr. Phil")
public class SampleTeleop extends LinearOpMode {
    DcMotor mot6;
    DcMotor mot7;
    DcMotor vipmain;//viper
    DcMotor vipside;
    int pos = 0;
    int limit = 800;
    int viperpos = 0;
    Servo xservo;
    Servo yservo;
    Servo claw;
    final double SAFE_DRIVE_SPEED = 0.8; // Adjust this to your robot and your driver.  Slower usually means more accuracy.  Max value = 1.0
    final double SAFE_STRAFE_SPEED = 0.8; // Adjust this to your robot and your driver.  Slower usually means more accuracy.  Max value = 1.0
    final double SAFE_YAW_SPEED = 0.5; // Adjust this to your robot and your driver.  Slower usually means more accuracy.  Max value = 1.0
    final double HEADING_HOLD_TIME = 10.0; // How long to hold heading once all driver input stops. (This Avoids effects of Gyro Drift)

    // local parameters
    ElapsedTime stopTime = new ElapsedTime();  // Use for timeouts.
    boolean autoHeading = false; // used to indicate when heading should be locked.

    // get an instance of the "Robot" class.
    SimplifiedOdometryRobot robot = new SimplifiedOdometryRobot(this);

    @Override
    public void runOpMode() {
        mot6 = hardwareMap.get(DcMotor.class, "mot6");
        mot7 = hardwareMap.get(DcMotor.class, "mot7");
        vipmain = hardwareMap.get(DcMotor.class, "vipmain");
        vipside = hardwareMap.get(DcMotor.class, "vipside");
        xservo = hardwareMap.get(Servo.class, "xservo");
        yservo = hardwareMap.get(Servo.class, "yservo");
        claw = hardwareMap.get(Servo.class, "claw");
        mot7.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mot6.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        vipmain.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        vipside.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        mot6.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mot7.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        vipside.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        vipmain.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        mot6.setDirection(DcMotorSimple.Direction.REVERSE);
        vipside.setDirection(DcMotorSimple.Direction.REVERSE);
        claw.setPosition(.8);

        // Initialize the drive hardware & Turn on telemetry
        robot.initialize(true);

        // Wait for driver to press start
        while (opModeInInit()) {
            telemetry.addData(">", "Touch Play to drive");

            // Read and display sensor data
            robot.readSensors();
            telemetry.update();
        }
        ;

        while (opModeIsActive()) {
            robot.readSensors();

            // Allow the driver to reset the gyro by pressing both small gamepad buttons
            if (gamepad1.options && gamepad1.share) {
                robot.resetHeading();
                robot.resetOdometry();
            }

            // read joystick values and scale according to limits set at top of this file
            double drive = -gamepad1.left_stick_y * SAFE_DRIVE_SPEED;      //  Fwd/back on left stick
            double strafe = -gamepad1.left_stick_x * SAFE_STRAFE_SPEED;     //  Left/Right on left stick
            double yaw = -gamepad1.right_stick_x * SAFE_YAW_SPEED;       //  Rotate on right stick

            //  OR... For special conditions, Use the DPAD to make pure orthoginal motions
            if (gamepad1.dpad_left) {
                strafe = SAFE_DRIVE_SPEED / 2.0;
            } else if (gamepad1.dpad_right) {
                strafe = -SAFE_DRIVE_SPEED / 2.0;
            } else if (gamepad1.dpad_up) {
                drive = SAFE_DRIVE_SPEED / 2.0;
            } else if (gamepad1.dpad_down) {
                drive = -SAFE_STRAFE_SPEED / 2.0;
            }

            // This is where we heep the robot heading locked so it doesn't turn while driving or strafing in a straight line.
            // Is the driver turning the robot, or should it hold its heading?
            if (Math.abs(yaw) > 0.05) {
                // driver is commanding robot to turn, so turn off auto heading.
                autoHeading = false;
            } else {
                // If we are not already locked, wait for robot to stop rotating (<2 deg per second) and then lock-in the current heading.
                if (!autoHeading && Math.abs(robot.getTurnRate()) < 2.0) {
                    robot.yawController.reset(robot.getHeading());  // Lock in the current heading
                    autoHeading = true;
                }
            }

            // If auto heading is on, override manual yaw with the value generated by the heading controller.
            if (autoHeading) {
                yaw = robot.yawController.getOutput(robot.getHeading());
            }

            //  Drive the wheels based on the desired axis motions
            robot.moveRobot(drive, strafe, yaw);

            // If the robot has just been sitting here for a while, make heading setpoint track any gyro drift to prevent rotating.
            if ((drive == 0) && (strafe == 0) && (yaw == 0)) {
                if (stopTime.time() > HEADING_HOLD_TIME) {
                    robot.yawController.reset(robot.getHeading());  // just keep tracking the current heading
                }
            } else {
                stopTime.reset();
            }
// my own motsssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss
            if (gamepad2.left_stick_y < 0) {
                pos = pos + ((int) (gamepad2.left_stick_y * -20));
                if (pos > limit) {
                    pos = limit;
                }

            }
            if (gamepad2.left_stick_y > 0) {
                pos = pos + ((int) (gamepad2.left_stick_y * -20));
                if (pos < 0) {

                    pos = 0;
                }
            }
            mot6.setTargetPosition(pos);

            if (pos < 20 && gamepad2.left_stick_y == 0) {
                mot6.setPower(0);
                mot7.setPower(0);
            } else if (pos < 20 && gamepad2.left_stick_y < 0) {
                mot6.setPower(0);
                mot7.setPower(0);
            } else if (mot6.getCurrentPosition() > pos - 10 && mot6.getCurrentPosition() < pos + 10) {
                mot6.setPower(.2);
            } else if (mot6.getCurrentPosition() > pos - 50 && mot6.getCurrentPosition() < pos + 50) {
                mot6.setPower(1);
            } /*else if (mot6.getCurrentPosition() > pos - 100 && mot6.getCurrentPosition() < pos + 100) {
                mot6.setPower(.2);
            } else if (mot6.getCurrentPosition() > pos - 200 && mot6.getCurrentPosition() < pos + 200) {
                mot6.setPower(.5);*/
             else {
                mot6.setPower(1);
            }

            mot6.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            if (mot6.getCurrentPosition() > pos) {
                mot7.setPower(-mot6.getPower());
            } else if (mot6.getCurrentPosition() < pos) {
                mot7.setPower(mot6.getPower());
            }
            if (gamepad2.left_trigger > 0) {
                if (viperpos < 0) {
                    viperpos += 100;
                }
                // Increase target position
            } else if (gamepad2.right_trigger > 0) {
                if (viperpos > -2500) {
                    viperpos -= 100;
                } // Decrease target position
                if (viperpos < -2500) {
                    viperpos = -2500;
                }
            }
            vipmain.setTargetPosition(viperpos);
            if (vipmain.getCurrentPosition() > viperpos - 20 && vipmain.getCurrentPosition() < viperpos + 20) {
                vipmain.setPower(0);
            } else if (vipmain.getCurrentPosition() > viperpos - 50 && vipmain.getCurrentPosition() < viperpos + 50) {
                vipmain.setPower(.1);
            } else if (vipmain.getCurrentPosition() > viperpos - 100 && vipmain.getCurrentPosition() < viperpos + 100) {
                vipmain.setPower(.2);
            } else if (vipmain.getCurrentPosition() > viperpos - 200 && vipmain.getCurrentPosition() < viperpos + 200) {
                vipmain.setPower(.5);
            } else {
                vipmain.setPower(1);
            }

            vipmain.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            if (vipmain.getCurrentPosition() > viperpos) {
                vipside.setPower(-vipmain.getPower());
            } else if (vipmain.getCurrentPosition() < viperpos) {
                vipside.setPower(vipmain.getPower());
            }


            //servooooooooooooooooooooo
            yservo.setPosition(yservo.getPosition());
            if(gamepad2.y){
                xservo.setPosition(.25);;
            }

            if (gamepad2.dpad_right){
                xservo.setPosition(xservo.getPosition()-.05);

            } else if (gamepad2.dpad_left) {
                xservo.setPosition(xservo.getPosition()+.05);
            }


            if (gamepad2.dpad_up) {
                yservo.setPosition(yservo.getPosition() - .05);

            } else if (gamepad2.dpad_down) {
                if (yservo.getPosition() > .7) {
                    yservo.setPosition(yservo.getPosition());
                } else {
                    yservo.setPosition(yservo.getPosition() + .05);
                }


            }
            if (gamepad2.right_bumper) {
                if (claw.getPosition() < 0.2) {// limits how much  claw can close
                    claw.setPosition(claw.getPosition());
                } else {
                    claw.setPosition(claw.getPosition() - .02);
                }
            } else if (gamepad2.left_bumper) {


                claw.setPosition(claw.getPosition() + .02);

            }

            telemetry.addData("xservo", xservo.getPosition());
            telemetry.addData("yservo", yservo.getPosition());
            telemetry.addData("claw", claw.getPosition());
            telemetry.addData("mot6", mot6.getCurrentPosition());
            telemetry.addData("vipmain", vipmain.getCurrentPosition());
        }

        }
    }

