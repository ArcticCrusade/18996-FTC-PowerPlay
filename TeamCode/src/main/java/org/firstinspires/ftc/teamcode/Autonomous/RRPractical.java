/*
- zoom to drop on high
- turn to stack and cycle all cones to that high
- park facing in starting direction
- in exactly 30 seconds
*/
package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import java.util.List;

@Config
@Autonomous(name="AAAARRPractical", group="Linear Opmode")
public class RRPractical extends LinearOpMode {
    private static final String[] LABELS = {
            "Donut",
            "Present",
            "Zebra"
    };
    private static final String VUFORIA_KEY ="AcNgP8//////AAABmZ55IDBzuUkekc8paw1AZMqOvbqVTPE2eC+CPRCYgcEht7UZ0bvrfLZSahlrwgTJfjVS8oq2kwDC/NMCS60BINpcCptCceUO07Zjek6g5yRMWoAderbKZLQuqatA1SFHN8wBwTRnTt5+FIdlFUbzGzjPytDFsw16ic+cEd/2zmlTod/XtFmRKOtYc5+hUyrdNrYwrBXw1rrqyOASodP4HPLgUCgrTJR6OfSaYO7oI5dsILNRI0VQ+pM2i7WyGanuXo3PLKf0cbOIP1+Rwnu480JWJgrVaU4dpB4IG+jzz0kdNXWhkx1fvv1W1kTlfPs+kh5Y5Wg86x+nJH4WzCkcpSlYu4ViFAYA0h8rPtPNB2D1";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;
    private void initVuforia() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("tfodMonitorViewId", "id",  hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.625f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 300;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromFile("Cone4.tflite", LABELS);
    }
    int donut = 0;
    int present = 0;
    int zebra = 0;
    @Override
    public void runOpMode() {
        //yoink all of the motor declarations and their methods
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        //set starting position
        Pose2d startPose = new Pose2d(-36, 72, Math.toRadians(270));
        drive.setPoseEstimate(startPose);
        TrajectorySequence startAlign = drive.trajectorySequenceBuilder(startPose)
                .splineTo(new Vector2d(-30, 6), Math.toRadians(270))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(-42, 6, Math.toRadians(-8)), Math.toRadians(-8))
                .build();
        TrajectorySequence cycle = drive.trajectorySequenceBuilder(drive.getPoseEstimate())
                .setReversed(false)
                .splineToConstantHeading(new Vector2d(-36.5, -4), Math.toRadians(-10))
                .splineToConstantHeading(new Vector2d(-26, 0.5), Math.toRadians(-10))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(-36.5, -4), Math.toRadians(-10))
                .splineToConstantHeading(new Vector2d(-42, 6), Math.toRadians(-8))
                .build();
        TrajectorySequence park1 = drive.trajectorySequenceBuilder(drive.getPoseEstimate())

                .build();
        TrajectorySequence park2 = drive.trajectorySequenceBuilder(drive.getPoseEstimate())

                .build();
        TrajectorySequence park3 = drive.trajectorySequenceBuilder(drive.getPoseEstimate())

                .build();

        initVuforia();
        initTfod();
        if (tfod != null) {
            tfod.activate();
            tfod.setZoom(1.5, 16.0/9.0);
        }

        waitForStart();
        if (isStopRequested()) return;
        for (int i = 0; i < 20; i++) {
            if (tfod != null) {
                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    for (Recognition recognition : updatedRecognitions) {
                        if (recognition.getLabel().equals("Donut")) {
                            donut++;
                        } else if (recognition.getLabel().equals("Present")) {
                            present++;
                        } else if (recognition.getLabel().equals("Zebra")) {
                            zebra++;
                        }
                    }
                }
            }
        }
        drive.followTrajectorySequence(startAlign);
        for (int i = 1; i <= 5; i++) {
            drive.followTrajectorySequence(cycle);
        }
        if (donut > present && donut > zebra) { //state 1
            drive.followTrajectorySequence(park1);
        } else if (present > zebra && present > donut) { //state 2
            drive.followTrajectorySequence(park2);
        } else if (zebra > present && zebra > donut) { //state 3
            drive.followTrajectorySequence(park3);
        }
    }
}