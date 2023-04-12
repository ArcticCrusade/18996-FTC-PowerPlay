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
    public static double x = -28;
    public static double y = -1;
    public static double angle = -10;
    @Override
    public void runOpMode() {
        //yoink all of the motor declarations and their methods
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        //set starting position
        Pose2d startPose = new Pose2d(-36, 72, Math.toRadians(270));
        drive.setPoseEstimate(startPose);

        TrajectorySequence firstToHigh = drive.trajectorySequenceBuilder(startPose)
                .addDisplacementMarker(() -> {
                    drive.servosGoToUpright();
                    drive.operateClaw(0);
                })
                .splineTo(new Vector2d(-32, 7.25), Math.toRadians(270))
                .build();
        Trajectory toPosition = drive.trajectoryBuilder(firstToHigh.end(), true)
                .splineToLinearHeading(new Pose2d(-43.5, 6, Math.toRadians(-10)), Math.toRadians(-10))
                .build();
        Trajectory cycleFwd = drive.trajectoryBuilder(toPosition.end())
                .splineTo(new Vector2d(x, y), Math.toRadians(angle))
                .build();
        Trajectory cycleBk = drive.trajectoryBuilder(cycleFwd.end(), true)
                .splineToLinearHeading(new Pose2d(-43.5, 8, Math.toRadians(-10)), Math.toRadians(-10))
                .build();
        waitForStart();
        if (isStopRequested()) return;
        drive.followTrajectorySequence(firstToHigh);
        drive.operateClaw(1);
        sleep(300);
        drive.followTrajectory(toPosition);
        for (int i = 1; i <= 5; i++) {
            drive.operateClaw(0);
            drive.servosGoToPickup();
            sleep(1400);
            drive.servosGoToUpright();
            sleep(450);
            drive.followTrajectory(cycleFwd);
            drive.operateClaw(1);
            drive.followTrajectory(cycleBk);
            sleep(500);
        }
    }
}
