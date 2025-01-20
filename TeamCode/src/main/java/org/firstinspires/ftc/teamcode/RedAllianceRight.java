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

@Autonomous(name = "RedAllianceRight", group = "RedAlliance")
public class RedAllianceRight extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        // Initialize other motors
        DcMotor mot6 = hardwareMap.get(DcMotor.class, "mot6");
        DcMotor mot7 = hardwareMap.get(DcMotor.class, "mot7");
        DcMotor vipside = hardwareMap.get(DcMotor.class, "vipside");
        DcMotor vipmain = hardwareMap.get(DcMotor.class, "vipmain");
        mot6.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        vipmain.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        vipmain.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mot6.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        mot6.setDirection(DcMotorSimple.Direction.REVERSE);
        vipside.setDirection(DcMotorSimple.Direction.REVERSE);

        PinpointDrive drive = new PinpointDrive(hardwareMap, new Pose2d(-60, 8.5, Math.toRadians(90)));

        int x = 90;
        waitForStart();
        if (isStopRequested()) return;
        //while loop for stuff
        mot6.setTargetPosition(0);

        // Autonomous stuff
        Actions.runBlocking(
                drive.actionBuilder(new Pose2d(-60, 8.5, Math.toRadians(90)))
                        // Step 1
                        .strafeTo(new Vector2d(-40, 11))// -40,11
                        .waitSeconds(1) // placeholder
                        .strafeToLinearHeading(new Vector2d(-10, 46), Math.toRadians(270))// -10,46
                        .strafeTo(new Vector2d(-55, 46))// -55,46
                        .strafeTo(new Vector2d(-10, 46))// -10,46
                        .strafeTo(new Vector2d(-10, 57))// -10,57
                        .strafeTo(new Vector2d(-55, 57))// -55,57
                        .strafeTo(new Vector2d(-10, 47))// -10,57
                        .strafeTo(new Vector2d(-10, 62))// -10,62
                        .strafeTo(new Vector2d(-55, 62))// -55,62
                        .strafeTo(new Vector2d(-35, 62))// -35,62
                        .strafeTo(new Vector2d(-35, 47))// -35,47
                        .strafeTo(new Vector2d(-55, 47))// -55,47

                        // Step 2
                        .waitSeconds(1) // placeholder
                        .strafeToLinearHeading(new Vector2d(-35, 9), Math.toRadians(90))// -35, 9
                        .waitSeconds(1) //placeholder, hangs second specimen
                        .strafeToLinearHeading(new Vector2d(-55, 45), Math.toRadians(270))// -55,45
                        .waitSeconds(1) //placeholder, grabs specimen from human player
                        .strafeToLinearHeading(new Vector2d(-35, 7), Math.toRadians(90))// -35,7
                        .waitSeconds(1) //placeholder, hangs third specimen
                        .strafeToLinearHeading(new Vector2d(-55, 45), Math.toRadians(270))// -55,45
                        .waitSeconds(1) //placeholder, grabs specimen from human player
                        .strafeToLinearHeading(new Vector2d(-35, 5), Math.toRadians(90))// -35,5
                        .waitSeconds(1) //placeholder, hangs fourth specimen
                        .build()

        );


    }


    public class motaction implements Action{ ;
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
                mot.setPower(.1);
            }
            else if (mot.getCurrentPosition()> mot.getTargetPosition()-50 &&mot.getCurrentPosition()< mot.getTargetPosition()+50){
                mot.setPower(.1);
            }
            else if (mot.getCurrentPosition()> mot.getTargetPosition()-100 &&mot.getCurrentPosition()< mot.getTargetPosition()+100){
                mot.setPower(.2);
            }
            else if (mot.getCurrentPosition()> mot.getTargetPosition()-200 &&mot.getCurrentPosition()< mot.getTargetPosition()+200){
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
            return mot.getCurrentPosition() != mot.getTargetPosition();
        }
    }

}
