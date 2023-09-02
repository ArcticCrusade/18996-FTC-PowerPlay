package org.firstinspires.ftc.common.Hardware;

import static java.lang.Thread.sleep;

import com.arcrobotics.ftclib.command.SubsystemBase;


public class GrabberSubsystem extends SubsystemBase {
    RobotHardware robot;
    double grabPosition;
    double releasePosition;
    double targetPosition;

    public GrabberSubsystem(RobotHardware robot) {
        this.robot = robot;
        grabPosition = .4;
        releasePosition = .5;
    }

    public void grab() {
        targetPosition = grabPosition;
    }

    public void release() {
        targetPosition = releasePosition;
    }
    public void periodic() {
        robot.grabber.setPosition(targetPosition);
    }
}
