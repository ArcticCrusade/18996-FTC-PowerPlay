package org.firstinspires.ftc.common.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.common.Interfaces.Subsystem;

public class FourBar implements Subsystem {
    Servo fourBarServo1;
    Servo fourBarServo2;

    double pickUpPosition;
    double highDropPosition;
    double flatPosition;

    @Override
    public void initialize(LinearOpMode opMode) {
        fourBarServo1 = opMode.hardwareMap.servo.get("4bar 1");
        fourBarServo2 = opMode.hardwareMap.servo.get("4bar 2");
        // TODO: find out what these values are
        pickUpPosition = .1;
        highDropPosition = .2;
        flatPosition = .3;
    }

    public void setHigh() {
        fourBarServo1.setPosition(highDropPosition);
        fourBarServo2.setPosition(highDropPosition);
    }

    public void setPickUpPosition() {
        fourBarServo1.setPosition(pickUpPosition);
        fourBarServo2.setPosition(pickUpPosition);
    }

    public void setFlat() {
        fourBarServo1.setPosition(flatPosition);
        fourBarServo2.setPosition(flatPosition);
    }

    public void incrementAndSetPosition() {
        fourBarServo1.setPosition(Math.min(fourBarServo1.getPosition() + .01, 1));
        fourBarServo2.setPosition(Math.min(fourBarServo2.getPosition() + .01, 1));
    }

    public void decrementAndSetPosition() {
        fourBarServo1.setPosition(Math.min(fourBarServo1.getPosition() - .01, 0));
        fourBarServo2.setPosition(Math.min(fourBarServo2.getPosition() - .01, 0));
    }

    public double getLastSetPosition() {
        return fourBarServo1.getPosition();
    }

}
