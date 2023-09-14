package org.firstinspires.ftc.teamcode.Autonomous;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.Robot;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.common.Hardware.Camera;
import org.firstinspires.ftc.common.Hardware.DriveSubsystem;
import org.firstinspires.ftc.common.Hardware.RobotHardware;
import org.firstinspires.ftc.common.Commands.TurnToCone;

import java.util.LinkedList;
import java.util.Queue;

@TeleOp
public class turnToConeTest extends CommandOpMode {
    RobotHardware robot = RobotHardware.getInstance();
    DriveSubsystem driveSubsystem;
    TurnToCone t;
    Queue<Double> angles = new LinkedList();
    @Override
    public void initialize() {
        telemetry.addData("Initiation", "Initiating...");
        telemetry.update();

        CommandScheduler.getInstance().reset();
        robot.init(hardwareMap, RobotHardware.OpModes.AUTO);
        driveSubsystem = new DriveSubsystem(robot);
        register(driveSubsystem, robot.camera);
        t = new TurnToCone(driveSubsystem, robot.camera);
        telemetry.addData("Initiation", "Complete");
        telemetry.update();

        CommandScheduler.getInstance().schedule(t);
    }

    @Override
    public void run() {
        CommandScheduler.getInstance().run();
        telemetry.addData("CenterVals", t.getCenterVal());
        telemetry.addData("Times", t.getTimes());
        telemetry.addData("Do I see something?", t.foundContour());
        angles.add((double) robot.imu.getAngularOrientation().firstAngle);
        if (angles.size() >= 11) {
            angles.poll();
        }
        telemetry.addData("Angles", angles);
        telemetry.update();
    }
}
