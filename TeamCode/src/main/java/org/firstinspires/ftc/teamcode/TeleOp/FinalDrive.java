package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.common.Config.*;

import java.util.ArrayList;


@TeleOp(name="Final Drive", group="Linear Opmode")
public class FinalDrive extends LinearOpMode {
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftRear;
    private boolean aPressed, bPressed, xPressed, yPressed;
    private DcMotor rightRear;
    private double LF, RF, LR, RR, rightY, rightX, leftY, leftX;

    @Override
    public void runOpMode() {
        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightRear = hardwareMap.dcMotor.get("rightRear");
        ArrayList<ArrayList<Double>> slowConfig = new ArrayList<ArrayList<Double>>();
        ArrayList<ArrayList<Double>> normalConfig = new ArrayList<ArrayList<Double>>();
        ArrayList<ArrayList<Double>> fastConfig = new ArrayList<ArrayList<Double>>();
        SpeedMovementConfig slow = new SpeedMovementConfig("Slow", FinalDriveConfig.populateArray("Slow"));
        SpeedMovementConfig normal = new SpeedMovementConfig("Normal", FinalDriveConfig.populateArray("Normal"));
        SpeedMovementConfig fast = new SpeedMovementConfig("Fast", FinalDriveConfig.populateArray("Fast"));
        SpeedMovementConfig[] speeds = {slow, normal, fast};

        waitForStart();

        while (opModeIsActive()) {

            SpeedMovementConfig currentSpeed = speeds[gamepad1.right_bumper ? 2 : (gamepad1.left_bumper ? 0 : 1)];

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
            LF = -(Y1 + X1 + X2);
            RF = Y1 - X1 - X2;
            LR = -(Y1 - X1 + X2);
            RR = Y1 + X1 - X2;

            //set motor commands
            leftFront.setPower(LF);
            rightFront.setPower(RF);
            leftRear.setPower(LR);
            rightRear.setPower(RR);


            telemetry.addLine("Currently Modifying: " + currentSpeed.getName());
            telemetry.addLine("Currently Modifying: " + currentSpeed.getChanging());
            telemetry.addLine(currentSpeed.getConfig().getCurrentlyChanging());
            telemetry.addData("Value:", currentSpeed.getConfig().currentValue());
            telemetry.update();
        }
    }
}
