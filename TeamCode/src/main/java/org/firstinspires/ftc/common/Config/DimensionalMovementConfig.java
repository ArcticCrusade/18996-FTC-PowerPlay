package org.firstinspires.ftc.common.Config;

public class DimensionalMovementConfig {
    private double minInput;
    private double minSpeed;
    private double maxSpeed;
    private double base;
    private functionType type;
    private String currentlyChanging;
    private enum functionType {
        linear,
        exponential,
        logarithmic,
    };

    public double calculateSpeed(double input) { // Some of this code might be scuffed
        int negative = input < 0 ? -1 : 1; 
        input = Math.abs(input);
        if (minInput > input) return 0.0;
        double processedInput = (input - minInput) / (1 - minInput);
        switch (type) {
            case linear: {
                double slope = maxSpeed - minSpeed;
                return negative * ((processedInput * slope) + minSpeed);
            }
            case exponential: {
                double exponent = Math.log(maxSpeed - minSpeed) / Math.log(base);
                return negative * (minSpeed + Math.pow(base, exponent * processedInput));
            }
            case logarithmic: {
                double val = Math.pow(base, maxSpeed - minSpeed);
                return negative * (minSpeed + (Math.log(val) / Math.log(base)));
            }
        }
        return 0.0;
    }

    public void changeVal(double change) {
        switch (currentlyChanging) {
            case "minInput": {
                minInput += change;
                break;
            }
            case "minSpeed": {
                minSpeed += change;
                break;
            }
            case "maxSpeed": {
                maxSpeed += change;
                break;
            }
        }
    }

    public void changeMode() {
        switch (currentlyChanging) {
            case "minInput": {
                currentlyChanging = "minSpeed";
                break;
            }
            case "minSpeed": {
                currentlyChanging = "maxSpeed";
                break;
            }
            case "maxSpeed": {
                currentlyChanging = "minInput";
                break;
            }
        }
    }



    public String getCurrentlyChanging() {
        return currentlyChanging;
    }

    public double currentValue() {
        switch (currentlyChanging) {
            case "minInput": {
                return minInput;
            }
            case "minSpeed": {
                return minSpeed;
            }
            case "maxSpeed": {
                return maxSpeed;
            }
        }
        return 1248.4124;
    }

    public DimensionalMovementConfig(double minInputVal, double minSpeedVal, double maxSpeedVal, String typeVal, double baseVal) {
        minInput = minInputVal;
        minSpeed = minSpeedVal;
        maxSpeed = maxSpeedVal;
        currentlyChanging = "minInput";
        type = functionType.valueOf(typeVal);
        if (type == null) throw new NullPointerException("contact chris - no type for dimensional movement in his dumbass code");
        base = baseVal;
    }
}
