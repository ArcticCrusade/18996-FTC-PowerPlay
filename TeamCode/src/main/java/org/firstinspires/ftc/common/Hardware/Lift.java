package org.firstinspires.ftc.common.Hardware;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Lift extends SubsystemBase {
    DcMotor leftLiftMotor;
    DcMotor rightLiftMotor;
    int lowPosition;
    int mediumPosition;
    int highPosition;

    public Lift(LinearOpMode opMode) {
        leftLiftMotor = opMode.hardwareMap.dcMotor.get("leftLiftMotor");
        rightLiftMotor = opMode.hardwareMap.dcMotor.get("rightLiftMotor");
        leftLiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftLiftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightLiftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lowPosition = 0;
        mediumPosition = 1;
        highPosition = 2;
    }

    public void setLow() {
        leftLiftMotor.setTargetPosition(lowPosition);
        rightLiftMotor.setTargetPosition(lowPosition);
        leftLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (leftLiftMotor.isBusy() && rightLiftMotor.isBusy()) {}
    }

    public void setMedium() {
        leftLiftMotor.setTargetPosition(mediumPosition);
        rightLiftMotor.setTargetPosition(mediumPosition);
        leftLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (leftLiftMotor.isBusy() && rightLiftMotor.isBusy()) {}
    }

    public void setHigh() {
        leftLiftMotor.setTargetPosition(highPosition);
        rightLiftMotor.setTargetPosition(highPosition);
        leftLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        while (leftLiftMotor.isBusy() && rightLiftMotor.isBusy()) {}
    }

    public void overrideValue(String height, int value) {
        switch (height) {
            case "low":
                lowPosition = value;
                break;
            case "medium":
                mediumPosition = value;
                break;
            case "high":
                highPosition = value;
                break;
        }
    }

    public int getStateValue(String height) {
        switch (height) {
            case "low":
                return lowPosition;
            case "medium":
                return mediumPosition;
            case "high":
                return highPosition;
        }
        return 0;
    }
}
