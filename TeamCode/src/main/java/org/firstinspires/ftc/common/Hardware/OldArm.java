package org.firstinspires.ftc.common.Hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class OldArm {
    private Servo lowArm, upArm, cone, popper, light;

    public OldArm(HardwareMap hardwareMap) {
        lowArm = hardwareMap.servo.get("rotator"); //0e
        upArm = hardwareMap.servo.get("extender"); //0
        cone = hardwareMap.servo.get("cone"); //2
        popper = hardwareMap.servo.get("popper"); //3
        light = hardwareMap.servo.get("release");//1e
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
