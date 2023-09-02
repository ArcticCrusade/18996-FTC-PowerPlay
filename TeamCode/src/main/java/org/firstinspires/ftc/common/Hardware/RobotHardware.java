package org.firstinspires.ftc.common.Hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class RobotHardware {
    public DcMotor leftLift;
    public DcMotor rightLift;

    public Servo fourBar1;
    public Servo fourBar2;

    private static RobotHardware instance = new RobotHardware();
    private HardwareMap hardwareMap;

    public static RobotHardware getInstance() {
        return instance;
    }

    public void init(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        leftLift = hardwareMap.dcMotor.get("leftLiftMotor");
        leftLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightLift = hardwareMap.dcMotor.get("rightLiftMotor");
        rightLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        fourBar1 = hardwareMap.servo.get("4bar 1");
        fourBar1.setDirection(Servo.Direction.REVERSE);
        fourBar2 = hardwareMap.servo.get("4bar 2");
        fourBar2.setDirection(Servo.Direction.FORWARD);
    }
}
