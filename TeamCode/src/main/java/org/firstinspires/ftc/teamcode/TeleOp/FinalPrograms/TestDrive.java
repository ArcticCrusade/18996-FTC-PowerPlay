package org.firstinspires.ftc.teamcode.TeleOp.FinalPrograms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.common.Hardware.Drivetrain;

@TeleOp(name="Full Power Test Drive", group="Linear Opmode")
public class TestDrive extends LinearOpMode {
    private Drivetrain drivetrain;

    @Override
    public void runOpMode() {
        drivetrain.initialize(this);
        drivetrain.setLinearSpeedConfig(0.05, 0.0, 1.0);

        waitForStart();

        while (opModeIsActive()) {
            drivetrain.calculateAndSetPower(gamepad1.left_stick_x,
                                            gamepad1.left_stick_y,
                                            gamepad1.right_stick_x,
                                            gamepad1.right_stick_y);
        }
    }
}