package org.firstinspires.ftc.teamcode.TeleOp;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.AnalogInput;
import org.firstinspires.ftc.common.Hardware.ConfigStateMachine;



public class DrivingConfig extends LinearOpMode {
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftRear;
    private DcMotor rightRear;
    private double LF, RF, LR, RR, Y1, X1, Y2, X2;
    private static int index = 0;


    private static void incrementState(State currentState) {
        switch (currentState) {
            case minSpeed:
                currentState = State.topSpeed;
                index = 1;
                break;
            case topSpeed:
                currentState = State.brakeAmount;

                break;
            case brakeAmount:
                currentState = State.speedMode;
                break;
            case speedMode:
                currentState = State.minSpeed;
                break;
        }
    }

    double[] configValues = {0.0, 1.0, 0.5, 2.0};

    enum State {
        minSpeed,
        topSpeed,
        brakeAmount,
        speedMode,
        slowMode,
    };


    @Override
    public void runOpMode() {
        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightRear = hardwareMap.dcMotor.get("rightRear");
        LF = 0; RF = 0; LR = 0; RR = 0;
        Y1 = 0; X1 = 0; Y2 = 0; X2 = 0;

// Get joystick values
        Y1 = gamepad1.right_stick_y;
        X1 = -gamepad1.right_stick_x;
        Y2 = -gamepad1.left_stick_y;
        X2 = gamepad1.left_stick_x;

        //calculate motor output from joysticks
        LF = Y1 - X1 + X2;
        RF = Y1 + X1 - X2;
        LR = Y1 + X1 + X2;
        RR = Y1 - X1 - X2;
    }
}
