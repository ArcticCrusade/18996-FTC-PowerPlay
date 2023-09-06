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
    Camera camera;

    @Override
    public void initialize() {
        robot.init(hardwareMap, RobotHardware.OpModes.AUTO);
        driveSubsystem = new DriveSubsystem(robot);
        camera = new Camera(hardwareMap);
    }

    @Override
    public void runOpMode() {
        CommandScheduler.getInstance().schedule(
                new TurnToCone(driveSubsystem, camera)
        );
    }
}
