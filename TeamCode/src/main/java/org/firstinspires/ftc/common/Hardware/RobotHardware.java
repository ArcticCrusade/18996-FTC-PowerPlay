package org.firstinspires.ftc.common.Hardware;

import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.common.Software.AprilTagDetectionPipeline;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.util.Arrays;
import java.util.List;

public class RobotHardware {
    public enum OpModes {
        AUTO,
        TELEOP
    }
    public Motor leftFront, rightFront, leftRear, rightRear;
    public DcMotor leftLift, rightLift;

    public Servo fourBar1, fourBar2;
    public Servo grabber;

    public OpenCvCamera webcam;
    public AprilTagDetectionPipeline AprilTagPipeline;

    private static RobotHardware instance = new RobotHardware();
    private HardwareMap hardwareMap;

    public static RobotHardware getInstance() {
        return instance;
    }

    public void init(HardwareMap hardwareMap, OpModes mode) {
        this.hardwareMap = hardwareMap;

        leftFront = new Motor(hardwareMap, "leftFront");
        leftFront.setRunMode(Motor.RunMode.RawPower);
        rightFront = new Motor(hardwareMap,"rightFront");
        rightFront.setRunMode(Motor.RunMode.RawPower);
        leftRear = new Motor(hardwareMap,"leftRear");
        leftRear.setRunMode(Motor.RunMode.RawPower);
        rightRear = new Motor(hardwareMap,"rightRear");
        rightRear.setRunMode(Motor.RunMode.RawPower);

        leftLift = hardwareMap.dcMotor.get("leftLiftMotor");
        rightLift = hardwareMap.dcMotor.get("rightLiftMotor");
        List<DcMotor> liftMotors = Arrays.asList(leftLift, rightLift);
        for (DcMotor motor : liftMotors) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        fourBar1 = hardwareMap.servo.get("4bar 1");
        fourBar1.setDirection(Servo.Direction.REVERSE);
        fourBar2 = hardwareMap.servo.get("4bar 2");
        fourBar2.setDirection(Servo.Direction.FORWARD);

        grabber = hardwareMap.servo.get("Grabber");
        if (mode.equals(OpModes.AUTO)) {
            int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
            AprilTagPipeline = new AprilTagDetectionPipeline(0.2, 1430, 1430, 480, 620); // these values might be wrong I got them off some random website
            webcam.setPipeline(AprilTagPipeline);
            webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
                @Override
                public void onOpened() { webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT); }
                @Override
                public void onError(int errorCode) {}
            });
        }
    }
}
