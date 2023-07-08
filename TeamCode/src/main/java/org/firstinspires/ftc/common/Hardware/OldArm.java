package org.firstinspires.ftc.common.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.common.Interfaces.Subsystem;

public class OldArm implements Subsystem {
    private Servo lowArm, upArm, cone, popper, light;
    public void initialize(LinearOpMode opMode) {
        lowArm = opMode.hardwareMap.servo.get("rotator"); //0e
        upArm = opMode.hardwareMap.servo.get("extender"); //0
        cone = opMode.hardwareMap.servo.get("cone"); //2
        popper = opMode.hardwareMap.servo.get("popper"); //3
        light = opMode.hardwareMap.servo.get("release");//1e
    }
    public void servosGoToUpright() {
        cone.setPosition(0.6);
        upArm.setPosition(0.64);
        lowArm.setPosition(0.26 + 0.07);
        light.setPosition(0);
    }
    public void servosGoToPickup() {
        cone.setPosition(0.53);
        upArm.setPosition(0.65);
        lowArm.setPosition(0.01);
        light.setPosition(0);
    }
    public void servosGoToPickup(double c, double u, double l) {
        cone.setPosition(c);
        upArm.setPosition(u);
        lowArm.setPosition(l);
    }
    public void operateClaw(int state) {
        popper.setPosition(0.13 + state * 0.22);
    }
}
