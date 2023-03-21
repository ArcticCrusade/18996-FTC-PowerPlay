package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

public class RRTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        //yoink all of the motor declarations and their methods
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        //set starting position
        drive.setPoseEstimate(new Pose2d(0, 0, 0));

        //declare all trajectories
        Trajectory t1 = drive.trajectoryBuilder(new Pose2d(0, 0, 0))
                .strafeRight(10)
                .build();
        Trajectory t2 = drive.trajectoryBuilder(t1.end())
                .splineTo(new Vector2d(20, 20), Math.toRadians(90))
                .build();
        Trajectory t3 = drive.trajectoryBuilder(t2.end())
                .lineToLinearHeading(new Pose2d(0, 0, 0))
                .build();

        waitForStart();
        if (isStopRequested()) return;
        drive.followTrajectory(t1);
        drive.followTrajectory(t2);
        drive.followTrajectory(t3);
    }
}
