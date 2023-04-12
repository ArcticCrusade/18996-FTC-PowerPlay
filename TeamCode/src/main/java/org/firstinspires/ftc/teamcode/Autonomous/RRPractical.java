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
    public static double x = -33;
    public static double y = 4;
    public static double angle = 170;
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
                .splineTo(new Vector2d(-37, 10), Math.toRadians(270))
                .splineToConstantHeading(new Vector2d(-43.5, 8), 0)
                .build();
        Trajectory cycleFwd = drive.trajectoryBuilder(toPosition.end())
                .splineTo(new Vector2d(x, y), Math.toRadians(angle))
                .build();
        Trajectory cycleBk = drive.trajectoryBuilder(cycleFwd.end(), true)
                .addDisplacementMarker(() -> {
                    drive.operateClaw(1);
                })
                .splineTo(new Vector2d(-43.5, 8), Math.toRadians(170))
                .build();
        waitForStart();
        if (isStopRequested()) return;
        drive.followTrajectorySequence(firstToHigh);
        drive.operateClaw(1);
        sleep(300);
        drive.followTrajectory(toPosition);
        drive.turn(Math.toRadians(170));
        for (int i = 1; i <= 5; i++) {
            drive.operateClaw(0);
            drive.servosGoToPickup();
            sleep(1300);
            drive.servosGoToUpright();
            sleep(300);
            drive.followTrajectory(cycleFwd);
            drive.followTrajectory(cycleBk);
            sleep(500);
        }
    }
}
