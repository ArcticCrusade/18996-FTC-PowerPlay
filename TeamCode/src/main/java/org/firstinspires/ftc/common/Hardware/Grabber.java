package org.firstinspires.ftc.common.Hardware;

import static java.lang.Thread.sleep;

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
        grabPosition = .4;
        releasePosition = .5;
    }

    public void grab() throws InterruptedException {
        grabberServo.setPosition(grabPosition);
        sleep(waitTime);
    }

    public void release() throws InterruptedException {
        grabberServo.setPosition(releasePosition);
        sleep(waitTime);
    }
}
