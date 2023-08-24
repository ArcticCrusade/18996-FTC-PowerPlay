package org.firstinspires.ftc.common.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.common.Interfaces.Subsystem;

public class FourBar implements Subsystem {
    Servo fourBarServo;

    double pickUpPosition;
    double highDropPosition;
    double flatPosition;

    @Override
    public void initialize(LinearOpMode opMode) {
        fourBarServo = opMode.hardwareMap.servo.get("4bar");
        // TODO: find out what these values are
        pickUpPosition = 0;
        highDropPosition = 1;
        flatPosition = .5;
    }

    public void setHigh() {
        fourBarServo.setPosition(highDropPosition);
    }

    public void setPickUpPosition() {
        fourBarServo.setPosition(pickUpPosition);
    }
}
