/* 
 * HOW TO USE
 * 
 * -----------
 * 
 * BUTTONS
 * A - CHANGES SPEED
 * B - CHANGES DIMENSIONAL MOVEMENT FOR SPEED
 * X - DOES NOTHING
 * Y - CHANGES CONFIG VALUE FOR DIMENSIONAL MOVEMENT FOR SPEED
 * 
 * DPAD UP - INCREMENT CONFIG
 * DPAD DOWN - DECREMENT CONFIG
 * 
 * RIGHT STICK - NON TURNING DRIVING
 * LEFT STICK - TURNING
 * 
 * NOTES:
 * Y2 IS USELESS 
 * CURRENTLY ONLY SUPPORTS LINEAR 
 * LOGGING WILL BE IMPLEMENTED SOON (hopefully)
 */



package org.firstinspires.ftc.teamcode.TeleOp.FinalPrograms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.common.Config.DimensionalMovementConfig;
import org.firstinspires.ftc.common.Config.SpeedMovementConfig;

@TeleOp(name="Driving Configuration", group="Linear Opmode")
public class DrivingConfig extends LinearOpMode {
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftRear;
    private DcMotor rightRear;
    private boolean aPressed, bPressed, xPressed, yPressed;
    private double LF, RF, LR, RR, rightY, rightX, leftY, leftX;
    private int index = 0;

    @Override
    public void runOpMode() {
        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightRear = hardwareMap.dcMotor.get("rightRear");
        SpeedMovementConfig slow = new SpeedMovementConfig("Slow");
        SpeedMovementConfig normal = new SpeedMovementConfig("Normal");
        SpeedMovementConfig fast = new SpeedMovementConfig("Fast");
        SpeedMovementConfig[] speeds = {slow, normal, fast};

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.a) {
                if (!aPressed) {
                    index = (index + 1) % 3;
                } 
                aPressed = true;
            } else {
                aPressed = false;
            }

            if (gamepad1.b) {
                if (!bPressed) {
                    speeds[index].changeConfig();
                }
                bPressed = true;
            } else {
                bPressed = false;
            }

            if (gamepad1.y) {
                if (!yPressed) {
                    speeds[index].getConfig().changeMode();
                }
                yPressed = true;
            } else {
                yPressed = false;
            }

            if (gamepad1.dpad_up) {
                speeds[index].getConfig().changeVal(0.01);
                sleep(200);
            }

            if (gamepad1.dpad_down) {
                speeds[index].getConfig().changeVal(-0.01);
                sleep(200);
            }

            SpeedMovementConfig currentSpeed = speeds[index];

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
            LF = -(X1 - Y1 + X2);
            RF = -(Y1 + X1 + X2);
            LR = -(X2 - X1 - Y1);
            RR = -Y1 + X1 - X2;

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
