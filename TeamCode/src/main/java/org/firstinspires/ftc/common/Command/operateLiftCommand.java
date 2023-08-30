package org.firstinspires.ftc.common.Command;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.common.Hardware.Lift;

public class operateLiftCommand extends CommandBase {
    Lift lift;

    public operateLiftCommand(Lift lift) {
        this.lift = lift;
        addRequirements(this.lift); //idk what this does
    }

    @Override
    public void initialize() {}



}
