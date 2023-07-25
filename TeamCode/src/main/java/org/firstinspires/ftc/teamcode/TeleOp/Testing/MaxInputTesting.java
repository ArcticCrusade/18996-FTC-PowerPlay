package org.firstinspires.ftc.teamcode.TeleOp.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name="Input Testing", group="Linear Opmode")
public class MaxInputTesting extends LinearOpMode {
    private double LF, RF, LR, RR, Y1, X1, Y2, X2;
    private double maxLF, maxRF, maxLR, maxRR;

    @Override
    public void runOpMode() {
        waitForStart();
        while (opModeIsActive()) {
            LF = 0; RF = 0; LR = 0; RR = 0;
            Y1 = 0; X1 = 0; Y2 = 0; X2 = 0;

            // Get joystick values
            Y1 = gamepad1.right_stick_y;
            X1 = gamepad1.right_stick_x;
            Y2 = gamepad1.left_stick_y;
            X2 = gamepad1.left_stick_x;

            //calculate motor output from joysticks
            LF = Y1 - X1 + X2;
            RF = Y1 + X1 - X2;
            LR = Y1 + X1 + X2;
            RR = Y1 - X1 - X2;

            maxLF = Math.max(maxLF, LF);
            maxRF = Math.max(maxLF, RF);
            maxLR = Math.max(maxLF, LR);
            maxRR = Math.max(maxLF, RR);

            telemetry.addData("X1", X1);
            telemetry.addData("Y1", Y1);
            telemetry.addData("X2", X2);
            telemetry.addData("Y2", Y2);
            telemetry.update();
        }
    }
}
