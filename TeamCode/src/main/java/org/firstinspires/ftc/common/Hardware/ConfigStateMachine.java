package org.firstinspires.ftc.common.Hardware;

import org.firstinspires.ftc.teamcode.TeleOp.DrivingConfig;

public class ConfigStateMachine {
    private static int index;
    private enum State {
        minSpeed,
        topSpeed,
        brakeAmount,
        speedMode,
        slowMode,
    };
    public static void incrementState(State currentState) {
        switch (currentState) {
            case minSpeed:
                currentState = State.topSpeed;
                index = 1;
                break;
            case topSpeed:
                currentState = State.brakeAmount;

                break;
            case brakeAmount:
                currentState = State.speedMode;
                break;
            case speedMode:
                currentState = State.minSpeed;
                break;
        }
    }

    double[] configValues = {0.0, 1.0, 0.5, 2.0};
}
