/**
 * DEPRECIATED
 */
package org.firstinspires.ftc.common.Software;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.openftc.apriltag.AprilTagDetection;
import org.firstinspires.ftc.common.Hardware.Camera;

import java.util.ArrayList;

public class AprilTagAutonomous {
    private Camera camera;
    private static final double FEET_PER_METER = 3.28084;
    private AprilTagDetection tagOfInterest = null;
    ArrayList<AprilTagDetection> currentDetections;

    private int numFramesWithoutDetection = 0;
    private final float DECIMATION_HIGH;
    private final float DECIMATION_LOW;
    private final float THRESHOLD_HIGH_DECIMATION_RANGE_METERS;
    private final int THRESHOLD_NUM_FRAMES_NO_DETECTION_BEFORE_LOW_DECIMATION;

    public AprilTagAutonomous(Camera camera) {
        DECIMATION_HIGH = 3;
        DECIMATION_LOW = 2;
        THRESHOLD_HIGH_DECIMATION_RANGE_METERS = 1.0f;
        THRESHOLD_NUM_FRAMES_NO_DETECTION_BEFORE_LOW_DECIMATION = 4;
        this.camera = camera;
    }
    public AprilTagAutonomous(Camera camera,
                              float DECIMATION_HIGH, float DECIMATION_LOW,
                              float THRESHOLD_HIGH_DECIMATION_RANGE_METERS,
                              int THRESHOLD_NUM_FRAMES_NO_DETECTION_BEFORE_LOW_DECIMATION) {
        this.DECIMATION_HIGH = DECIMATION_HIGH;
        this.DECIMATION_LOW = DECIMATION_LOW;
        this.THRESHOLD_HIGH_DECIMATION_RANGE_METERS = THRESHOLD_HIGH_DECIMATION_RANGE_METERS;
        this.THRESHOLD_NUM_FRAMES_NO_DETECTION_BEFORE_LOW_DECIMATION = THRESHOLD_NUM_FRAMES_NO_DETECTION_BEFORE_LOW_DECIMATION;
        this.camera = camera;
    }
    public Camera getCamera() {
        return camera;
    }
    public String getTagOfInterest() {
        switch (tagOfInterest.id) {
            case 0: return "LEFT";
            case 1: return "MIDDLE";
            case 2: return "RIGHT";
        }
        return "";
    }
    public void findTagIDSimple() {
        currentDetections = camera.getAprilTagPipeline().getLatestDetections();
        if (currentDetections.size() > 0) {
            for (AprilTagDetection tag : currentDetections) {
                if (tag.id == 0 || tag.id == 1 || tag.id == 2) {
                    tagOfInterest = tag;
                }
            }
        }
    }
    public void findTagIDDecimation() {
        currentDetections = camera.getAprilTagPipeline().getDetectionsUpdate();
        if (currentDetections.size() == 0) {
            numFramesWithoutDetection++;
            if (numFramesWithoutDetection >= THRESHOLD_NUM_FRAMES_NO_DETECTION_BEFORE_LOW_DECIMATION) {
                camera.getAprilTagPipeline().setDecimation(DECIMATION_LOW);
            }
        } else {
            numFramesWithoutDetection = 0;
            if (currentDetections.get(0).pose.z < THRESHOLD_HIGH_DECIMATION_RANGE_METERS) {
                camera.getAprilTagPipeline().setDecimation(DECIMATION_HIGH);
            }
        }
        findTagIDSimple();
    }
    public double[] returnTagLocalization() {
        Orientation rot = Orientation.getOrientation(tagOfInterest.pose.R, AxesReference.INTRINSIC, AxesOrder.YXZ, AngleUnit.DEGREES);
        if (tagOfInterest == null) {
            return new double[]{};
        }
        return new double[]{tagOfInterest.pose.x * FEET_PER_METER,
                            tagOfInterest.pose.y * FEET_PER_METER,
                            tagOfInterest.pose.z * FEET_PER_METER,
                            rot.firstAngle,
                            rot.secondAngle,
                            rot.thirdAngle};
        //feet, degrees
        //translation x, y, z, rotation yaw, pitch, roll
        //yaw = side to side (z is constant), pitch = up or down, roll = barrel roll
    }
}