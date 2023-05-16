package org.firstinspires.ftc.common.Hardware;

import java.lang.Math;

public class TeleOpStateConfig {
    private int index;
    private boolean logarithmic;
    private double[] configValues = {0.0, 1.0, 1.0,  0.0};
    //                               minS maxS turnS minInput
    public TeleOpStateConfig(boolean log) {
        logarithmic = log;
    }
    private State currentState = State.minSpeed;
    private enum State {
        minSpeed,
        topSpeed,
        turnSpeed,
        minInput,
    };

    public String getState() {
        switch (currentState) {
            case minSpeed:
                return "Currently Modifying Min Speed";
            case topSpeed:
                return "Currently Modifying Max Speed";
            case turnSpeed:
                return "Currently Modifying Turn Speed";
            case minInput:
                return "Currently Modifying Min Input for Movement";
        }
        return "Shut Off Immediately";
    }

    public void changeState() {
        index = (index + 1) % 4;
        switch (currentState) {
            case minSpeed:
                currentState = State.topSpeed;
                break;
            case topSpeed:
                currentState = State.turnSpeed;
                break;
            case turnSpeed:
                currentState = State.minInput;
                break;
            case minInput:
                currentState = State.minSpeed;
                break;
        }
    }

    public void changeValue(double value) {
        configValues[index] += value;
    }

    public double[] getConfigValues() {
        return configValues;
    }

    public double calculatePower(double unfilteredInput) {
        if (unfilteredInput < configValues[3]) {
            return 0.0;
        }
        double input = unfilteredInput / (1 - configValues[0]);
        if (logarithmic) {
            double val = Math.pow(1.5, configValues[1] - configValues[0]);
            return configValues[0] + (Math.log(input * val) / Math.log(1.5)); // Change of base
        }
        double m = configValues[1] - configValues[0];
        return (m * input) + configValues[0];
    }

    public boolean isLogarithmic() {
        return isLogarithmic();
    }

    public double getTurnPower() {
        return configValues[3];
    }
}
