package org.firstinspires.ftc.common.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.common.Interfaces.Subsystem;

public class Drivetrain implements Subsystem {
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftRear;
    private DcMotor rightRear;
    private double LF, RF, LR, RR, rightY, rightX, leftY, leftX;

    @Override
    public void initialize(LinearOpMode opMode) {
        leftFront = opMode.hardwareMap.dcMotor.get("leftFront");
        rightFront = opMode.hardwareMap.dcMotor.get("rightFront");
        leftRear = opMode.hardwareMap.dcMotor.get("leftRear");
        rightRear = opMode.hardwareMap.dcMotor.get("rightRear");
    }
    public void calculatePower() {
        /*rightY = -Gamepad.gamepad1.right_stick_y;
        rightX = gamepad1.right_stick_x;
        leftY = -gamepad1.left_stick_y;
        leftX = gamepad1.left_stick_x;*/
    }
}
