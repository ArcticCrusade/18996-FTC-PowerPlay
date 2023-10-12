package org.firstinspires.ftc.teamcode.Autonomous.Testing;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.common.Hardware.Camera;


@Autonomous(name="Camera Test", group="Linear OpMode")
public class CameraTest extends LinearOpMode {
    Camera camera = new Camera(hardwareMap);

    @Override
    public void runOpMode() throws InterruptedException {
        String color = "temp";

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addLine("Color Seen: " + color);
            telemetry.update();
        }
    }
}
