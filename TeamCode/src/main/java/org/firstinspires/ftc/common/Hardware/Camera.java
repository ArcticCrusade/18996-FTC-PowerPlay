package org.firstinspires.ftc.common.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.common.Interfaces.Subsystem;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;

import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.opencv.core.Size;
import org.opencv.core.Core;
import org.openftc.apriltag.AprilTagDetection;
import org.firstinspires.ftc.common.Software.AprilTagDetectionPipeline;

public class Camera implements Subsystem {
    OpenCvCamera webcam;
    SignalDetection SignalPipeline;
    RedConeDetection RedConePipeline;
    AprilTagDetectionPipeline AprilTagPipeline;
    double tagsize = .2; // in meters

    @Override
    public void initialize(LinearOpMode opMode) {
        int cameraMonitorViewId = opMode.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", opMode.hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(opMode.hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        AprilTagPipeline = new AprilTagDetectionPipeline(tagsize, 1430, 1430, 480, 620); // these values might be wrong I got them off some random website

        webcam.setPipeline(AprilTagPipeline);
        // webcam.setMillisecondsPermissionTimeout(7000); // Timeout for obtaining permission is configurable. Set before opening.
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {

            }
        });
    }

    public AprilTagDetectionPipeline getAprilTagPipeline() {
        return AprilTagPipeline;
    }

    public void switchPipeline(String coneColor) {
        switch (coneColor) {
            case "Red": webcam.setPipeline(RedConePipeline);
        }
    }

    public OpenCvCamera getWebcam() {
        return webcam;
    }
    public String getColor() {
        return SignalPipeline.getViewedColor();
    }

    class SignalDetection extends OpenCvPipeline {
        boolean viewportPaused;
        private int phase;
        private String viewedColor;


        @Override
        public Mat processFrame(Mat input) {
            double[] colorsSeen = input.get(120, 160);
            int red = (int) colorsSeen[0];
            int green = (int) colorsSeen[1];
            int blue = (int) colorsSeen[2];
            if (red > blue && red > green) {
                viewedColor = "RED";
            } else if (blue > red && blue > green) {
                viewedColor = "BLUE";
            } else if (green > red && green > blue) {
                viewedColor = "GREEN";
            }
            else {
                viewedColor = "if you see this idk what happened but good luck!!!!";
            }
            Imgproc.rectangle(
                    input,
                    new Point(
                            7 * input.cols() / 16,
                            7 * input.rows() / 16),
                    new Point(
                            input.cols() * (9f / 16f),
                            input.rows() * (9f / 16f)),
                    new Scalar(red, green, blue), 4);
            return input;
        }

        public String getViewedColor() {
            return viewedColor;
        }
        @Override
        public void onViewportTapped() {

            viewportPaused = !viewportPaused;

            if(viewportPaused) {
                webcam.pauseViewport();
            }
            else {
                webcam.resumeViewport();
            }
        }
    }

    class RedConeDetection extends OpenCvPipeline {
        Mat hsvImage;
        Mat inRange;
        Mat mask;
        int center_x;
        Scalar lowerRange = new Scalar(150, 150, 59);
        Scalar upperRange = new Scalar(180, 180, 255);

        @Override
        public Mat processFrame(Mat mat) {

            // Scale Down Image
            double scalePercent = 20;
            int width = (int) Math.round(mat.size().width * scalePercent / 100);
            int height = (int) Math.round(mat.size().height * scalePercent / 100);
            Size dimensions = new Size(width, height);
            Imgproc.resize(mat, mat, dimensions);

            // Convert to HSV and filter
            hsvImage = new Mat();
            inRange = new Mat();
            Imgproc.cvtColor(mat, hsvImage, Imgproc.COLOR_RGB2HSV);
            Core.inRange(hsvImage, lowerRange, upperRange, inRange);
            List<MatOfPoint> contours = new ArrayList<>();
            Mat hierarchy = new Mat();
            Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

            MatOfPoint largestContour = findLargestContour(contours);

            // todo: add function that takes mean or median of these for higher accuracy
            center_x = findBoundingRectCenter(largestContour);
            return mat;
        }

        public MatOfPoint findLargestContour(List<MatOfPoint> contours) {
            if (contours.size() == 0) {
                return null;
            }

            double largestArea = 0;
            MatOfPoint largestContour = null;

            for (MatOfPoint contour : contours) {
                double area = Imgproc.contourArea(contour);
                if (area > largestArea) {
                    largestArea = area;
                    largestContour = contour;
                }
            }

            return largestContour;
        }

        public int findBoundingRectCenter(MatOfPoint contour) {
            Rect boundingRect = Imgproc.boundingRect(contour);
            int cX = boundingRect.x + boundingRect.width / 2;
            return cX;
        }
    }
}
