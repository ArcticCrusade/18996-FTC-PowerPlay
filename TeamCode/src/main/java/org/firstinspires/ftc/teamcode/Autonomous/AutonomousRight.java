/*
- zoom to drop on high
- turn to stack and cycle all cones to that high
- park facing in starting direction
- in exactly 30 seconds
*/
package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.common.Hardware.Camera;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Config
@Autonomous(name="Imitation Right-Auto", group="Linear Opmode")
public class AutonomousRight extends LinearOpMode {
    int tag;
    Camera camera = new Camera();
    AprilTagAutonomous aprilTag;
    @Override
    public void runOpMode() {
        //yoink all of the motor declarations and their methods
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        aprilTag = new AprilTagAutonomous(camera, new int[]{0, 1, 2});
        aprilTag.getCamera().initialize(this);

        //set starting position
        Pose2d startPose = new Pose2d(-35.6,-49.6, Math.toRadians(90));
        drive.setPoseEstimate(startPose);
        TrajectorySequence start = drive.trajectorySequenceBuilder(startPose)
                .splineToSplineHeading(new Pose2d(-25.6,-2, Math.toRadians(47)), Math.toRadians(47))
                .build();
        TrajectorySequence cycle = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .setReversed(true)
                .splineToSplineHeading(new Pose2d(-53.6,-12.4, Math.toRadians(0)), Math.toRadians(0))
                .setReversed(false)
                .splineToSplineHeading(new Pose2d(-25.6,-2, Math.toRadians(47)), Math.toRadians(47))
                .build();
        TrajectorySequence park1 = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .setReversed(true)
                .splineToSplineHeading(new Pose2d(-35.2,-10.8, Math.toRadians(-90)), Math.toRadians(-90))
                .setReversed(false)
                .splineToSplineHeading(new Pose2d(-22.8,-10.4, Math.toRadians(-90)), Math.toRadians(-90))
                .build();
        TrajectorySequence park2 = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .setReversed(true)
                .splineToSplineHeading(new Pose2d(-36,-36, Math.toRadians(-90)), Math.toRadians(-90))
                .setReversed(false)
                .splineToSplineHeading(new Pose2d(-23.2,-36.4, Math.toRadians(-90)), Math.toRadians(-90))
                .build();
        TrajectorySequence park3 = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .setReversed(true)
                .splineToSplineHeading(new Pose2d(-36,-60, Math.toRadians(-90)), Math.toRadians(-90))
                .setReversed(false)
                .splineToSplineHeading(new Pose2d(-22.8,-59.2, Math.toRadians(-90)), Math.toRadians(-90))
                .build();

        waitForStart();
        tag = aprilTag.findTagIDSimple();
        drive.followTrajectorySequence(start);
        for (int i = 1; i <= 5; i++) {
            drive.followTrajectorySequence(cycle);
        }
        if (tag == 0) { //state 1
            drive.followTrajectorySequence(park1);
        } else if (tag == 1) { //state 2
            drive.followTrajectorySequence(park2);
        } else if (tag == 2) { //state 3
            drive.followTrajectorySequence(park3);
        }
    }
}