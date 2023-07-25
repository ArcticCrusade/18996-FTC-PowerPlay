package org.firstinspires.ftc.teamcode.Autonomous;
/*
 * Copyright (c) 2021 OpenFTC Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.openftc.apriltag.AprilTagDetection;
import org.firstinspires.ftc.common.Hardware.AprilTagDetectionPipeline;
import org.firstinspires.ftc.common.Hardware.Camera;
import java.util.ArrayList;
import java.util.Arrays;

public class AprilTagAutonomous {
    private Camera camera;
    private static final double FEET_PER_METER = 3.28084;
    private int tagIDs[];
    private AprilTagDetection tagOfInterest = null;

    public AprilTagAutonomous(Camera camera, int[] tagIDs) {
        this.camera = camera;
        this.tagIDs = tagIDs;
    }
    public Camera getCamera() {
        return camera;
    }
    public int getDetectedTagID() {
        ArrayList<AprilTagDetection> currentDetections = camera.getAprilTagPipeline().getLatestDetections();
        if (currentDetections.size() > 0) {
            for (AprilTagDetection tag : currentDetections) {
                if (Arrays.asList(tagIDs).contains(tag.id)) {
                    tagOfInterest = tag;
                    return tagOfInterest.id;
                }
            }
        }
        return -1;
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
    }
}