package org.firstinspires.ftc.common.Hardware;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


public class Lift extends SubsystemBase {
    DcMotor leftLiftMotor;
    DcMotor rightLiftMotor;
    DcMotor[] motors = new DcMotor[]{leftLiftMotor, rightLiftMotor};
    int[] positions = new int[]{0, 1, 2};
    int tolerance = 10;
    public enum Positions {
        LOW,
        MEDIUM,
        HIGH
    }


    public Lift (LinearOpMode opMode) {
        leftLiftMotor = opMode.hardwareMap.dcMotor.get("leftLiftMotor");
        rightLiftMotor = opMode.hardwareMap.dcMotor.get("rightLiftMotor");
        for (DcMotor motor: motors) {
            motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    }

    public void setLow() {
        leftLiftMotor.setTargetPosition(positions[0]);
        rightLiftMotor.setTargetPosition(positions[0]);
    }

    public void setMedium() {
        leftLiftMotor.setTargetPosition(positions[1]);
        rightLiftMotor.setTargetPosition(positions[1]);
    }

    public void setHigh() {
        leftLiftMotor.setTargetPosition(positions[2]);
        rightLiftMotor.setTargetPosition(positions[2]);
    }
    @Override
    public void periodic() {
        for (DcMotor motor: motors) {
            if (motor.getCurrentPosition() - motor.getTargetPosition() >= tolerance) {
                motor.setPower(0.4);
            } else {
                motor.setPower(0);
            }
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
