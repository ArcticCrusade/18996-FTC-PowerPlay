package org.firstinspires.ftc.common.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.common.Interfaces.Subsystem;

public class Lift implements Subsystem {
    DcMotor leftLiftMotor;
    DcMotor rightLiftMotor;
    int lowPosition;
    int mediumPosition;
    int highPosition;

    @Override
    public void initialize(LinearOpMode opMode) {
        leftLiftMotor = opMode.hardwareMap.dcMotor.get("leftLiftMotor");
        rightLiftMotor = opMode.hardwareMap.dcMotor.get("rightLiftMotor");
        leftLiftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightLiftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lowPosition = 0;
        mediumPosition = 1;
        highPosition = 2;
    }

    public void setLow() {
        leftLiftMotor.setTargetPosition(lowPosition);
        rightLiftMotor.setTargetPosition(lowPosition);
    }

    public void setMedium() {
        leftLiftMotor.setTargetPosition(mediumPosition);
        rightLiftMotor.setTargetPosition(mediumPosition);
    }

    public void setHigh() {
        leftLiftMotor.setTargetPosition(highPosition);
        rightLiftMotor.setTargetPosition(highPosition);
    }
}
