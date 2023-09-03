package org.firstinspires.ftc.teamcode.TeleOp.FinalPrograms;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.common.Hardware.DeliverySystem;
import org.firstinspires.ftc.common.Hardware.RobotHardware;

@TeleOp(name="Final TeleOp", group="Teleop")
public class FinalTeleOp extends CommandOpMode {
    GamepadEx drivePad;
    RobotHardware robot = RobotHardware.getInstance();
    DeliverySystem delivery;
    MecanumDrive drive;
    @Override
    public void initialize() {
        robot.init(hardwareMap, RobotHardware.OpModes.TELEOP);
        delivery = new DeliverySystem(robot);
        drive = new MecanumDrive(robot.leftFront, robot.rightFront, robot.leftRear, robot.rightRear);

        drivePad = new GamepadEx(gamepad1);
        CommandScheduler.getInstance().reset();
        register(delivery);
        register(delivery.getFourBar());
        register(delivery.getLift());
        register(delivery.getGrabber());

        drivePad.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(() -> schedule(new InstantCommand(() -> delivery.intake())));
        drivePad.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(() -> schedule(new InstantCommand(() -> delivery.dropHigh())));
    }
    @Override
    public void run() {
        CommandScheduler.getInstance().run();
        delivery.periodic();
        drive.driveRobotCentric(drivePad.getLeftX(), drivePad.getLeftY(), drivePad.getRightY());
    }
}

