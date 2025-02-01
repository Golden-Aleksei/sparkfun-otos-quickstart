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

@Autonomous(name="AUTOBOTS roll out", group="Testing")
public class autobots_rollOUT extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        //init other mots
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

        PinpointDrive drive = new PinpointDrive(hardwareMap, new Pose2d(-8, 63, 3 * Math.PI / 2));

        int x = 90;
        waitForStart();
        if (isStopRequested()) return;
        //while loop for stuff
        mot6.setTargetPosition(0);
        vipmain.setTargetPosition(0);/*
        while (opModeIsActive()) {

            if(mot6.getCurrentPosition()> mot6.getTargetPosition()-10 &&mot6.getCurrentPosition()< mot6.getTargetPosition()+10){
                mot6.setPower(.1);
            }
            else if (mot6.getCurrentPosition()> mot6.getTargetPosition()-50 &&mot6.getCurrentPosition()< mot6.getTargetPosition()+50){
                mot6.setPower(.1);
            }
            else if (mot6.getCurrentPosition()> mot6.getTargetPosition()-100 &&mot6.getCurrentPosition()< mot6.getTargetPosition()+100){
                mot6.setPower(.2);
            }
            else if (mot6.getCurrentPosition()> mot6.getTargetPosition()-200 &&mot6.getCurrentPosition()< mot6.getTargetPosition()+200){
                mot6.setPower(.5);
            }
            else{
                mot6.setPower(1);
            }


            if(mot6.getCurrentPosition()>mot6.getTargetPosition()) {
                mot7.setPower(-mot6.getPower());
            }else if (mot6.getCurrentPosition()<mot6.getTargetPosition()) {
                mot7.setPower(mot6.getPower());
            }

            if(vipmain.getCurrentPosition()> vipmain.getTargetPosition()-20 &&vipmain.getCurrentPosition()< vipmain.getTargetPosition()+20){
                vipmain.setPower(0);
            }
            else if (vipmain.getCurrentPosition()> vipmain.getTargetPosition()-50 &&vipmain.getCurrentPosition()< vipmain.getTargetPosition()+50){
                vipmain.setPower(.1);
            }
            else if (vipmain.getCurrentPosition()> vipmain.getTargetPosition()-100 &&vipmain.getCurrentPosition()< vipmain.getTargetPosition()+100){
                vipmain.setPower(.2);
            }
            else if (vipmain.getCurrentPosition()> vipmain.getTargetPosition()-200 &&vipmain.getCurrentPosition()< vipmain.getTargetPosition()+200){
                vipmain.setPower(.5);
            }
            else{
                vipmain.setPower(1);
            }

            vipmain.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            if(vipmain.getCurrentPosition()>vipmain.getTargetPosition()) {
                vipside.setPower(-vipmain.getPower());
            }else if (vipmain.getCurrentPosition()<vipmain.getTargetPosition()) {
                vipside.setPower(vipmain.getPower());
            }


            telemetry.addData("vipermain", vipmain.getCurrentPosition());
            telemetry.addData("target vipermain", vipmain.getTargetPosition());
            telemetry.addData("mot6", vipmain.getCurrentPosition());
            telemetry.addData("target mot6", mot6.getTargetPosition());
        }*/

            /*
                Stuff that needs to be added tp calculations, add 13 degrees to the turning
             */
        Actions.runBlocking(
                drive.actionBuilder(new Pose2d(-8, 63, 3 * Math.PI / 2))

                        // Start the action builder at the initial pose
                        .strafeTo(new Vector2d(-8, 35))

                        .turn(Math.PI)
                        .strafeTo(new Vector2d(-34, 37))
                        .strafeTo(new Vector2d(-34, 10))
                        .strafeTo(new Vector2d(-45, 10))
                        .strafeTo(new Vector2d(-45, 55))//to box
                        .strafeTo(new Vector2d(-45, 10))
                        .strafeTo(new Vector2d(-52, 10))
                        .strafeTo(new Vector2d(-52, 55))
                        .strafeTo(new Vector2d(-52, 10))
                        .strafeTo(new Vector2d(-60, 10))
                        .strafeTo(new Vector2d(-60, 55))
                        .strafeTo(new Vector2d(-60, 40))

                        .strafeTo(new Vector2d(-34, 60))


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





