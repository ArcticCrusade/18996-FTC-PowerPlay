package org.firstinspires.ftc.teamcode.TeleOp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.common.Hardware.OldArm;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

@TeleOp(name="BlockToBlock", group="Linear Opmode")
public class BlockToBlock extends LinearOpMode {
    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        OldArm arm = new OldArm(hardwareMap);
        drive.setPoseEstimate(new Pose2d());
        Queue<Trajectory> trajQueue = new LinkedList<Trajectory>();
        ElapsedTime time = new ElapsedTime();
        ElapsedTime debounceTime = new ElapsedTime();

        Trajectory f = drive.trajectoryBuilder(new Pose2d())
                .forward(24)
                .build();
        Trajectory b = drive.trajectoryBuilder(new Pose2d())
                .back(24)
                .build();
        Trajectory l = drive.trajectoryBuilder(new Pose2d())
                .strafeLeft(24)
                .build();
        Trajectory r = drive.trajectoryBuilder(new Pose2d())
                .strafeRight(24)
                .build();
        telemetry.addData("State", "Waiting...");
        telemetry.update();
        arm.servosGoToPickup(0.53, 0.65, 0.27);
        waitForStart();
        time.reset();
        debounceTime.reset();
        telemetry.addData("State", "Accepting Inputs...");
        telemetry.update();
        while (!isStopRequested()) {
            if (debounceTime.seconds() > 0.3) {
                if (gamepad1.dpad_up) {
                    trajQueue.add(f);
                    debounceTime.reset();
                }
                if (gamepad1.dpad_down) {
                    trajQueue.add(b);
                    debounceTime.reset();
                }
                if (gamepad1.dpad_left) {
                    trajQueue.add(l);
                    debounceTime.reset();
                }
                if (gamepad1.dpad_right) {
                    trajQueue.add(r);
                    debounceTime.reset();
                }
            }
            if (!trajQueue.isEmpty()) {
                telemetry.addData("State", "Running!");
                telemetry.update();
                drive.setPoseEstimate(new Pose2d());
                drive.followTrajectory(trajQueue.poll());
            } else {
                telemetry.addData("State", "Accepting Inputs...");
                telemetry.update();
            }
        }
    }
}
