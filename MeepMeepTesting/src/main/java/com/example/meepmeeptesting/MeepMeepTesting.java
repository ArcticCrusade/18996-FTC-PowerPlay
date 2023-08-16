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
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(36,-70, Math.toRadians(90)))
                        .splineToSplineHeading(new Pose2d(36,-30, Math.toRadians(90)), Math.toRadians(90))
                        .splineToSplineHeading(new Pose2d(-51,-22, Math.toRadians(270)), Math.toRadians(270))
                        .splineToSplineHeading(new Pose2d(21,15, Math.toRadians(0)), Math.toRadians(0))
                        .splineToSplineHeading(new Pose2d(-61,-23, Math.toRadians(135)), Math.toRadians(135))
                        .splineToSplineHeading(new Pose2d(-53,55, Math.toRadians(60)), Math.toRadians(60))
                        .splineToSplineHeading(new Pose2d(7,-62, Math.toRadians(225)), Math.toRadians(225))
                        .splineToSplineHeading(new Pose2d(-8,31, Math.toRadians(0)), Math.toRadians(0))
                        .build()
                );
        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}