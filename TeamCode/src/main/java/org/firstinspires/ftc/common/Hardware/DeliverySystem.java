package org.firstinspires.ftc.common.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.common.Hardware.FourBar;
import org.firstinspires.ftc.common.Hardware.Lift;
import org.firstinspires.ftc.common.Hardware.Grabber;
import org.firstinspires.ftc.common.Interfaces.Subsystem;

public class DeliverySystem implements Subsystem {
    Lift lift;
    Grabber grabber;
    FourBar fourBar;
    enum State {

    }

    @Override
    public void initialize(LinearOpMode opMode) {
        lift = new Lift();
        grabber = new Grabber();
        fourBar = new FourBar();
    }

    public void intake() throws InterruptedException {
        // lift.setLow()
        fourBar.setPickUpPosition();
        grabber.grab();
    }

    public void dropHigh() throws InterruptedException {
        // lift.setHigh()
        fourBar.setHigh();
        grabber.release();
    }
}
