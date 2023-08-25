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


    Lift lift = new Lift();
    State currentState = State.low;
    boolean upPressed = false;
    boolean rightPressed = false;
    boolean leftPressed = false;
    boolean downPressed = false;
    int stepSize = 3;
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
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
                upPressed = true;
            } else {
                upPressed = false;
            }

            if (gamepad1.dpad_left) {
                if (!leftPressed) {
                    stepSize -= 1;
                    leftPressed = true;
                }
            } else {
                leftPressed = false;
            }

            if (gamepad1.dpad_right) {
                if (!rightPressed) {
                    stepSize += 1;
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

            telemetry.addData("Currently Adjusting:", currentState.toString());
            telemetry.addData("Current Value:", lift.getStateValue(currentState.toString()));
            telemetry.update();
        }
    }
}
