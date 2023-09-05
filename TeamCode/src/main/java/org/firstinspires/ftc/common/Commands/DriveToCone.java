package org.firstinspires.ftc.common.Commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.checkerframework.checker.units.qual.C;
import org.firstinspires.ftc.common.Hardware.Camera;
import org.firstinspires.ftc.common.Hardware.DriveSubsystem;


public class DriveToCone extends CommandBase {

    private final DriveSubsystem drivetrain;
    private final Camera camera;
    int centerVal;

    public DriveToCone(DriveSubsystem newDrivetrain, Camera newCamera) {
        drivetrain = newDrivetrain;
        camera = newCamera;
        addRequirements(newDrivetrain, newCamera);
    }

    @Override
    public void initialize() {
        camera.switchPipeline("red");
    }

    @Override
    public void execute() {
        centerVal = camera.getConePipeline().getCenter();
        if (centerVal < 160) {
            drivetrain.manualPower(0, 0, .3);
        } else {
            drivetrain.manualPower(0, 0, -.3);
        }
    }

    @Override
    public boolean isFinished() {
        return (centerVal >= 155 && centerVal <= 165);
    }
}
