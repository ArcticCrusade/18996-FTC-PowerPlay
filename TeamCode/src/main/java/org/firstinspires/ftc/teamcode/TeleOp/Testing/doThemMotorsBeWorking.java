package org.firstinspires.ftc.teamcode.TeleOp.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class doThemMotorsBeWorking extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor leftLiftMotor = hardwareMap.dcMotor.get("leftLiftMotor");
        DcMotor rightLiftMotor = hardwareMap.dcMotor.get("rightLiftMotor");
        leftLiftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightLiftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.dpad_up) {
                rightLiftMotor.setPower(1);
                leftLiftMotor.setPower(1);
            }
            else if (gamepad1.dpad_down) {
                rightLiftMotor.setPower(-1);
                leftLiftMotor.setPower(-1);
            }
            else {
                rightLiftMotor.setPower(0);
                leftLiftMotor.setPower(0);
            }
        }
    }
}
