package org.firstinspires.ftc.common.Commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.common.Hardware.Camera;
import org.firstinspires.ftc.common.Hardware.RobotHardware;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.openftc.apriltag.AprilTagDetection;

import java.util.ArrayList;

public class AprilTagIDCommand extends CommandBase {
    private Camera camera;
    private static final double FEET_PER_METER = 3.28084;
    private AprilTagDetection tagOfInterest = null;
    ArrayList<AprilTagDetection> currentDetections;
    private double[] stats;
    private final double MARGIN_OF_ERROR = 1.0;

    public AprilTagIDCommand(RobotHardware robot) {
        this.camera = robot.camera;
        addRequirements(camera);
    }

    @Override
    public void initialize() {
        currentDetections = camera.getAprilTagPipeline().getLatestDetections();
        if (currentDetections.size() > 0) {
            for (AprilTagDetection tag : currentDetections) {
                if (tag.id == 0 || tag.id == 1 || tag.id == 2) {
                    tagOfInterest = tag;
                }
            }
        }
    }
    @Override
    public void execute() {
        Orientation rot = Orientation.getOrientation(tagOfInterest.pose.R, AxesReference.INTRINSIC, AxesOrder.YXZ, AngleUnit.DEGREES);
        stats = new double[]{tagOfInterest.pose.x * FEET_PER_METER,
                tagOfInterest.pose.y * FEET_PER_METER,
                tagOfInterest.pose.z * FEET_PER_METER,
                rot.firstAngle,
                rot.secondAngle,
                rot.thirdAngle};
    }
    @Override
    public boolean isFinished() {
        return true;
    }
}
