package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;
import java.util.ArrayList;
@Autonomous(name="Test", group="Vision")
public class Test extends OpMode {
    static final int STREAM_WIDTH = 1280; // modify for your camera
    static final int STREAM_HEIGHT = 960; // modify for your camera
    OpenCvWebcam webcam;
    SamplePipeline pipeline;
    @Override
    public void init() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        WebcamName webcamName = null;
        webcamName = hardwareMap.get(WebcamName.class, "Webcam 1"); // put your camera's name here
        webcam = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);
        pipeline = new SamplePipeline();
        webcam.setPipeline(pipeline);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                webcam.startStreaming(STREAM_WIDTH, STREAM_HEIGHT, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Camera Failed","");
                telemetry.update();
            }
        });

    }

    @Override
    public void loop() {
        telemetry.addData("red:",pipeline.getRAnalysis());
        telemetry.addData("blue:",pipeline.getBAnalysis());
        telemetry.addData("green:",pipeline.getGAnalysis());
        telemetry.update();
    }


}

class SamplePipeline extends OpenCvPipeline {

    Mat R = new Mat();
    Mat G = new Mat();
    Mat B = new Mat();

    int avgR, avgB, avgG;


    /*
     * This function takes the RGB frame, converts to YCrCb,
     * and extracts the Y channel to the 'Y' variable
     */
    void separate(Mat input) {
        ArrayList<Mat> rgbChannels = new ArrayList<Mat>(3);
        Core.split(input, rgbChannels);
        R = rgbChannels.get(1);
        G = rgbChannels.get(2);
        B = rgbChannels.get(0);
    }

    @Override
    public void init(Mat firstFrame) {
        separate(firstFrame);
    }

    @Override
    public Mat processFrame(Mat input) {
        separate(input);
        avgR = (int) Core.mean(R).val[0];
        avgG = (int) Core.mean(G).val[0];
        avgB = (int) Core.mean(B).val[0];
        R.release(); // don't leak memory!
        G.release(); // don't leak memory!
        B.release();
        return input;
    }

    public int getRAnalysis() {
        return avgR;
    }
    public int getGAnalysis() {
        return avgG;
    }
    public int getBAnalysis() {
        return avgB;
    }
}
