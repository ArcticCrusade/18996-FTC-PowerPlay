package org.firstinspires.ftc.teamcode.Indexers;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.common.Hardware.Turret;

@TeleOp(name="Turret Indexer", group="Linear Opmode")
public class TurretIndexer extends LinearOpMode {
    Turret turret;
    boolean upPressed = false;
    boolean downPressed = false;
    @Override
    public void runOpMode() throws InterruptedException {
        if (gamepad1.dpad_up) {
            if (!upPressed) {
                turret.incrementAndSetPosition();
            }
            upPressed = true;
        } else {
            upPressed = false;
        }

        if (gamepad1.dpad_down) {
            if (!upPressed) {
                turret.incrementAndSetPosition();
            }
            upPressed = true;
        } else {
            upPressed = false;
        }

        telemetry.addData("Current Position:", turret.getLastSetPosition());
    }
}
