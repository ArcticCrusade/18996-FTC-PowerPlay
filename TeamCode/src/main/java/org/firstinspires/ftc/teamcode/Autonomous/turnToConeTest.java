package org.firstinspires.ftc.teamcode.Autonomous;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.Robot;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.common.Hardware.Camera;
import org.firstinspires.ftc.common.Hardware.DriveSubsystem;
import org.firstinspires.ftc.common.Hardware.RobotHardware;
import org.firstinspires.ftc.common.Commands.TurnToCone;

@TeleOp
public class turnToConeTest extends CommandOpMode {
    RobotHardware robot = RobotHardware.getInstance();
    DriveSubsystem driveSubsystem;
    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();
        robot.init(hardwareMap, RobotHardware.OpModes.AUTO);
        driveSubsystem = new DriveSubsystem(robot);
        register(driveSubsystem, robot.camera);
    }

    @Override
    public void run() {
        CommandScheduler.getInstance().run();
        CommandScheduler.getInstance().schedule(
                new TurnToCone(driveSubsystem, robot.camera)
        );
    }
}
