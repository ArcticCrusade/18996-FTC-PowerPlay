package org.firstinspires.ftc.teamcode.TeleOp.Indexers;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.common.Hardware.Lift;

@TeleOp(name="lift indexer", group="Linear OpMode")
public class LiftIndexer extends LinearOpMode {
    enum State {
        low,
        medium,
        high
    }


    Lift lift;
    State currentState = State.low;
    boolean upPressed = false;
    boolean rightPressed = false;
    boolean leftPressed = false;
    boolean downPressed = false;
    int stepSize = 400;
    @Override
    public void runOpMode() throws InterruptedException {
        lift = new Lift(this);
        waitForStart();
        int count = 0;
        while (opModeIsActive()) {
            if (gamepad1.dpad_up) {
                if (!upPressed) {
                    lift.overrideValue(currentState.toString(), lift.getStateValue(currentState.toString()) + stepSize);
                }
                upPressed = true;
            } else {
                upPressed = false;
            }

            if (gamepad1.dpad_down) {
                if (!downPressed) {
                    lift.overrideValue(currentState.toString(), lift.getStateValue(currentState.toString()) - stepSize);
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
                currentState = State.low;
            }
            if (gamepad1.b) {
                currentState = State.medium;
            }
            if (gamepad1.y) {
                currentState = State.high;
            }
            if (gamepad1.x) {
                switch (currentState) {
                    case low:
                        lift.setLow();
                        break;
                    case medium:
                        lift.setMedium();
                        break;
                    case high:
                        lift.setHigh();
                        break;
                }
            }

            telemetry.addData("Currently Adjusting:", currentState.toString());
            telemetry.addData("Current Encoder Value:", lift.getStateValue(currentState.toString()));
            telemetry.addData("Current Step Size: ", stepSize);
            telemetry.update();
        }
    }
}
