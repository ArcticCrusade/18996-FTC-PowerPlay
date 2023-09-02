package org.firstinspires.ftc.teamcode.TeleOp.Indexers;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.common.Hardware.LiftSubsystem;
import org.firstinspires.ftc.common.Hardware.LiftSubsystem.Positions;
import org.firstinspires.ftc.common.Hardware.RobotHardware;

import com.arcrobotics.ftclib.command.CommandOpMode;

@TeleOp(name="lift indexer", group="Indexer")
public class LiftIndexer extends CommandOpMode {
    LiftSubsystem lift;
    GamepadEx gamepadEx;
    Positions currentState = Positions.LOW;
    int stepSize = 400;
    RobotHardware robot = RobotHardware.getInstance();
    @Override
    public void initialize() {
        robot.init(hardwareMap, RobotHardware.OpModes.TELEOP);
        lift = new LiftSubsystem(robot);
        gamepadEx = new GamepadEx(gamepad1);

        CommandScheduler.getInstance().reset();
        register(lift);

        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(() -> schedule(new InstantCommand(() -> lift.overrideValue(currentState, lift.getStateValue(currentState) + stepSize))));
        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(() -> schedule(new InstantCommand(() -> lift.overrideValue(currentState, lift.getStateValue(currentState) - stepSize))));
        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                .whenPressed(() -> schedule(new InstantCommand(() -> stepSize -= 50)));
        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(() -> schedule(new InstantCommand(() -> stepSize += 50)));
        gamepadEx.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(() -> schedule(new InstantCommand(() -> currentState = Positions.LOW)));
        gamepadEx.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(() -> schedule(new InstantCommand(() -> currentState = Positions.MEDIUM)));
        gamepadEx.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(() -> schedule(new InstantCommand(() -> currentState = Positions.HIGH)));
        gamepadEx.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(() -> schedule(new InstantCommand(() -> lift.setHeight(currentState))));
    }
    @Override
    public void run() {
        CommandScheduler.getInstance().run();
        lift.periodic();
    }
}