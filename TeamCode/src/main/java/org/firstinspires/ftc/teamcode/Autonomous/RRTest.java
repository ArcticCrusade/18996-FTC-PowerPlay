package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Autonomous(name="RRTest", group="Linear Opmode")
public class RRTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        //yoink all of the motor declarations and their methods
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        //set starting position
        drive.setPoseEstimate(new Pose2d(0, 0, 0));

        //declare all trajectories
        Trajectory t1 = drive.trajectoryBuilder(new Pose2d(0, 0, 0))
                .splineTo(new Vector2d(0, -30), 0)
                .build();
        Trajectory t2 = drive.trajectoryBuilder(t1.end())
                .splineToSplineHeading(new Pose2d(40, 20, Math.toRadians(45)), 0)
                .build();
        Trajectory t3 = drive.trajectoryBuilder(t2.end())
                .splineToLinearHeading(new Pose2d(-10, -10, Math.toRadians(90)), 0)
                .build();
        Trajectory t4 = drive.trajectoryBuilder(t3.end())
                .splineTo(new Vector2d(-20, 30), 0)
                .build();
        Trajectory t5 = drive.trajectoryBuilder(t4.end())
                .splineToLinearHeading(new Pose2d(0, 0, Math.toRadians(0)), 0)
                .build();

        waitForStart();
        if (isStopRequested()) return;
        drive.followTrajectory(t1);
        drive.followTrajectory(t2);
        drive.followTrajectory(t3);
        drive.followTrajectory(t4);
        drive.followTrajectory(t5);
    }
}
