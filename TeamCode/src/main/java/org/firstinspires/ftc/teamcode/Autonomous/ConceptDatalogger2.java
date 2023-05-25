/*
Steps to use single-var concept datalogger:
1. make a conceptdatalogger2 with # of entries
2. use addVariable to add the doubles or strings you want
3. use execute to finish 
still under testing and review
*/
package org.firstinspires.ftc.teamcode.Autonomous;

import java.util.LinkedList;
import java.util.Queue;
public class ConceptDatalogger2 {
    private Datalogger.Builder builtlogger = new Datalogger.Builder();
    private Datalogger datalogger = builtlogger.build();
    private int numvar;
    public Queue<Datalogger.GenericField> fields;
    public ConceptDatalogger2(String name, int numVar) {
        fields = new LinkedList<Datalogger.GenericField>();
        builtlogger.setFilename(name);
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
        datalogger = builtlogger.setFields(fields.toArray(new Datalogger.GenericField[numvar])).build();
        datalogger.writeLine();
    }
}

