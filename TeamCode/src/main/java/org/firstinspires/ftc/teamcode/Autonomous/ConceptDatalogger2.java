package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@TeleOp(name = "Concept Datalogger Single-Var", group = "Datalogging")
public class ConceptDatalogger2 {
    Datalog datalog;

    public ConceptDatalogger2() {
        // Initialize the datalog
        datalog = new Datalog("datalog_02");

        datalog.opModeStatus.set(5);
        datalog.writeLine();
    }

    public static class Datalog {
        private final Datalogger datalogger;

        public Datalogger.GenericField opModeStatus = new Datalogger.GenericField("OpModeStatus");

        public Datalog(String name)
        {
            // Build the underlying datalog object
            datalogger = new Datalogger.Builder().setFilename(name)
                    .setAutoTimestamp(Datalogger.AutoTimestamp.DECIMAL_SECONDS)
                    .setFields(
                            opModeStatus
                    )
                    .build();
        }

        public void writeLine()
        {
            datalogger.writeLine();
        }
    }
}

