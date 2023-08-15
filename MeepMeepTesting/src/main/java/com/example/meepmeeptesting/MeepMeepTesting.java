package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(80, 30, Math.toRadians(180), Math.toRadians(180), 12.33)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(36,-60.8, Math.toRadians(90)))
                        .splineTo(new Vector2d(36,-30), Math.toRadians(90))
                        .splineToSplineHeading(new Pose2d(32,-7, Math.toRadians(-43)), Math.toRadians(-43))
                        .splineToLinearHeading(new Pose2d(57,-12.4, Math.toRadians(0)), Math.toRadians(0))
                        .setReversed(true)
                        .lineToLinearHeading(new Pose2d(32,-7, Math.toRadians(-43)))
                        .setReversed(false)
                        .splineTo(new Vector2d(36,-20), Math.toRadians(-90))
                        .splineTo(new Vector2d(36,-35), Math.toRadians(-45))
                        .splineTo(new Vector2d(59, -35), Math.toRadians(0))
                        .build()
                );
        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}