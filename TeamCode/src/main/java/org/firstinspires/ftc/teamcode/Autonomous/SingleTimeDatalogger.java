/*
Steps to use this single-time datalogger:
1. make a conceptdatalogger2
2. use addVariable to add the doubles or strings you want
3. use execute to finish and writeline to write a line or more
still under testing and review
*/
package org.firstinspires.ftc.teamcode.Autonomous;

import java.util.LinkedList;
import java.util.Queue;
public class SingleTimeDatalogger {
    private Datalogger.Builder builtlogger;
    private Datalogger datalogger;
    public LinkedList<Datalogger.GenericField> fields = new LinkedList<Datalogger.GenericField>();
    public SingleTimeDatalogger(String name) {
        builtlogger = new Datalogger.Builder()
                .setFilename(name)
                .setAutoTimestamp(Datalogger.AutoTimestamp.DECIMAL_SECONDS);
    }
    public void add(double var, int num) {
        Datalogger.GenericField field = new Datalogger.GenericField("Variable " + num);
        field.set(var);
        fields.offer(field);
    }
    public void add(String var, int num) {
        Datalogger.GenericField field = new Datalogger.GenericField("Variable " + num);
        field.set(var);
        fields.offer(field);
    }
    public void execute() {
        datalogger = builtlogger.setFields(fields.toArray(new Datalogger.GenericField[fields.size()])).build();
    }
    public void writeLine() {
        datalogger.writeLine();
    }
}


