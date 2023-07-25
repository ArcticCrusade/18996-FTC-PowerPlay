package org.firstinspires.ftc.common.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.common.Interfaces.Subsystem;

public class Lift implements Subsystem {
    DcMotor motorLift;

    @Override
    public void initialize(LinearOpMode opMode) {
        motorLift = opMode.hardwareMap.dcMotor.get("lift");
    }
}
