package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.sql.Array;
import java.util.ArrayList;

import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

@Autonomous(name="OpenCV Testing", group="Vision")
public class OpenCVTesting extends LinearOpMode {

    OpenCvWebcam webcam;


    public void runOpMode() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        DetectionPipeline usedPipeline = new DetectionPipeline();
        webcam.setPipeline(usedPipeline);
        webcam.setMillisecondsPermissionTimeout(5000); // Timeout for obtaining permission is configurable. Set before opening.
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {

            }
        });
        waitForStart();
        usedPipeline.setPhase(2);
        while (opModeIsActive()) {
            telemetry.addLine("COLOR SEEN: " + usedPipeline.getViewedColor());
            telemetry.update();
        }
    }
    class DetectionPipeline extends OpenCvPipeline {
        boolean viewportPaused;
        int phase;
        private String viewedColor;

        Mat filteredImage;
        Mat grayImage;
        MatOfInt fromTo;

        public DetectionPipeline() {

            phase = 1;
            filteredImage = new Mat();
            grayImage = new Mat();
        }

        public void setPhase(int phase) {
            this.phase = phase;
        }

        @Override
        public Mat processFrame(Mat input) {
            switch (phase) {
                case 1: input = processPhase1(input);

                case 2: input = processPhase2(input);
            }
            return input;
        }

        private Mat processPhase2(Mat input) {
            ArrayList<Mat> channels = new ArrayList<>();
            Core.split(input, channels);
            channels.get(0).setTo(new Scalar(0));
            Core.merge(channels, input);
            Imgproc.cvtColor(input, grayImage, Imgproc.COLOR_RGB2GRAY); // look into HSV color filtering, current method could be inaccurate
            Imgproc.threshold(grayImage, filteredImage, 2, 29, Imgproc.THRESH_BINARY);
            ArrayList<MatOfPoint> contours = new ArrayList<>();
            Imgproc.findContours(filteredImage, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
            double maxArea = 0;


            int maxContourIndex = -1;
            for (int i = 0; i < contours.size(); i++) {
                double area = Imgproc.contourArea(contours.get(i));
                if (area > maxArea) {
                    maxArea = area;
                    maxContourIndex = i;
                }
            }
            Imgproc.drawContours(input, contours, maxContourIndex, new Scalar(255, 255, 0), 7);


            return input;
        }

        private Mat processPhase1(Mat input) {
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

            if(viewportPaused)
            {
                webcam.pauseViewport();
            }
            else
            {
                webcam.resumeViewport();
            }
        }
    }
}



