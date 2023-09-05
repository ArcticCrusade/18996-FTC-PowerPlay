package org.firstinspires.ftc.common.Hardware;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;

import org.firstinspires.ftc.common.Config.SpeedMovementConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DriveSubsystem extends SubsystemBase {
    private SpeedMovementConfig config;
    private List<SpeedMovementConfig> configs  = new ArrayList<SpeedMovementConfig>();
    private final MecanumDrive drive;
    private double strafe = 0;
    private double forward = 0;
    private double turn = 0;
    public DriveSubsystem(RobotHardware robot) {
        drive = new MecanumDrive(robot.leftFront, robot.rightFront, robot.leftRear, robot.rightRear);
    }

    public SpeedMovementConfig getConfig() { return config; }
    public MecanumDrive getDrive() { return drive; }
    public void createNewLinearConfig(String name, double minInput, double minSpeed, double maxSpeed) {
        config = new SpeedMovementConfig(name, Arrays.asList(minInput, minSpeed, maxSpeed, 1.0), "linear");
        configs.add(config);
    }
    public void createNewNonlinearConfig(String name, double minInput, double minSpeed, double maxSpeed, double base) {
        config = new SpeedMovementConfig(name, Arrays.asList(minInput, minSpeed, maxSpeed, base), "exponential");
        configs.add(config);
    }
    public void switchConfig(String name) {
        for (SpeedMovementConfig config : configs) {
            if (config.getName().equals(name)) {
                this.config = config;
            }
        }
    }
    public void calculatePower(double lx, double ly, double rx) {
        strafe = config.getConfigList()[0].calculateSpeed(lx);
        forward = config.getConfigList()[1].calculateSpeed(ly);
        turn = config.getConfigList()[2].calculateSpeed(rx);
    }
    @Override
    public void periodic() {
        drive.driveRobotCentric(strafe, forward, turn);
    }
}
