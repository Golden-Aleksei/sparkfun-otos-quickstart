package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;

import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "VipTesting", group = "Testing")
public class viptesting extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        // Initialize other motors
        DcMotor mot6 = hardwareMap.get(DcMotor.class, "mot6");
        DcMotor mot7 = hardwareMap.get(DcMotor.class, "mot7");
        DcMotor vipside = hardwareMap.get(DcMotor.class, "vipside");
        DcMotor vipmain = hardwareMap.get(DcMotor.class, "vipmain");
        Servo xservo = hardwareMap.get(Servo.class, "xservo");
        Servo yservo = hardwareMap.get(Servo.class, "yservo");
        Servo claw = hardwareMap.get(Servo.class, "claw");
        mot6.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        vipmain.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        vipmain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mot6.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mot6.setDirection(DcMotorSimple.Direction.REVERSE);
        vipside.setDirection(DcMotorSimple.Direction.REVERSE);

        PinpointDrive drive = new PinpointDrive(hardwareMap, new Pose2d(8.5, -60, Math.toRadians(90)));

        int x = 90;
        waitForStart();
        if (isStopRequested()) return;
        //while loop for stuff
        mot6.setTargetPosition(0);
        vipmain.setTargetPosition(0);

        // Autonomous stuff
        Actions.runBlocking(
                drive.actionBuilder(new Pose2d(8.5, -60, Math.toRadians(90)))
                        // .stopAndAdd(new motaction(mot6, mot7, 500))
                        // .stopAndAdd(new vipaction(vipmain, vipside, -2000))
                        .stopAndAdd(new motaction(mot6, mot7, 96))
                        .stopAndAdd(new ServoAction(xservo, .6))
                        .stopAndAdd(new ServoAction(yservo, .25))
                        .stopAndAdd(new ServoAction(claw, 1))
                        .waitSeconds(1)
                        .stopAndAdd(new ServoAction(claw, .6))
                        .waitSeconds(.5)
                        .stopAndAdd(new ServoAction(yservo, 0))
                        .waitSeconds(10)
                        .build()
        );



    }


    public class motaction implements Action{ ; //Runs the motors 6 and 7, lifts vipslide up
        DcMotor mot2;
        DcMotor mot;
        int position;
        public motaction(DcMotor s, DcMotor t, int p){
            this.mot=s;
            this.position=p;
            this.mot2=t;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            mot.setTargetPosition(position);
            mot.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            if(mot.getCurrentPosition()> mot.getTargetPosition()-10 &&mot.getCurrentPosition()< mot.getTargetPosition()+10){
                mot.setPower(1);
            }
            else if (mot.getCurrentPosition()> mot.getTargetPosition()-50 &&mot.getCurrentPosition()< mot.getTargetPosition()+50){
                mot.setPower(1);
            }
            /*else if (mot.getCurrentPosition()> mot.getTargetPosition()-100 &&mot.getCurrentPosition()< mot.getTargetPosition()+100){
                mot.setPower(.2);
            }
            else if (mot.getCurrentPosition()> mot.getTargetPosition()-200 &&mot.getCurrentPosition()< mot.getTargetPosition()+200){
                mot.setPower(.5);
            }*/
            else{
                mot.setPower(1);
            }
            if (mot.getCurrentPosition()> mot.getTargetPosition()-10 &&mot.getCurrentPosition()< mot.getTargetPosition()+10){
                mot2.setPower(0);
            }
            else if(mot.getCurrentPosition()>mot.getTargetPosition()) {
                mot2.setPower(-mot.getPower());
            }else if (mot.getCurrentPosition()<mot.getTargetPosition()) {
                mot2.setPower(mot.getPower());
            }
            return mot.getCurrentPosition() != mot.getTargetPosition();
        }
    }

    public class vipaction implements Action{ ;
        DcMotor mot2;
        DcMotor mot;
        int position;
        public vipaction(DcMotor s, DcMotor t, int p){
            this.mot=s;
            this.position=p;
            this.mot2=t;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            mot.setTargetPosition(position);
            mot.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            if(mot.getCurrentPosition()> mot.getTargetPosition()-20 &&mot.getCurrentPosition()< mot.getTargetPosition()+20){
                mot.setPower(0);
            }
            else if (mot.getCurrentPosition()> mot.getTargetPosition()-50 &&mot.getCurrentPosition()< mot.getTargetPosition()+50){
                mot.setPower(.1);
            }
            else if (mot.getCurrentPosition()> mot.getTargetPosition()-100 &&mot.getCurrentPosition()< mot.getTargetPosition()+100){
                mot.setPower(.2);
            }
            else if (mot.getCurrentPosition()> mot.getTargetPosition()-500 &&mot.getCurrentPosition()< mot.getTargetPosition()+500){
                mot.setPower(.5);
            }
            else{
                mot.setPower(1);
            }
            if (mot.getCurrentPosition()> mot.getTargetPosition()-10 &&mot.getCurrentPosition()< mot.getTargetPosition()+10){
                mot2.setPower(0);
            }
            else if(mot.getCurrentPosition()>mot.getTargetPosition()) {
                mot2.setPower(-mot.getPower());
            }else if (mot.getCurrentPosition()<mot.getTargetPosition()) {
                mot2.setPower(mot.getPower());
            }
            return false;
        }
    }
    public class ServoAction implements Action {
        Servo servo;
        double position;

        public ServoAction(Servo s, double p) {
            this.servo = s;
            this.position = p;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            servo.setPosition(position);
            return false;
        }
    }

}

