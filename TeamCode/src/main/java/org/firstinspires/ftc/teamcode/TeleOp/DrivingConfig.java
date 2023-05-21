package org.firstinspires.ftc.teamcode.TeleOp;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.common.Hardware.DimensionalMovementConfig;


public class DrivingConfig extends LinearOpMode {
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftRear;
    private DcMotor rightRear;
    private double LF, RF, LR, RR, rightY, rightX, leftY, leftX;
    @Override
    public void runOpMode() {
        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightRear = hardwareMap.dcMotor.get("rightRear");
        DimensionalMovementConfig X1 = new DimensionalMovementConfig(0.0, 0.2, 0.3, "linear", 2.1);
        DimensionalMovementConfig X2 = new DimensionalMovementConfig(0.0, 0.2, 0.3, "linear", 2.1);
        DimensionalMovementConfig Y1 = new DimensionalMovementConfig(0.0, 0.2, 0.3, "linear", 2.1);
        DimensionalMovementConfig Y2 = new DimensionalMovementConfig(0.0, 0.2, 0.3, "linear", 2.1);

        while (opModeIsActive()) {
            LF = 0; RF = 0; LR = 0; RR = 0;


            // Get joystick values
            rightY = -gamepad1.right_stick_y;
            rightX = gamepad1.right_stick_x;
            leftY = -gamepad1.left_stick_y;
            leftX = gamepad1.left_stick_x;

            //calculate motor output from joysticks
            LF = rightY - rightX + leftX;
            RF = rightY + rightX - leftX;
            LR = rightY + rightX + leftX;
            RR = rightY - rightX - leftX;

            //set motor commands
            leftFront.setPower(LF);
            rightFront.setPower(RF);
            leftRear.setPower(LR);
            rightRear.setPower(RR);

            telemetry.update();
        }
    }
}
