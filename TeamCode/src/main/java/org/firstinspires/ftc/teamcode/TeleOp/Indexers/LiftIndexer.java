package org.firstinspires.ftc.teamcode.TeleOp.Indexers;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.common.Hardware.Lift;
import org.firstinspires.ftc.common.Hardware.Lift.Positions;
import com.arcrobotics.ftclib.command.CommandOpMode;

@TeleOp(name="lift indexer", group="Linear OpMode")
public class LiftIndexer extends CommandOpMode {
    Lift lift;
    Positions currentState = Positions.LOW;
    boolean upPressed = false;
    boolean rightPressed = false;
    boolean leftPressed = false;
    boolean downPressed = false;
    int stepSize = 400;
    @Override
    public void initialize() {
        lift = new Lift(this);
        CommandScheduler.getInstance().reset();
        register(lift);

    }
    @Override
    public void run() {

    }
    @Override
    public void runOpMode() throws InterruptedException {
        lift = new Lift(this);
        waitForStart();
        int count = 0;
        while (opModeIsActive()) {
            if (gamepad1.dpad_up) {
                if (!upPressed) {
                    lift.overrideValue(currentState, lift.getStateValue(currentState) + stepSize);
                }
                upPressed = true;
            } else {
                upPressed = false;
            }

            if (gamepad1.dpad_down) {
                if (!downPressed) {
                    lift.overrideValue(currentState, lift.getStateValue(currentState) - stepSize);
                }
                downPressed = true;
            } else {
                downPressed = false;
            }

            if (gamepad1.dpad_left) {
                if (!leftPressed) {
                    stepSize -= 50;
                    leftPressed = true;
                }
            } else {
                leftPressed = false;
            }

            if (gamepad1.dpad_right) {
                if (!rightPressed) {
                    stepSize += 50;
                    rightPressed = true;
                }
            } else {
                rightPressed = false;
            }

            if (gamepad1.a) {
                currentState = Positions.LOW;
            }
            if (gamepad1.b) {
                currentState = Positions.MEDIUM;
            }
            if (gamepad1.y) {
                currentState = Positions.HIGH;
            }
            if (gamepad1.x) {
                switch (currentState) {
                    case LOW:
                        lift.setLow();
                        break;
                    case MEDIUM:
                        lift.setMedium();
                        break;
                    case HIGH:
                        lift.setHigh();
                        break;
                }
            }

            telemetry.addData("Currently Adjusting:", currentState.toString());
            telemetry.addData("Current Encoder Value:", lift.getStateValue(currentState));
            telemetry.addData("Current Step Size: ", stepSize);
            telemetry.update();
        }
    }
}
