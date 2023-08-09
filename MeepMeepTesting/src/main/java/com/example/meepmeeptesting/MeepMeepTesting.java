package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);
        /* TrajectorySequence park1 = drive.trajectorySequenceBuilder(cycle.end())
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(-35.2,-10.8, Math.toRadians(-90)), Math.toRadians(-90))
                .setReversed(false)
                .splineToLinearHeading(new Pose2d(-22.8,-10.4, Math.toRadians(-90)), Math.toRadians(-90))
                .build();
        TrajectorySequence park2 = drive.trajectorySequenceBuilder(cycle.end())
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(-36,-36, Math.toRadians(-90)), Math.toRadians(-90))
                .setReversed(false)
                .splineToLinearHeading(new Pose2d(-23.2,-36.4, Math.toRadians(-90)), Math.toRadians(-90))
                .build();
        TrajectorySequence park3 = drive.trajectorySequenceBuilder(cycle.end())
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(-36,-60, Math.toRadians(-90)), Math.toRadians(-90))
                .setReversed(false)
                .splineToLinearHeading(new Pose2d(-22.8,-59.2, Math.toRadians(-90)), Math.toRadians(-90))
                .build();*/
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-35.6,-60.4, Math.toRadians(90)))
                                .splineToLinearHeading(new Pose2d(-34.4,-11.2, Math.toRadians(47)), Math.toRadians(47))
                                .setReversed(true)
                                .splineToLinearHeading(new Pose2d(-58.4,-12, Math.toRadians(0)), Math.toRadians(0))
                                .setReversed(false)
                                .splineToLinearHeading(new Pose2d(-34.4,-11.2, Math.toRadians(47)), Math.toRadians(47))
                                .setReversed(true)
                                .splineToLinearHeading(new Pose2d(-58.4,-12, Math.toRadians(0)), Math.toRadians(0))
                                .setReversed(false)
                                .splineToLinearHeading(new Pose2d(-34.4,-11.2, Math.toRadians(47)), Math.toRadians(47))
                                .setReversed(true)
                                .splineToLinearHeading(new Pose2d(-58.4,-12, Math.toRadians(0)), Math.toRadians(0))
                                .setReversed(false)
                                .splineToLinearHeading(new Pose2d(-34.4,-11.2, Math.toRadians(47)), Math.toRadians(47))
                                .setReversed(true)
                                .splineToLinearHeading(new Pose2d(-58.4,-12, Math.toRadians(0)), Math.toRadians(0))
                                .setReversed(false)
                                .splineToLinearHeading(new Pose2d(-34.4,-11.2, Math.toRadians(47)), Math.toRadians(47))
                                .setReversed(true)
                                .splineToLinearHeading(new Pose2d(-58.4,-12, Math.toRadians(0)), Math.toRadians(0))
                                .setReversed(false)
                                .splineToLinearHeading(new Pose2d(-34.4,-11.2, Math.toRadians(47)), Math.toRadians(47))

                                .setReversed(true)
                                .splineToLinearHeading(new Pose2d(-47,-12.6, Math.toRadians(0)), Math.toRadians(0))
                                .setReversed(false)
                                .splineToLinearHeading(new Pose2d(-23.6,-60.4, Math.toRadians(0)), Math.toRadians(0))
                                .build()
                );
        meepMeep.setBackground(MeepMeep.Background.FIELD_POWERPLAY_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}