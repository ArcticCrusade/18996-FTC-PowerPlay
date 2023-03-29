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
    public static double x1 = 28;
    public static double y1 = -5;
    public static double angle = 104;
    public static double x2 = 47;
    public static double y2 = -12;
    @Override
    public void runOpMode() {
        //yoink all of the motor declarations and their methods
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        //set starting position
        Pose2d startPose = new Pose2d(34.4, -56.83, 0);
        drive.setPoseEstimate(startPose);

        TrajectorySequence t = drive.trajectorySequenceBuilder(startPose)
                .addDisplacementMarker(() -> {
                    drive.servosGoToUpright();
                })
                .splineTo(new Vector2d(x1, y1), 0)
                .addDisplacementMarker(() -> {
                    drive.operateClaw(1);
                })
                .splineTo(new Vector2d(x2, y2), angle)
                .addDisplacementMarker(() -> {
                    drive.servosGoToPickup();
                })
                .build();
        waitForStart();
        if (isStopRequested()) return;
        drive.followTrajectorySequence(t);
    }
}
