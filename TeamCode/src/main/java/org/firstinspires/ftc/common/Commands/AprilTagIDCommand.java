package org.firstinspires.ftc.common.Commands;

import com.acmerobotics.roadrunner.drive.Drive;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.common.Hardware.Camera;
import org.firstinspires.ftc.common.Hardware.DriveSubsystem;
import org.firstinspires.ftc.common.Hardware.RobotHardware;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.openftc.apriltag.AprilTagDetection;

import java.util.ArrayList;

public class AprilTagIDCommand extends CommandBase {
    private Camera camera;
    private DriveSubsystem drive;
    private static final double FEET_PER_METER = 3.28084;
    private AprilTagDetection tagOfInterest = null;
    ArrayList<AprilTagDetection> currentDetections;
    private double[] stats;
    private final double MARGIN_OF_ERROR = 1.0;

    public AprilTagIDCommand(RobotHardware robot, DriveSubsystem drive) {
        this.camera = robot.camera;
        this.drive = drive;
        addRequirements(this.camera, this.drive);
    }

    @Override
    public void initialize() {}
    @Override
    public void execute() {
        currentDetections = camera.getAprilTagPipeline().getLatestDetections();
        if (currentDetections.size() > 0) {
            for (AprilTagDetection tag : currentDetections) {
                if (tag.id == 0 || tag.id == 1 || tag.id == 2) {
                    tagOfInterest = tag;
                }
            }
        }
        Orientation rot = Orientation.getOrientation(tagOfInterest.pose.R, AxesReference.INTRINSIC, AxesOrder.YXZ, AngleUnit.DEGREES);
        stats = new double[]{tagOfInterest.pose.x * FEET_PER_METER,
                tagOfInterest.pose.y * FEET_PER_METER,
                tagOfInterest.pose.z * FEET_PER_METER,
                rot.firstAngle,
                rot.secondAngle,
                rot.thirdAngle};
        if (tagOfInterest.id == 0) {
            drive.manualPower(-0.3 * stats[0], -0.3 * Math.abs(stats[1] - 1), 0.01 * stats[3]);
        } else if (tagOfInterest.id == 1) {
            drive.manualPower(-0.3 * stats[0], -0.3 * Math.abs(stats[1] - 2), 0.01 * stats[3]);
        } else if (tagOfInterest.id == 2) {
            drive.manualPower(-0.3 * stats[0], -0.3 * Math.abs(stats[1] - 3), 0.01 * stats[3]);
        }
    }
    @Override
    public boolean isFinished() {
        if (Math.abs(stats[0]) < MARGIN_OF_ERROR && Math.abs(stats[3]) < MARGIN_OF_ERROR) {
            return true;
        }
        return false;
    }
}
