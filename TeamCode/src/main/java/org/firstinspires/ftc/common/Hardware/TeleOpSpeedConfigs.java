package org.firstinspires.ftc.common.Hardware;

public class TeleOpSpeedConfigs {
    private int mode = 0;
    // Slow, Normal, Fast
    TeleOpStateConfig states[] = {new TeleOpStateConfig(true), new TeleOpStateConfig(false), new TeleOpStateConfig(false)};

    public void changeMode() {
        mode = (mode + 1) % 3;
    }

    public TeleOpStateConfig getConfig() {
        return states[mode];
    }

    public String getState() {
        switch (mode) {
            case 0:
                return "Slow";
            case 1:
                return "Normal";
            case 2:
                return "Fast";
        }
        return "Exit Program";
    }
}
