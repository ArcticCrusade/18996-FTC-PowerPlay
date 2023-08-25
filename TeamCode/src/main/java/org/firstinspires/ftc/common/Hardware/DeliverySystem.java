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



    @Override
    public void initialize(LinearOpMode opMode) {
        lift = new Lift();
        grabber = new Grabber();
        fourBar = new FourBar();
    }

    // DO NOT CHANGE THE ORDER OF THESE COMMANDS - WILL (probably) BREAK IF YOU DO
    public void intake() throws InterruptedException {
        fourBar.setPickUpPosition();
        lift.setLow();
        grabber.grab();
    }

    public void dropHigh() throws InterruptedException {
        fourBar.setHigh();
        lift.setHigh();
        grabber.release();
    }
}
