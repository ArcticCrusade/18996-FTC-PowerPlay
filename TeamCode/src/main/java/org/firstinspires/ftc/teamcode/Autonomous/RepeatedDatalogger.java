/*
Steps to use this repeatable datalogger:
1. make a repeated datalogger
2. use addVariable to add the doubles or strings you want out of while and build
3. use modify then writeline in while to write a line or more
still under testing and review
*/
package org.firstinspires.ftc.teamcode.Autonomous;

import java.util.LinkedList;

public class RepeatedDatalogger {
    private Datalogger.Builder builtlogger;
    private Datalogger datalogger;
    public LinkedList<Datalogger.GenericField> fields = new LinkedList<Datalogger.GenericField>();
    public RepeatedDatalogger(String name) {
        builtlogger = new Datalogger.Builder()
                .setFilename(name)
                .setAutoTimestamp(Datalogger.AutoTimestamp.DECIMAL_SECONDS);
    }
    public void add(int num) {
        Datalogger.GenericField field = new Datalogger.GenericField("Variable " + num);
        fields.offer(field);
    }
    public void modify(String name, double var) {
        for (Datalogger.GenericField field: fields) {
            if (field.getName().equals(name)) {
                field.set(var);
            }
        }
    }
    public void modify(String name, String var) {
        for (Datalogger.GenericField field: fields) {
            if (field.getName().equals(name)) {
                field.set(var);
            }
        }
    }
    public void build() {
        datalogger = builtlogger.setFields(fields.toArray(new Datalogger.GenericField[fields.size()])).build();
    }
    public void writeLine() {
        datalogger.writeLine();
    }
}



