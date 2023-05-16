package org.firstinspires.ftc.teamcode.TeleOp;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.common.Hardware.TeleOpSpeedConfigs;
import org.firstinspires.ftc.common.Hardware.TeleOpStateConfig;


public class DrivingConfig extends LinearOpMode {
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftRear;
    private DcMotor rightRear;
    private double LF, RF, LR, RR, Y1, X1, Y2, X2, powerScale;
    TeleOpSpeedConfigs stateMachine;
    @Override
    public void runOpMode() {
        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightRear = hardwareMap.dcMotor.get("rightRear");

        while (opModeIsActive()) {
            TeleOpStateConfig currentState = stateMachine.getConfig();
            LF = 0; RF = 0; LR = 0; RR = 0;
            Y1 = 0; X1 = 0; Y2 = 0; X2 = 0;

            // Get joystick values
            Y1 = gamepad1.right_stick_y;
            X1 = -gamepad1.right_stick_x;
            Y2 = -gamepad1.left_stick_y;
            X2 = gamepad1.left_stick_x;

            //calculate motor output from joysticks
            LF = Y1 - X1 + X2;
            RF = Y1 + X1 - X2;
            LR = Y1 + X1 + X2;
            RR = Y1 - X1 - X2;

            //set motor commands
            leftFront.setPower(LF * powerScale);
            rightFront.setPower(RF * powerScale);
            leftRear.setPower(LR * powerScale);
            rightRear.setPower(RR * powerScale);

            telemetry.update();
        }
    }
}
