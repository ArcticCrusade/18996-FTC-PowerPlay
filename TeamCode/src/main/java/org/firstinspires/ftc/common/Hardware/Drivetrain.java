//incompatible with any rr stuff
/**
 * TEMPOARILY OBSOLETED
 */
/**
 * TEMPOARILY OBSOLETED
 */
/**
 * TEMPOARILY OBSOLETED
 */
/**
 * TEMPOARILY OBSOLETED
 */

package org.firstinspires.ftc.common.Hardware;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.common.Config.SpeedMovementConfig;

import java.util.Arrays;
import java.util.List;

public class Drivetrain extends SubsystemBase {
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftRear;
    private DcMotor rightRear;
    private List<DcMotor> motors;
    private List<SpeedMovementConfig> configs;
    private SpeedMovementConfig config;

    public void initialize(LinearOpMode opMode) {
        leftFront = opMode.hardwareMap.dcMotor.get("leftFront");
        rightFront = opMode.hardwareMap.dcMotor.get("rightFront");
        leftRear = opMode.hardwareMap.dcMotor.get("leftRear");
        rightRear = opMode.hardwareMap.dcMotor.get("rightRear");
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        leftRear.setDirection(DcMotor.Direction.REVERSE);
        rightRear.setDirection(DcMotor.Direction.FORWARD);
        motors = Arrays.asList(leftFront, rightFront, leftRear, rightRear);
        for (DcMotor motor : motors) {
           motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
           motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
    }

    public SpeedMovementConfig getConfig() { return config; }
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
    public void calculateAndSetPower(double leftX, double leftY, double rightX, double rightY) {
        double x1 = config.getConfigList()[0].calculateSpeed(rightX);
        double y1 = config.getConfigList()[1].calculateSpeed(rightY);
        double x2 = config.getConfigList()[2].calculateSpeed(leftX);
        //double y2 = config.getConfigList()[3].calculateSpeed(leftY);
        leftFront.setPower(y1 + x1 + x2);
        rightFront.setPower(y1 - x1 - x2);
        leftRear.setPower(y1 - x1 + x2);
        rightRear.setPower(y1 + x1 - x2);
    }
}
