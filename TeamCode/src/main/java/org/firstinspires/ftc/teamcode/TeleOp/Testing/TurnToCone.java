package org.firstinspires.ftc.teamcode.TeleOp.Testing;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.checkerframework.checker.units.qual.C;
import org.firstinspires.ftc.common.Hardware.Camera;

public class TurnToCone extends LinearOpMode {
    final double speed = .2;
    Camera camera = new Camera(hardwareMap);
    int currentCenter;
    int currentWidth;
    private DcMotor leftFront, rightFront, leftRear, rightRear;

    @Override
    public void runOpMode() throws InterruptedException {
        camera.switchPipeline("red");
        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightRear = hardwareMap.dcMotor.get("rightRear");

        waitForStart();

        while (opModeIsActive()) {
            currentCenter = camera.getConePipeline().getCenter();
            currentWidth = camera.getConePipeline().getWidth();

            if (currentCenter != 600) {
                if (currentCenter > 170) {
                    leftFront.setPower(speed + .2);
                    leftRear.setPower(speed + .2);
                    rightFront.setPower(-speed + .2);
                    rightRear.setPower(-speed + .2);
                }
                else if (currentCenter < 150) {
                    leftFront.setPower(-speed + .2);
                    leftRear.setPower(-speed + .2);
                    rightFront.setPower(speed + .2);
                    rightRear.setPower(speed + .2);
                }
                else if (currentWidth > 50) {
                    leftFront.setPower(0.2);
                    leftRear.setPower(0.2);
                    rightFront.setPower(0.2);
                    rightRear.setPower(0.2);
                }
                else {
                    leftFront.setPower(0);
                    leftRear.setPower(0);
                    rightFront.setPower(0);
                    rightRear.setPower(0);
                }
            }
            wait(50);
        }
    }
}
