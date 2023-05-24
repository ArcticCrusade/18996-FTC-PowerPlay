/*
Steps to use single-var concept datalogger:
1. make a conceptdatalogger2 with # of entries
2. use addVariable to add the doubles or strings you want
3. use execute to finish 
still under testing and review
*/
package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@TeleOp(name = "Concept Datalogger Single-Var", group = "Datalogging")
public class ConceptDatalogger2 {
    Datalog datalog;

    public ConceptDatalogger2(int numvar) {
        // Initialize the datalog
        datalog = new Datalog("s_datalog_01", numvar);
    }
    public void addVariable(double var, int num) {
        datalog.add(var, num);
    }
    public void addVariable(String var, int num) {
        datalog.add(var, num);
    }
    public void execute() {
        datalog.finish();
        datalog.writeLine();
    }

    public static class Datalog {
        private final Datalogger datalogger;
        public Datalogger.GenericField[] fields;

        public Datalogger.GenericField f = new Datalogger.GenericField("Variable");

        public Datalog(String name, double numvar)
        {
            fields = new Datalogger.GenericField[numVar];
            datalogger = new Datalogger.Builder()
                    .setFilename(name)
                    .setFields(fields);
        }
        public void add(double var, int num) {
            Datalogger.GenericField field = new Datalogger.GenericField("Variable " + num);
            this.field.set(var);
            fields.add(field);
        }
        public void add(String var, int num) {
            Datalogger.GenericField field = new Datalogger.GenericField("Variable " + num);
            this.field.set(var);
            fields.add(field);
        }
        public void writeLine() {
            datalogger.writeLine();
        }
        public void finished() {
            datalogger.build();
        }
    }
}

