package org.firstinspires.ftc.common.Hardware;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class FourBarSubsystem extends SubsystemBase {
    RobotHardware robot;

    double pickUpPosition;
    double highDropPosition;
    double flatPosition;
    double targetPosition;

    public FourBarSubsystem(RobotHardware robot) {
        this.robot = robot;
        pickUpPosition = 0;
        highDropPosition = 0.01;
        flatPosition = 0.02;
    }

    public void setHigh() {
        targetPosition = highDropPosition;
    }

    public void setPickUpPosition() {
        targetPosition = pickUpPosition;
    }

    public void setFlat() {
        targetPosition = flatPosition;
    }

    public void incrementAndSetPosition() {
        targetPosition = Math.min(robot.fourBar1.getPosition() + .002, 1);
    }

    public void decrementAndSetPosition() {
        targetPosition = Math.min(robot.fourBar1.getPosition() - .002, 1);
    }
    @Override
    public void periodic() {
        robot.fourBar1.setPosition(targetPosition);
        robot.fourBar2.setPosition(getLastSetPosition());
    }

    public double getLastSetPosition() {
        return robot.fourBar1.getPosition();
    }
}
