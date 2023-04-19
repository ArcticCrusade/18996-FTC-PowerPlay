package org.firstinspires.ftc.teamcode.TeleOp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import java.util.PriorityQueue;
import java.util.Queue;

@TeleOp(name="BlockToBlock", group="Linear Opmode")
public class BlockToBlock extends LinearOpMode {
    @Override
    public void runOpMode() {
        Pose2d pose;
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(new Pose2d());
        //Queue<Trajectory> trajQueue = new PriorityQueue<Trajectory>();
        waitForStart();
        while (!isStopRequested()) {
            //trajectory stops all other commands so no ridiculous backlogging
            pose = drive.getPoseEstimate();
            if (gamepad1.dpad_up) {
                drive.followTrajectory(drive.trajectoryBuilder(pose)
                        .splineTo(new Vector2d(pose.getX() + 24, pose.getY()), 0)
                        .build());
            }
            if (gamepad1.dpad_down) {
                drive.followTrajectory(drive.trajectoryBuilder(drive.getPoseEstimate())
                        .splineTo(new Vector2d(pose.getX() - 24, pose.getY()), 0)
                        .build());
            }
            if (gamepad1.dpad_left) {
                drive.followTrajectory(drive.trajectoryBuilder(drive.getPoseEstimate())
                        .splineToConstantHeading(new Vector2d(pose.getX(), pose.getY() + 24), 0)
                        .build());
            }
            if (gamepad1.dpad_right) {
                drive.followTrajectory(drive.trajectoryBuilder(drive.getPoseEstimate())
                        .splineToConstantHeading(new Vector2d(pose.getX(),pose.getY() - 24), 0)
                        .build());
            }
        }
    }
}
