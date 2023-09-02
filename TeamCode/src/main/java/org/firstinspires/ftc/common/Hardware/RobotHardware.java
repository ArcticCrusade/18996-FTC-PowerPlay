package org.firstinspires.ftc.common.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Arrays;
import java.util.List;

public class RobotHardware {
    public DcMotor leftFront, rightFront, leftRear, rightRear;
    public DcMotor leftLift, rightLift;

    public Servo fourBar1, fourBar2;
    public Servo grabber;

    private static RobotHardware instance = new RobotHardware();
    private HardwareMap hardwareMap;

    public static RobotHardware getInstance() {
        return instance;
    }

    public void init(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        leftFront = hardwareMap.dcMotor.get("leftFront");
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        rightFront = hardwareMap.dcMotor.get("rightFront");
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        leftRear = hardwareMap.dcMotor.get("leftRear");
        leftRear.setDirection(DcMotor.Direction.REVERSE);
        rightRear = hardwareMap.dcMotor.get("rightRear");
        rightRear.setDirection(DcMotor.Direction.FORWARD);
        List<DcMotor> driveMotors = Arrays.asList(leftFront, rightFront, leftRear, rightRear);
        for (DcMotor motor : driveMotors) {
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }

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
    }
}
