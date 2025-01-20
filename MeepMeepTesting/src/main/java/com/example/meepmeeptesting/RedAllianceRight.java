package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;


public class RedAllianceRight {
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel,// maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(8.5, -60, Math.toRadians(90)))
                        // Step 1: Hang first specimen and get specimens to build area
                        .lineTo(new Vector2d(11,-40)) // (11,-40)
                        .waitSeconds(1) // placeholder, going to be when the robot is hanging specimen
                        .lineToLinearHeading(new Pose2d(46, -10, Math.toRadians(270))) // (46,-10)
                        .forward(45) // (46,-55)
                        .back(45) // (46,-10)
                        .strafeLeft(11) // (57,-10)
                        .forward(45) // (57,-55)
                        .back(45) // (57,-10)
                        .strafeLeft(5) // (62,-10)
                        .forward(45) // (62,-55)
                        .back(20)  // (62,-35)
                        .strafeRight(15) //(47,-35)
                        .forward(20) // (47,-55)
                        // Step 2: hangs 3 specimens
                        .waitSeconds(1) //placeholder, going to be when robot is getting specimen from human player
                        .lineToLinearHeading(new Pose2d(11, -35, Math.toRadians(90))) // (9,-35)
                        .waitSeconds(1) //placeholder, hangs second specimen
                        .lineToLinearHeading(new Pose2d(45, -55, Math.toRadians(270))) // (45,-55)
                        .waitSeconds(1) //placeholder, grabs specimen from human player
                        .lineToLinearHeading(new Pose2d(9, -35, Math.toRadians(90))) // (7,-35)
                        .waitSeconds(1) //placeholder, hangs third specimen
                        .lineToLinearHeading(new Pose2d(45, -55, Math.toRadians(270))) // (45,-55)
                        .waitSeconds(1) //placeholder, grabs specimen from human player
                        .lineToLinearHeading(new Pose2d(7, -35, Math.toRadians(90))) // (5,-35)
                        .waitSeconds(1) //placeholder, hangs fourth specimen
                        .build());


        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}