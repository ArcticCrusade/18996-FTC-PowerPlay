package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Config
@Autonomous(name="RRPractical", group="Linear Opmode")
public class RRPractical extends LinearOpMode {

    @Override
    public void runOpMode() {
        //yoink all of the motor declarations and their methods
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        //set starting position
        Pose2d startPose = new Pose2d(-36, 72, Math.toRadians(270));
        drive.setPoseEstimate(startPose);

        TrajectorySequence startAlign = drive.trajectorySequenceBuilder(startPose)
                .addDisplacementMarker(() -> {
                    drive.servosGoToUpright();
                    drive.operateClaw(0);
                })
                .splineTo(new Vector2d(-30, 8), Math.toRadians(270))
                .setReversed(true)
                .addDisplacementMarker(() -> {
                    drive.operateClaw(1);
                })
                .splineToLinearHeading(new Pose2d(-42.67, 6, Math.toRadians(-10)), Math.toRadians(-10))
                .build();
        TrajectorySequence cycle = drive.trajectorySequenceBuilder(new Pose2d(-42.67, 6, Math.toRadians(-10)))
                .addTemporalMarker(0, () -> {
                    drive.servosGoToPickup();
                })
                .addTemporalMarker(1.5, () -> {
                    drive.operateClaw(0);
                })
                .addTemporalMarker(2, () -> {
                    drive.servosGoToUpright();
                })
                .waitSeconds(2)
                .splineToConstantHeading(new Vector2d(-26.5, -4.5), Math.toRadians(-10))
                .strafeLeft(5)
                .addDisplacementMarker(() -> {
                    drive.operateClaw(1);
                })
                .strafeRight(5)
                .splineToConstantHeading(new Vector2d(-42.67, 6), Math.toRadians(-10))
                .build();
        waitForStart();
        if (isStopRequested()) return;
        drive.followTrajectorySequence(startAlign);
        drive.followTrajectorySequence(cycle);
        drive.followTrajectorySequence(cycle);
        drive.followTrajectorySequence(cycle);
        drive.followTrajectorySequence(cycle);
        drive.followTrajectorySequence(cycle);
    }
}
