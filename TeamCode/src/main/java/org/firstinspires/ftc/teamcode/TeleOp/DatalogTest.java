package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.datalogger.RepeatedDatalogger;
import com.qualcomm.hardware.bosch.BNO055IMU;

@TeleOp(name="Datalog Test", group="Linear Opmode")
public class DatalogTest extends LinearOpMode {
    DistanceSensor distanceSensor1;
    DistanceSensor distanceSensor2;
    DistanceSensor distanceSensor3;
    BNO055IMU imu;

    @Override
    public void runOpMode() {
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        distanceSensor1 = hardwareMap.get(DistanceSensor.class, "distanceSensor1");
        distanceSensor2 = hardwareMap.get(DistanceSensor.class, "distanceSensor2");
        distanceSensor3 = hardwareMap.get(DistanceSensor.class, "distanceSensor3");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imu.initialize(parameters);

        RepeatedDatalogger data = new RepeatedDatalogger("datalog_5");
        for (int i = 1; i <= 4; i++) {
            data.add(i);
        }
        data.build();

        waitForStart();
        while (opModeIsActive()) {
            data.modify("Variable 1", imu.getAngularOrientation().firstAngle);
            data.modify("Variable 2", distanceSensor1.getDistance(DistanceUnit.CM));
            data.modify("Variable 3", distanceSensor2.getDistance(DistanceUnit.CM));
            data.modify("Variable 4", distanceSensor3.getDistance(DistanceUnit.CM));
            data.writeLine();
        }
    }
}
