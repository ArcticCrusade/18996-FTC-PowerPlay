package org.firstinspires.ftc.common.Config;

import java.util.ArrayList;

public class SpeedMovementConfig {
    private DimensionalMovementConfig X1;
    private DimensionalMovementConfig X2;
    private DimensionalMovementConfig Y1;
    private DimensionalMovementConfig Y2;
    private int index = 0;
    private String name;

    public DimensionalMovementConfig getConfig() {
        DimensionalMovementConfig[] configs = {X1, Y1, X2, Y2};
        return configs[index];
    }

    public DimensionalMovementConfig[] getConfigList() {
        DimensionalMovementConfig[] configs = {X1, Y1, X2, Y2};
        return configs;
    }

    public String getName() {
        return name;
    }

    public void changeConfig() {
        index = (index + 1) % 4;
    }

    public String getChanging() {
        DimensionalMovementConfig[] configs = {X1, Y1, X2, Y2};
        switch (index) {
            case 0: { return "X1"; }
            case 1: { return "Y1"; }
            case 2: { return "X2"; }
            case 3: { return "Y2"; }

        }
        return "getChanging() in SpeedMovementConfig Broken";
    }

    public SpeedMovementConfig(String newName) {
        X1 = new DimensionalMovementConfig(0.05, 0.2, 0.3, "linear", 2.1);
        X2 = new DimensionalMovementConfig(0.05, 0.2, 0.3, "linear", 2.1);
        Y1 = new DimensionalMovementConfig(0.05, 0.2, 0.3, "linear", 2.1);
        Y2 = new DimensionalMovementConfig(0.05, 0.2, 0.3, "linear", 2.1);
        name = newName;
    }

    public SpeedMovementConfig(String newName, ArrayList<ArrayList<Double>> inputList, ArrayList<String> types) {
        // this code is awful and disgusting but it works
        X1 = new DimensionalMovementConfig(inputList.get(0).get(0), inputList.get(0).get(1), inputList.get(0).get(2), types.get(0), inputList.get(0).get(3));
        X2 = new DimensionalMovementConfig(inputList.get(1).get(0), inputList.get(1).get(1), inputList.get(1).get(2), types.get(1), inputList.get(1).get(3));
        Y1 = new DimensionalMovementConfig(inputList.get(2).get(0), inputList.get(2).get(1), inputList.get(2).get(2), types.get(2), inputList.get(2).get(3));
        Y2 = new DimensionalMovementConfig(inputList.get(3).get(0), inputList.get(3).get(1), inputList.get(3).get(2), types.get(3), inputList.get(3).get(3));
        name = newName;
    }
}

