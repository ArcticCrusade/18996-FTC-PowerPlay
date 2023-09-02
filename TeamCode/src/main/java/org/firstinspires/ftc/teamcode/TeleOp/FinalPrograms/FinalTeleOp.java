/**
 * UNUSABLE
 * DO NOT USE WITHOUT FURTHER TRANSITIONING TO LIB
 *//**
 * UNUSABLE
 * DO NOT USE WITHOUT FURTHER TRANSITIONING TO LIB
 *//**
 * UNUSABLE
 * DO NOT USE WITHOUT FURTHER TRANSITIONING TO LIB
 *//**
 * UNUSABLE
 * DO NOT USE WITHOUT FURTHER TRANSITIONING TO LIB
 *//**
 * UNUSABLE
 * DO NOT USE WITHOUT FURTHER TRANSITIONING TO LIB
 *//**
 * UNUSABLE
 * DO NOT USE WITHOUT FURTHER TRANSITIONING TO LIB
 */
package org.firstinspires.ftc.teamcode.TeleOp.FinalPrograms;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.ButtonReader;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.common.Config.DimensionalMovementConfig;
import org.firstinspires.ftc.common.Config.SpeedMovementConfig;
import static org.firstinspires.ftc.common.Config.FinalDriveConfig.getTypes;
import static org.firstinspires.ftc.common.Config.FinalDriveConfig.populateArray;
import org.firstinspires.ftc.common.Hardware.DeliverySystem;
import org.firstinspires.ftc.common.Hardware.RobotHardware;

@TeleOp(name="Final TeleOp", group="Linear Opmode")
public class FinalTeleOp extends LinearOpMode {
    DcMotor leftFront;
    DcMotor rightFront;
    GamepadEx driver = new GamepadEx(gamepad1);
    DcMotor leftRear;
    boolean aPressed, bPressed, xPressed, yPressed;
    DcMotor rightRear;
    double LF, RF, LR, RR, rightY, rightX, leftY, leftX;
    RobotHardware robot = RobotHardware.getInstance();

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap, RobotHardware.OpModes.TELEOP);
        DeliverySystem deliverySystem = new DeliverySystem(robot);
        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightRear = hardwareMap.dcMotor.get("rightRear");
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        SpeedMovementConfig slow = new SpeedMovementConfig("Slow", populateArray("Slow"), getTypes("Slow"));
        SpeedMovementConfig normal = new SpeedMovementConfig("Normal", populateArray("Normal"), getTypes("Normal"));
        SpeedMovementConfig fast = new SpeedMovementConfig("Fast", populateArray("Fast"), getTypes("Fast"));
        SpeedMovementConfig[] speeds = {slow, normal, fast};

        Button buttonA = new GamepadButton(
                driver, GamepadKeys.Button.A
        ).whenPressed(new InstantCommand(() -> {
            try {
                deliverySystem.intake();
            } catch (InterruptedException e) {}
        }));

        Button buttonY = new GamepadButton(
                driver, GamepadKeys.Button.Y
        ).whenPressed(new InstantCommand(() -> {
            try {
                deliverySystem.dropHigh();
            } catch (InterruptedException e) {}
        }));



        waitForStart();

        while (opModeIsActive()) {
            int index = 1;
            SpeedMovementConfig currentSpeed = speeds[(gamepad1.right_trigger > 0.3) ? 2 : ((gamepad1.left_trigger > 0.3) ? 0 : 1)];

            LF = 0; RF = 0; LR = 0; RR = 0;


            // Get joystick values
            rightY = -gamepad1.right_stick_y;
            rightX = gamepad1.right_stick_x;
            leftY = -gamepad1.left_stick_y;
            leftX = gamepad1.left_stick_x;

            DimensionalMovementConfig[] configs = currentSpeed.getConfigList();
            double X1 = configs[0].calculateSpeed(rightX);
            double Y1 = configs[1].calculateSpeed(rightY);
            double X2 = configs[2].calculateSpeed(leftX);
            double Y2 = configs[3].calculateSpeed(leftY);

            //calculate motor output from joysticks
            LF = -X1 - Y1 - X2;
            RF = Y1 - X1 - X2;
            LR = -X2 + X1 - Y1;
            RR = Y1 + X1 - X2;

            //set motor commands
            leftFront.setPower(LF);
            rightFront.setPower(RF);
            leftRear.setPower(LR);
            rightRear.setPower(RR);
        }
    }
}

