//functioning arm v2 teleop program
//goals: do everything and add arm modulation to v21

/*
Stack 5: rot 11.5 elbow 69.5
Stack 4: rot 10.5 elbow 69.5
Stack 3: rot 10 elbow 69.5
Stack 2: rot 9.5 elbow 70
Stack 1: rot 9 elbow 70
*/
package org.firstinspires.ftc.teamcode.TeleOp.Testing;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="TeleOpV23", group="Linear Opmode")
public class TeleOpV23 extends LinearOpMode{
    private ElapsedTime runtime = new ElapsedTime();
    private Servo rotator; //
    private Servo cone; //
    private Servo popper; //
    private Servo release; //
    private Servo elbow;
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftRear;
    private DcMotor rightRear;
    double rotatorPos, extenderPos, conePos, popperPos, releasePos, elbowPos;
    //declare position/speed variables
    double RF,LF,RR,LR;
    //declare gamepad position/state variables
    double X1,Y1,X2,Y2;
    //set the power scale
    double powerScale;
    //stores the position of the whole arm
    String armPos;
    int counter1 = 0;
    int counter2 = 0;
    int counter3 = 0;
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //initialize motor variables
        leftFront = hardwareMap.dcMotor.get("leftFront"); //slot 0
        rightFront = hardwareMap.dcMotor.get("rightFront"); //slot 1
        leftRear = hardwareMap.dcMotor.get("leftRear"); //slot 2
        rightRear = hardwareMap.dcMotor.get("rightRear"); //slot 3
        rotator = hardwareMap.servo.get("rotator");
        elbow = hardwareMap.servo.get("extender");
        cone = hardwareMap.servo.get("cone");
        popper = hardwareMap.servo.get("popper");
        release = hardwareMap.servo.get("release");

        // Set the drive motor direction:
        // "Reverse" the motor that runs backwards when connected directly to the battery
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        leftRear.setDirection(DcMotor.Direction.REVERSE);
        rightRear.setDirection(DcMotor.Direction.FORWARD);
        //set servo directions
        rotator.setDirection(Servo.Direction.FORWARD);
        cone.setDirection(Servo.Direction.FORWARD);
        popper.setDirection(Servo.Direction.FORWARD);
        release.setDirection(Servo.Direction.FORWARD);
        elbow.setDirection(Servo.Direction.FORWARD);


        // Set the drive motor run modes:
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {

            rotatorPos = rotator.getPosition();
            conePos = cone.getPosition();
            popperPos =popper.getPosition();
            releasePos = release.getPosition();

            if (gamepad2.right_bumper==true){
                popper.setPosition(0.35);
// sleep(300);
            }
            else {
                popper.setPosition(0.19);
            }
//cycling high - rotator 0.285, elbow 0.65, cone 0.14
            if (gamepad2.b == true){
                counter1 = 0;
                counter2 = 0;
                counter3 = 0;
                rotator.setPosition(0.635);
                elbow.setPosition(0.65);
                cone.setPosition(0.14);
                armPos = "highDrop";
            }
//high drop off - rotator 0.26, elbow 0.65, cone 0.135
            if (gamepad2.y == true){
                counter1 = 0;
                counter2 = 0;
                counter3 = 0;
                rotator.setPosition(0.59);
                elbow.setPosition(0.65);
                cone.setPosition(0.135);
                armPos = "highDrop";
            }
//medium drop off - rotator 0.095, elbow 0.515, cone 0.16
            if (gamepad2.x == true){
                counter1 = 0;
                counter2 = 0;
                counter3 = 0;
                if (armPos == "medGrab" || armPos == "closeGrab"){
                    elbow.setPosition(0.38);
                    sleep(500);
                    rotator.setPosition(0.26);
                } else {
                    rotator.setPosition(0.26);
                    elbow.setPosition(0.38);
                }
                cone.setPosition(0.16);
                armPos = "medDrop";
            }
//low drop off - rotator 0.0, elbow 0.455, cone 0.17
            if (gamepad2.a == true){
                counter1 = 0;
                counter2 = 0;
                counter3 = 0;
                if (armPos == "medGrab" || armPos == "closeGrab"){
                    elbow.setPosition(0.265);
                    sleep(1000);
                    rotator.setPosition(0.11);
                } else {
                    rotator.setPosition(0.11);
                    elbow.setPosition(0.265);
                }
                cone.setPosition(0.17);
                armPos = "lowDrop";
            }
//far pick up - rotator 0.020, elbow 0.65, cone 0.075
            if (gamepad2.dpad_left == true){
                sleep(300);
                counter2 = 0;
                counter3 = 0;
                if (counter1 % 2 == 0) {
                    if (armPos == "medGrab" || armPos == "closeGrab"){
                        elbow.setPosition(0.65);
                        sleep(150);
                        rotator.setPosition(0.125);
                    } else {
                        rotator.setPosition(0.125);
                        elbow.setPosition(0.65);
                    }
                    cone.setPosition(0.075);
                    armPos = "farGrab";
                    counter1 += 1;
                }
                else if (counter1 % 2 == 1) {
                    rotator.setPosition(0.105);
                    elbow.setPosition(0.65);
                    cone.setPosition(0.075);
                    armPos = "farGrab";

                    counter1 += 1;
                }
            }
//medium pick up - rotator 0.12, elbow 0.745, cone 0.055
            if (gamepad2.dpad_down == true){
                sleep(300);
                counter1 = 0;
                counter3 = 0;
                if (counter2 % 2 == 0) {
                    if (armPos == "closeGrab"){
                        elbow.setPosition(0.86);
                        sleep(100);
                        rotator.setPosition(0.375);
                    } else {
                        rotator.setPosition(0.375);
                        elbow.setPosition(0.86);
                    }
                    cone.setPosition(0.06);
                    armPos = "medGrab";
                    counter2 += 1;
                }
                else {
                    rotator.setPosition(0.325);
                    elbow.setPosition(0.845);
                    cone.setPosition(0.055);
                    counter2 += 1;
                    armPos = "medGrab";
                }
            }
//lift off stack
/*if (gamepad2.dpad_up == true){
 sleep(300);
 counter1 = 0;
 counter2 = 0;
 counter3 = 0;
    if (armPos == "closeGrab"){
     elbow.setPosition(0.745);
     sleep(100);
     rotator.setPosition(0.20);
    } else {
     rotator.setPosition(0.20);
     elbow.setPosition(0.755);
    }
    cone.setPosition(0.06);
    armPos = "medGrab";
  }
*/
//close pick up - rotator 0.16, elbow 0.775, cone 0.04
            if (gamepad2.dpad_right == true){
                counter2 = 0;
                counter1 = 0;
                sleep(300);
                if (counter3 % 2 == 0) {
                    sleep(200);
                    rotator.setPosition(0.44);
                    elbow.setPosition(0.905);
                    cone.setPosition(0.05);
                    armPos = "closeGrab";
                    counter3 += 1;
                }
                else {
                    rotator.setPosition(0.385);
                    elbow.setPosition(0.905);
                    cone.setPosition(0.05);
                    counter3 += 1;
                    armPos = "closeGrab";
                }
            }

//ground junction
            if (gamepad2.right_stick_button == true){
                rotator.setPosition(0.4);
                elbow.setPosition(0.905);
                cone.setPosition(0.045);
                armPos = "closeGrab";
            }

            // Reset speed variables
            LF = 0; RF = 0; LR = 0; RR = 0;
            Y1 = 0; X1 = 0; Y2 = 0; X2 = 0;

            rotatorPos = rotator.getPosition();
            conePos = cone.getPosition();
            popperPos = popper.getPosition();
            releasePos = release.getPosition();
            elbowPos = elbow.getPosition();

// Get joystick values
            Y1 = gamepad1.right_stick_y;
            X1 = gamepad1.right_stick_x;
            //Y2 = -gamepad1.left_stick_y;
            X2 = gamepad1.left_stick_x;

            //calculate motor output from joysticks
            LF = Y1 + X1 + X2;
            RF = Y1 - X1 - X2;
            LR = Y1 - X1 + X2;
            RR = Y1 + X1 - X2;

            powerScale = ((gamepad1.right_trigger)*0.7)+0.3-((gamepad1.left_trigger) * .15);
            //set motor commands
            leftFront.setPower(LF*powerScale);
            rightFront.setPower(RF*powerScale);
            leftRear.setPower(LR*powerScale);
            rightRear.setPower(RR*powerScale);

            telemetry.addData("Rotator Position",rotatorPos);
            telemetry.addData("Extender Position",extenderPos);
            telemetry.addData("Cone Position",conePos);
            telemetry.addData("Popper Position",popperPos);
            telemetry.addData("Release Position",releasePos);
            telemetry.addData("Elbow Position",elbowPos);
            telemetry.addData("Arm State",armPos);
            telemetry.update();
        }
    }
}

