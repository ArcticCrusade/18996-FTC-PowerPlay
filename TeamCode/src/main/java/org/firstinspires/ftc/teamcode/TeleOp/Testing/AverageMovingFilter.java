package org.firstinspires.ftc.teamcode.TeleOp.Testing;



import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import java.util.Queue;
import java.util.LinkedList;
@TeleOp(name="Filter Testing", group="Linear Opmode")
public class AverageMovingFilter extends LinearOpMode {
    Queue<Double> Q = new LinkedList<>();
    DistanceSensor distanceSensor;
    double distanceReading;
    double distanceSum = 0;
    double distanceAverage;

    public void calculateDistance() {
        distanceReading = distanceSensor.getDistance(DistanceUnit.CM);
        Q.add(distanceReading);
        distanceSum += distanceReading - Q.remove();
        distanceAverage = distanceSum / 20;
    }
    @Override
    public void runOpMode() throws InterruptedException {
        distanceSensor = hardwareMap.get(DistanceSensor.class, "distanceSensor");
        for (int i = 0; i < 20; i++) {
            distanceSum += 10;
            Q.add(100.0);
        }
        while (opModeIsActive()) {
            calculateDistance();
            telemetry.addData("Current Reading: ", distanceAverage);
            telemetry.update();
        }
    }
}
