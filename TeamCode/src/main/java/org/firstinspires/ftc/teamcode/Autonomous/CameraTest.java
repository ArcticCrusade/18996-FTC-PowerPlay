package org.firstinspires.ftc.teamcode.Autonomous;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.common.Hardware.Camera;


@Autonomous(name="Camera Test", group="Linear OpMode")
public class CameraTest extends LinearOpMode {
    Camera camera = new Camera();

    @Override
    public void runOpMode() throws InterruptedException {
        camera.initialize(this);
        String color;

        waitForStart();

        color = camera.getColor();

        while (opModeIsActive()) {
            telemetry.addLine("Color Seen: " + color);
        }
    }
}
