package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvWebcam;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.opencv.core.Mat;
import java.util.ArrayList;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

/*
Things to do:
Finish robot class
Add OpenCV implementation for phase 1
Fix/Redo phase 2 (figure out HSV in python)
 */

@Autonomous(name="Robot Class - Ignore", group="Linear OpMode")
public class RightsideAuto extends LinearOpMode {
    OpenCvWebcam webcam;

    @Override
    public void runOpMode() {
        waitForStart();
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        DetectionPipeline usedPipeline = new DetectionPipeline();
        webcam.setPipeline(usedPipeline);
        webcam.setMillisecondsPermissionTimeout(7000); // Timeout for obtaining permission is configurable. Set before opening.
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


    class Robot {
        OpenCvWebcam webcam;
        private DcMotor motor;
        private Servo servo;
        Robot() {
            motor = hardwareMap.dcMotor.get("motor-name");
            servo = hardwareMap.servo.get("servo=name");
        }
    }


    class DetectionPipeline extends OpenCvPipeline {
        boolean viewportPaused;
        private int phase;
        private String viewedColor;
        public DetectionPipeline() {

            phase = 1;

        }

        public void setPhase(int phase) {
            this.phase = phase;
        }

        @Override
        public Mat processFrame(Mat input) {
            if (phase == 2) {
                input = processPhase1(input);
            }
            else if (phase == 1) {
                input = processPhase2(input);
            }
            return input;
        }

        private Mat processPhase2(Mat input) {

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
}