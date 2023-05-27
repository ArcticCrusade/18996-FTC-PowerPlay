
package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.common.Hardware.OldArm;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

@Config
@Autonomous(name="AAAARRPractical", group="Linear Opmode")
public class RRPractical extends LinearOpMode {

    @Override
    public void runOpMode() {
        //yoink all of the motor declarations and their methods
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        //set starting position
        Pose2d startPose = new Pose2d(-36, 72, Math.toRadians(270));
        drive.setPoseEstimate(startPose);
        TrajectorySequence startAlign = drive.trajectorySequenceBuilder(startPose)
                .splineTo(new Vector2d(-30, 6), Math.toRadians(270))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(-42, 6, Math.toRadians(-8)), Math.toRadians(-8))
                .build();
        TrajectorySequence cycle = drive.trajectorySequenceBuilder(startAlign.end())
                .setReversed(false)
                .splineToConstantHeading(new Vector2d(-36.5, -4), Math.toRadians(-10))
                .splineToConstantHeading(new Vector2d(-26, 0.5), Math.toRadians(-10))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(-36.5, -4), Math.toRadians(-10))
                .splineToConstantHeading(new Vector2d(-42, 6), Math.toRadians(-8))
                .build();
        waitForStart();
        if (isStopRequested()) return;
        drive.followTrajectorySequence(startAlign);
        for (int i = 1; i <= 5; i++) {
            drive.followTrajectorySequence(cycle);
        }
    }
}