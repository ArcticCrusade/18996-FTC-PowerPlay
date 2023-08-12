package org.firstinspires.ftc.common.Config;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class FinalDriveConfig {
    static double slowX1MinInput = 0.05;
    static double slowX2MinInput = 0.05;
    static double slowY1MinInput = 0.05;
    static double slowY2MinInput = 0.05;

    static double slowX1MinSpeed = 0;
    static double slowX2MinSpeed = 0;
    static double slowY1MinSpeed = 0;
    static double slowY2MinSpeed = 0;

    static double slowX1MaxSpeed = 1;
    static double slowX2MaxSpeed = 1;
    static double slowY1MaxSpeed = 1;
    static double slowY2MaxSpeed = 1;

    static String slowX1type = "linear";
    static String slowX2type = "linear";
    static String slowY1type = "linear";
    static String slowY2type = "linear";

    static double slowX1Base = 2.1;
    static double slowX2Base = 2.1;
    static double slowY1Base = 2.1;
    static double slowY2Base = 2.1;

    static double medX1MinInput = 0.05;
    static double medX2MinInput = 0.05;
    static double medY1MinInput = 0.05;
    static double medY2MinInput = 0.05;

    static double medX1MinSpeed = 0.3;
    static double medX2MinSpeed = 0.3;
    static double medY1MinSpeed = 0.3;
    static double medY2MinSpeed = 0.3;

    static double medX1MaxSpeed = 0.5;
    static double medX2MaxSpeed = 0.5;
    static double medY1MaxSpeed = 0.5;
    static double medY2MaxSpeed = 0.5;

    static String medX1type = "linear";
    static String medX2type = "linear";
    static String medY1type = "linear";
    static String medY2type = "linear";

    static double medX1Base = 2.1;
    static double medX2Base = 2.1;
    static double medY1Base = 2.1;
    static double medY2Base = 2.1;

    static double fastX1MinInput = 0.05;
    static double fastX2MinInput = 0.05;
    static double fastY1MinInput = 0.05;
    static double fastY2MinInput = 0.05;

    static double fastX1MinSpeed = 0.6;
    static double fastX2MinSpeed = 0.6;
    static double fastY1MinSpeed = 0.6;
    static double fastY2MinSpeed = 0.6;

    static double fastX1MaxSpeed = 0.8;
    static double fastX2MaxSpeed = 0.8;
    static double fastY1MaxSpeed = 0.8;
    static double fastY2MaxSpeed = 0.8;

    static String fastX1type = "linear";
    static String fastX2type = "linear";
    static String fastY1type = "linear";
    static String fastY2type = "linear";

    static double fastX1Base = 2.1;
    static double fastX2Base = 2.1;
    static double fastY1Base = 2.1;
    static double fastY2Base = 2.1;

    public static ArrayList<ArrayList<Double>> populateArray(String speed) {
        ArrayList<ArrayList<Double>> arr = new ArrayList<>();
        switch (speed) {
            case "Slow": {
                ArrayList<Double> X1 = new ArrayList<>();
                X1.add(slowX1MinInput);
                X1.add(slowX1MinSpeed);
                X1.add(slowX1MaxSpeed);
                X1.add(slowX1Base);
                ArrayList<Double> Y1 = new ArrayList<>();
                Y1.add(slowY1MinInput);
                Y1.add(slowY1MinSpeed);
                Y1.add(slowY1MaxSpeed);
                Y1.add(slowY1Base);
                ArrayList<Double> X2 = new ArrayList<>();
                X2.add(slowX2MinInput);
                X2.add(slowX2MinSpeed);
                X2.add(slowX2MaxSpeed);
                X2.add(slowX2Base);
                ArrayList<Double> Y2 = new ArrayList<>();
                Y2.add(slowY2MinInput);
                Y2.add(slowY2MinSpeed);
                Y2.add(slowY2MaxSpeed);
                Y2.add(slowY2Base);
                arr.add(X1);
                arr.add(X2);
                arr.add(Y1);
                arr.add(Y2);
            }
            case "Normal": {
                ArrayList<Double> X1 = new ArrayList<>();
                X1.add(medX1MinInput);
                X1.add(medX1MinSpeed);
                X1.add(medX1MaxSpeed);
                X1.add(medX1Base);
                ArrayList<Double> Y1 = new ArrayList<>();
                Y1.add(medY1MinInput);
                Y1.add(medY1MinSpeed);
                Y1.add(medY1MaxSpeed);
                Y1.add(medY1Base);
                ArrayList<Double> X2 = new ArrayList<>();
                X2.add(medX2MinInput);
                X2.add(medX2MinSpeed);
                X2.add(medX2MaxSpeed);
                X2.add(medX2Base);
                ArrayList<Double> Y2 = new ArrayList<>();
                Y2.add(medY2MinInput);
                Y2.add(medY2MinSpeed);
                Y2.add(medY2MaxSpeed);
                Y2.add(medY2Base);
                arr.add(X1);
                arr.add(X2);
                arr.add(Y1);
                arr.add(Y2);
            }
            case "Fast": {
                ArrayList<Double> X1 = new ArrayList<>();
                X1.add(fastX1MinInput);
                X1.add(fastX1MinSpeed);
                X1.add(fastX1MaxSpeed);
                X1.add(fastX1Base);
                ArrayList<Double> Y1 = new ArrayList<>();
                Y1.add(fastY1MinInput);
                Y1.add(fastY1MinSpeed);
                Y1.add(fastY1MaxSpeed);
                Y1.add(fastY1Base);
                ArrayList<Double> X2 = new ArrayList<>();
                X2.add(fastX2MinInput);
                X2.add(fastX2MinSpeed);
                X2.add(fastX2MaxSpeed);
                X2.add(fastX2Base);
                ArrayList<Double> Y2 = new ArrayList<>();
                Y2.add(fastY2MinInput);
                Y2.add(fastY2MinSpeed);
                Y2.add(fastY2MaxSpeed);
                Y2.add(fastY2Base);
                arr.add(X1);
                arr.add(X2);
                arr.add(Y1);
                arr.add(Y2);
            }
        }
        return arr;
    }

    public static ArrayList<String> getTypes(String speed) {
        ArrayList<String> arr = new ArrayList<>();
        switch (speed) {
            case "Slow": {
                arr.add(slowX1type);
                arr.add(slowX2type);
                arr.add(slowY1type);
                arr.add(slowY2type);
            }
            case "Normal": {
                arr.add(medX1type);
                arr.add(medX2type);
                arr.add(medY1type);
                arr.add(medY2type);
            }
            case "Fast": {
                arr.add(fastX1type);
                arr.add(fastX2type);
                arr.add(fastY1type);
                arr.add(fastY2type);
            }
        }
        return arr;
    }
}
