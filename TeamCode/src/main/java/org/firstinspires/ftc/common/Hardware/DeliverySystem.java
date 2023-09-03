package org.firstinspires.ftc.common.Hardware;

import com.arcrobotics.ftclib.command.SubsystemBase;

public class DeliverySystem extends SubsystemBase {
    LiftSubsystem lift;
    GrabberSubsystem grabber;
    FourBarSubsystem fourBar;
    RobotHardware robot;

    public DeliverySystem(RobotHardware robot) {
        this.robot = robot;
        lift = new LiftSubsystem(robot);
        grabber = new GrabberSubsystem(robot);
        fourBar = new FourBarSubsystem(robot);
    }

    // DO NOT CHANGE THE ORDER OF THESE COMMANDS - WILL (probably) BREAK IF YOU DO
    public void intake() {
        fourBar.setPickUpPosition();
        lift.setLow();
        grabber.grab();
    }

    public void dropHigh() {
        fourBar.setHigh();
        lift.setHigh();
        grabber.release();
    }
    @Override
    public void periodic() {
        fourBar.periodic();
        lift.periodic();
        grabber.periodic();
    }
    public FourBarSubsystem getFourBar() { return fourBar;}
    public LiftSubsystem getLift() { return lift;}
    public GrabberSubsystem getGrabber() { return grabber;}
}
