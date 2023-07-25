package org.firstinspires.ftc.common.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.common.Interfaces.Subsystem;

public class FourBar implements Subsystem {
    Servo fourBarServo;

    double lowerPosition;
    double mediumPosition;
    double highPosition;

    @Override
    public void initialize(LinearOpMode opMode) {
        fourBarServo = opMode.hardwareMap.servo.get("4bar");
        // TODO: find out what these values are
        lowerPosition = 0;
        mediumPosition = 0;
        highPosition = 0;
    }

    public void setPosition(double position) {
        fourBarServo.setPosition(position);
    }

    public void setLow() {
        fourBarServo.setPosition(lowerPosition);
    }

    public void setMedium() {
        fourBarServo.setPosition(mediumPosition);
    }

    public void setHigh() {
        fourBarServo.setPosition(highPosition);
    }


    public void incrementAndSetPosition() {
        fourBarServo.setPosition(Math.min(fourBarServo.getPosition() + .01, 1));
    }

    public void decrementAndSetPosition() {
        fourBarServo.setPosition(Math.max(fourBarServo.getPosition() - .01, 0));
    }

    public double getLastSetPosition() {
        return fourBarServo.getPosition();
    }
}
