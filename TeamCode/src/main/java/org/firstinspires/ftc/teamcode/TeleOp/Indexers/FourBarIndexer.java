package org.firstinspires.ftc.teamcode.TeleOp.Indexers;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.common.Hardware.FourBarSubsystem;
import org.firstinspires.ftc.common.Hardware.RobotHardware;

@TeleOp(name="FourBar Indexer", group="Indexer")
public class FourBarIndexer extends CommandOpMode {
    FourBarSubsystem fourBar;
    GamepadEx gamepadEx;
    RobotHardware robot = RobotHardware.getInstance();

    @Override
    public void initialize() {
        robot.init(hardwareMap, RobotHardware.OpModes.TELEOP);
        fourBar = new FourBarSubsystem(robot);
        gamepadEx = new GamepadEx(gamepad1);

        CommandScheduler.getInstance().reset();
        register(fourBar);

        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(() -> schedule(new InstantCommand(() -> fourBar.incrementAndSetPosition())));
        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(() -> schedule(new InstantCommand(() -> fourBar.decrementAndSetPosition())));
    }

    @Override
    public void run() {
        CommandScheduler.getInstance().run();
        telemetry.addData("Current Position:", fourBar.getLastSetPosition());
        telemetry.update();
    }
}
