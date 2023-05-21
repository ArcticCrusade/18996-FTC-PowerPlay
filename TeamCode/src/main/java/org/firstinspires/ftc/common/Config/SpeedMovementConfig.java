package org.firstinspires.ftc.common.Config;

public class SpeedMovementConfig {
    private DimensionalMovementConfig X1;
    private DimensionalMovementConfig X2;
    private DimensionalMovementConfig Y1;
    private DimensionalMovementConfig Y2;
    private int index = 0;

    public DimensionalMovementConfig getConfig() {
        DimensionalMovementConfig[] configs = {X1, Y1, X2, Y2};
        return configs[index];
    }

    public DimensionalMovementConfig[] getConfigList() {
        DimensionalMovementConfig[] configs = {X1, Y1, X2, Y2};
        return configs;
    }

    public void changeConfig() {
        index = (index + 1) % 4;
    }

    public String getName() {
        switch (index) {
            case 0: {
                return "X1";
            }
            case 1: {
                return "Y1";
            }
            case 2: {
                return "X2";
            }
            case 3: {
                return "Y2";
            }
        }
        return "broken - contact chris, i don't even know how this would happen";
    }

    public SpeedMovementConfig() {
        X1 = new DimensionalMovementConfig(0.0, 0.2, 0.3, "linear", 2.1);
        X2 = new DimensionalMovementConfig(0.0, 0.2, 0.3, "linear", 2.1);
        Y1 = new DimensionalMovementConfig(0.0, 0.2, 0.3, "linear", 2.1);
        Y2 = new DimensionalMovementConfig(0.0, 0.2, 0.3, "linear", 2.1);
    }
}

