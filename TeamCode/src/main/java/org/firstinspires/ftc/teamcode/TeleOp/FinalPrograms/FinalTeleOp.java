package org.firstinspires.ftc.teamcode.TeleOp.FinalPrograms;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.common.Hardware.DeliverySystem;
import org.firstinspires.ftc.common.Hardware.DriveSubsystem;
import org.firstinspires.ftc.common.Hardware.RobotHardware;

@TeleOp(name="Final TeleOp", group="Teleop")
public class FinalTeleOp extends CommandOpMode {
    GamepadEx drivePad;
    RobotHardware robot = RobotHardware.getInstance();
    DeliverySystem delivery;
    DriveSubsystem drive;
    @Override
    public void initialize() {
        robot.init(hardwareMap, RobotHardware.OpModes.TELEOP);
        delivery = new DeliverySystem(robot);
        drive = new DriveSubsystem(robot);
        drivePad = new GamepadEx(gamepad1);
        CommandScheduler.getInstance().reset();

        register(delivery);
        register(delivery.getFourBar());
        register(delivery.getLift());
        register(delivery.getGrabber());
        register(drive);

        drivePad.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(() -> schedule(new InstantCommand(() -> delivery.intake())));
        drivePad.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(() -> schedule(new InstantCommand(() -> delivery.dropHigh())));
    }
    @Override
    public void run() {
        CommandScheduler.getInstance().run();
        drive.calculatePower(drivePad.getLeftX(), drivePad.getLeftY(), drivePad.getRightX());
    }
}

