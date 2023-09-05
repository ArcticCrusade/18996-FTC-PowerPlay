package org.firstinspires.ftc.common.Hardware;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;;

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
import org.firstinspires.ftc.common.Software.AprilTagDetectionPipeline;

public class Camera extends SubsystemBase {
    OpenCvCamera webcam;
    SignalDetection SignalPipeline;
    ConeDetection ConePipeline;
    AprilTagDetectionPipeline AprilTagPipeline;
    Scalar lowerBound;
    Scalar upperBound;
    double tagsize = .2; // in meters

    public Camera(HardwareMap hardwareMap) {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        ConePipeline = new ConeDetection(lowerBound, upperBound);
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


    public void switchPipeline(String color) {
        switch (color) {
            case "red":
                lowerBound = new Scalar(150, 170, 130); // these might be wrong
                upperBound = new Scalar(180, 255, 255);
                break;
            case "blue":
                lowerBound = null; // TODO: figure out these values (also red might be representative of blue?)
                upperBound = null;
                break;
        }
        ConePipeline = new ConeDetection(lowerBound, upperBound);
        webcam.setPipeline(ConePipeline);
    }

    public AprilTagDetectionPipeline getAprilTagPipeline() {
        return AprilTagPipeline;
    }

    public ConeDetection getConePipeline() {
        return ConePipeline;
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
            } else {
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

            if (viewportPaused) {
                webcam.pauseViewport();
            } else {
                webcam.resumeViewport();
            }
        }
    }

    public class ConeDetection extends OpenCvPipeline {
        Mat hsvImage;
        Mat inRange;
        Mat mask;
        int centerX;
        int width;
        Scalar lowerRange;
        Scalar upperRange;
        List<MatOfPoint> contours;
        Mat hierarchy;
        MatOfPoint largestContour;

        @Override
        public Mat processFrame(Mat mat) {
            updateVals(mat);
            // Scale Down Image
            return mat;
        }

        public int getCenter() {
            return centerX;
        }
        public int getWidth() { return width; }

        private void updateVals(Mat mat) {
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
            contours = new ArrayList<>();
            hierarchy = new Mat();
            Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

            if (contours != null) {
                largestContour = findLargestContour(contours);
                Rect boundingRect = Imgproc.boundingRect(largestContour);
                centerX = boundingRect.x + boundingRect.width / 2;
                width = boundingRect.width;
            }
        }

        private MatOfPoint findLargestContour(List<MatOfPoint> contours) {
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


        ConeDetection(Scalar lowerBound, Scalar upperBound) {
            lowerRange = lowerBound;
            upperRange = upperBound;
        }
    }
}
