package org.firstinspires.ftc.common.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.common.Hardware.Camera;
import org.firstinspires.ftc.common.Hardware.DriveSubsystem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class TurnToCone extends CommandBase {
    enum Turning {
        RIGHT,
        LEFT,
        FINDING
    }
    int increment = 0;
    Turning turnState = Turning.FINDING;
    private final DriveSubsystem drivetrain;
    private final Camera camera;
    int centerVal = -1;
    Queue<Integer> centerVals = new LinkedList();
    Queue<Double> times = new LinkedList();
    ElapsedTime time = new ElapsedTime();
    public TurnToCone(DriveSubsystem newDrivetrain, Camera newCamera) {
        drivetrain = newDrivetrain;
        camera = newCamera;
        addRequirements(newDrivetrain, drivetrain);
    }
    public Queue<Integer> getCenterVal() {
        return centerVals;
    }
    public Queue<Double> getTimes() {
        return times;
    }
    public boolean foundContour() {
        return camera.getConePipeline().foundContour;
    }
    @Override
    public void initialize() {
        camera.switchPipeline("red");
        time.reset();
    }

    @Override
    public void execute() {
        times.add(time.milliseconds());
        if (times.size() >= 11) {
            times.poll();
        }
        if (camera.getConePipeline().foundContour) {
            centerVal = camera.getConePipeline().getCenter();
            centerVals.add(centerVal);
            if (centerVals.size() >= 11) {
                centerVals.poll();
            }
            /*if (30 <= centerVal && centerVal <= 38) {
                if (turnState.equals(Turning.RIGHT)) {
                    increment++;
                }
                turnState = Turning.LEFT;
                drivetrain.manualPower(0, 0, Math.max(.3 -0.01*increment,0.125));
            } else {
                if (turnState.equals(Turning.LEFT)) {
                    increment++;
                }
                turnState = Turning.RIGHT;
                drivetrain.manualPower(0, 0, Math.max(.3 -0.01*increment,0.125));
            }*/
        } else {
            drivetrain.manualPower(0, 0, -.3);
            increment = 0;
        }
    }

    @Override
    public boolean isFinished() {
        if (30 <= centerVal && centerVal <= 38) {
            drivetrain.manualPower(0, 0, 0);
            return true;
        }
        return false;
    }
}
