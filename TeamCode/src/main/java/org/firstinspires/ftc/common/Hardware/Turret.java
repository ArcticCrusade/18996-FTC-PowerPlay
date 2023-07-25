package org.firstinspires.ftc.common.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.common.Interfaces.Subsystem;

public class Turret implements Subsystem {
    Servo turretServo;

    @Override
    public void initialize(LinearOpMode opMode) {
        turretServo = opMode.hardwareMap.servo.get("turret");
    }

    public void setPosition(double position) {
        turretServo.setPosition(position);
    }


    public void incrementAndSetPosition() {
        turretServo.setPosition(Math.min(turretServo.getPosition() + .01, 1));
    }

    public void decrementAndSetPosition() {
        turretServo.setPosition(Math.max(turretServo.getPosition() - .01, 0));
    }

    public double getPosition() {
        return turretServo.getPosition();
    }

    // TODO: Create set positions
}
