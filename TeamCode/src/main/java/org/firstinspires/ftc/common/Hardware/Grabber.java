package org.firstinspires.ftc.common.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.common.Interfaces.Subsystem;
import com.qualcomm.robotcore.hardware.Servo;


public class Grabber implements Subsystem {
    Servo grabberServo;
    double grabPosition;
    double releasePosition;
    long waitTime;

    // TODO: Figure out values

    @Override
    public void initialize(LinearOpMode opmode) {
        grabberServo = opmode.hardwareMap.servo.get("Grabber");
    }

    public void grab() throws InterruptedException {
        grabberServo.setPosition(grabPosition);
        wait(waitTime);
    }

    public void release() throws InterruptedException {
        grabberServo.setPosition(releasePosition);
        wait(waitTime);
    }
}
