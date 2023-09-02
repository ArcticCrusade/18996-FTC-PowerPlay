package org.firstinspires.ftc.common.Hardware;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


public class LiftSubsystem extends SubsystemBase {
    RobotHardware robot;
    int[] positions = new int[]{0, 1, 2};
    int tolerance = 10;
    int targetPosition = 0;
    public enum Positions {
        LOW,
        MEDIUM,
        HIGH
    }

    public LiftSubsystem(RobotHardware robot) {
        this.robot = robot;
    }

    public void setLow() { targetPosition = positions[0]; }

    public void setMedium() { targetPosition = positions[1]; }

    public void setHigh() { targetPosition = positions[2]; }
    public void setHeight(Positions position) {
        switch (position) {
            case LOW:
                setLow();
            case MEDIUM:
                setMedium();
            case HIGH:
                setHigh();
        }
    }

    //ewwwwwwwwwww
    @Override
    public void periodic() {
        robot.leftLift.setTargetPosition(targetPosition);
        robot.rightLift.setTargetPosition(targetPosition);
        if (robot.leftLift.getCurrentPosition() - robot.leftLift.getTargetPosition() >= tolerance) {
            robot.leftLift.setPower(0.4);
        } else {
            robot.leftLift.setPower(0);
        }
        if (robot.rightLift.getCurrentPosition() - robot.rightLift.getTargetPosition() >= tolerance) {
            robot.rightLift.setPower(0.4);
        } else {
            robot.rightLift.setPower(0);
        }
    }

    public void overrideValue(Positions position, int value) {
        switch (position) {
            case LOW:
                positions[0] = value;
                break;
            case MEDIUM:
                positions[1] = value;
                break;
            case HIGH:
                positions[2] = value;
                break;
        }
    }

    public int getStateValue(Positions position) {
        switch (position) {
            case LOW:
                return positions[0];
            case MEDIUM:
                return positions[1];
            case HIGH:
                return positions[2];
        }
        return 0;
    }
}
