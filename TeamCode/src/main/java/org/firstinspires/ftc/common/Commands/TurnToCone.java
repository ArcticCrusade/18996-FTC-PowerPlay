package org.firstinspires.ftc.common.Commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.common.Hardware.Camera;
import org.firstinspires.ftc.common.Hardware.DriveSubsystem;


public class TurnToCone extends CommandBase {

    private final DriveSubsystem drivetrain;
    private final Camera camera;
    int centerVal;

    public TurnToCone(DriveSubsystem newDrivetrain, Camera newCamera) {
        drivetrain = newDrivetrain;
        camera = newCamera;
        addRequirements(newDrivetrain, drivetrain);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        camera.switchPipeline("red");
        if (camera.getConePipeline().foundContour) {
            centerVal = camera.getConePipeline().getCenter();
            if (centerVal < 160) {
                drivetrain.manualPower(0, 0, .3);
            } else {
                drivetrain.manualPower(0, 0, -.3);
            }
        }
        else {
            drivetrain.manualPower(0, 0, -.3);
        }
    }

    @Override
    public boolean isFinished() {
        return (centerVal >= 155 && centerVal <= 165);
    }
}
