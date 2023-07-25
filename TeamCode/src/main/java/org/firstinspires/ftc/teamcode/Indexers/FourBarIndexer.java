package org.firstinspires.ftc.teamcode.Indexers;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.common.Hardware.FourBar;

@TeleOp(name="FourBarIndexer", group="Linear Opmode")
public class FourBarIndexer extends LinearOpMode {
    FourBar fourBar;
    boolean upPressed = false;
    boolean downPressed = false;
    @Override
    public void runOpMode() throws InterruptedException {
        while (opModeIsActive()) {
            if (gamepad1.dpad_up) {
                if (!upPressed) {
                    fourBar.incrementAndSetPosition();
                }
                upPressed = true;
            } else {
                upPressed = false;
            }

            if (gamepad1.dpad_down) {
                if (!upPressed) {
                    fourBar.incrementAndSetPosition();
                }
                upPressed = true;
            } else {
                upPressed = false;
            }
            telemetry.addData("Current Position:", fourBar.getLastSetPosition());
            telemetry.update();
            wait(20);
        }
    }
}
