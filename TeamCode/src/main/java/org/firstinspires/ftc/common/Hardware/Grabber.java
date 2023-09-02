package org.firstinspires.ftc.common.Hardware;

import static java.lang.Thread.sleep;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.common.Interfaces.Subsystem;
import com.qualcomm.robotcore.hardware.Servo;


public class Grabber extends SubsystemBase {
    RobotHardware robot;
    double grabPosition;
    double releasePosition;
    double targetPosition;

    public Grabber(RobotHardware robot) {
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
