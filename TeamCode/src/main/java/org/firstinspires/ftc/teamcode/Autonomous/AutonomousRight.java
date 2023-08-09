/*
- zoom to drop on high
- turn to stack and cycle all cones to that high
- park facing in starting direction
- in exactly 30 seconds
*/
package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.common.Hardware.Camera;
import org.firstinspires.ftc.common.Software.AprilTagAutonomous;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import java.util.ArrayList;
import java.util.List;

@Config
@Autonomous(name="Imitation Right-Auto", group="Linear Opmode")
public class AutonomousRight extends LinearOpMode {
    int tag;
    Camera camera = new Camera();
    List<Integer> tagIDs = new ArrayList<>(3);
    AprilTagAutonomous aprilTag;
    @Override
    public void runOpMode() {
        tagIDs.add(0);
        tagIDs.add(1);
        tagIDs.add(2);
        //yoink all of the motor declarations and their methods
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        aprilTag = new AprilTagAutonomous(camera, tagIDs);
        aprilTag.getCamera().initialize(this);
        waitForStart();
        if (isStopRequested()) return;
        //set starting position
        Pose2d startPose = new Pose2d(-35.6,-49.6, Math.toRadians(90));
        drive.setPoseEstimate(startPose);
        Trajectory start = drive.trajectoryBuilder(startPose)
                .splineToLinearHeading(new Pose2d(-25.6,-2, Math.toRadians(47)), Math.toRadians(47))
                .build();
        TrajectorySequence cycle = drive.trajectorySequenceBuilder(start.end())
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(-53.6,-12.4, Math.toRadians(0)), Math.toRadians(0))
                .setReversed(false)
                .splineToLinearHeading(new Pose2d(-25.6,-2, Math.toRadians(47)), Math.toRadians(47))
                .build();
        TrajectorySequence park1 = drive.trajectorySequenceBuilder(cycle.end())
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
                .build();
        tag = aprilTag.findTagIDSimple();
        drive.followTrajectory(start);
        for (int i = 1; i <= 5; i++) {
            drive.followTrajectorySequence(cycle);
        }
        if (tag == 0) { //state 1 - left
            drive.followTrajectorySequence(park1);
        } else if (tag == 1) { //state 2 - middle
            drive.followTrajectorySequence(park2);
        } else if (tag == 2) { //state 3 - right
            drive.followTrajectorySequence(park3);
        }
    }
}