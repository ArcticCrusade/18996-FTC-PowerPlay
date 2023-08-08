//autonomous v25 for the right
//changelog: number guessing game shennanigans

package org.firstinspires.ftc.teamcode.Autonomous;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import com.qualcomm.robotcore.util.ElapsedTime;
import java.util.List;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="Depreciated", group="Linear Opmode")
public class OldAuto extends LinearOpMode{
    private Servo rotator, elbow, cone, popper, release; //servo dec.
    private DcMotor leftFront, rightFront, leftRear, rightRear; //motor dec.
    private DcMotor xOdometer, yLeftOdometer, yRightOdometer; //odometer dec.
    //file where tflite file is stored along with some data with the file
    private static final String[] LABELS = {
            "Donut",
            "Present",
            "Zebra"
    };
    //reference password for vuforia access
    private static final String VUFORIA_KEY =     "AcNgP8//////AAABmZ55IDBzuUkekc8paw1AZMqOvbqVTPE2eC+CPRCYgcEht7UZ0bvrfLZSahlrwgTJfjVS8oq2kwDC/NMCS60BINpcCptCceUO07Zjek6g5yRMWoAderbKZLQuqatA1SFHN8wBwTRnTt5+FIdlFUbzGzjPytDFsw16ic+cEd/2zmlTod/XtFmRKOtYc5+hUyrdNrYwrBXw1rrqyOASodP4HPLgUCgrTJR6OfSaYO7oI5dsILNRI0VQ+pM2i7WyGanuXo3PLKf0cbOIP1+Rwnu480JWJgrVaU4dpB4IG+jzz0kdNXWhkx1fvv1W1kTlfPs+kh5Y5Wg86x+nJH4WzCkcpSlYu4ViFAYA0h8rPtPNB2D1";
    //create the vuforia and tensorflow frames
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;
    //vuforia initiation: finds webcam and accesses vuforia
    private void initVuforia() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }
    //tensorflow initiation: sets confidence, model parameters, file location
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("tfodMonitorViewId", "id",  hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minResultConfidence = 0.625f;
        tfodParameters.isModelTensorFlow2 = true;
        tfodParameters.inputSize = 300;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromFile("Cone4.tflite", LABELS);
    }
    //odometry variables/constants
    public static final double DISTANCE_PER_PULSE = Math.PI * 3.5 / 8192;
    double xvalue = 0.0;
    double ylvalue = 0.0;
    double yrvalue = 0.0;
    double heading = 0.0;
    double XPos = 0.0;
    double YPos = 0.0;
    double deltaHeading = 0.0;
    double deltaX = 0;
    double deltaYL = 0;
    double deltaYR = 0;
    double deltaYPos = 0;
    double deltaXPos = 0;
    //tensorflow variables
    int donut = 0;
    int present = 0;
    int zebra = 0;
    int currentStep = 1;
    //pid controller
    double ApError = 0.0;
    double AdError = 0.0;
    double AiError = 0.0;
    double AlastError = 0.0;
    double e = 0;
    ElapsedTime time = new ElapsedTime();
    ElapsedTime AerrorTime = new ElapsedTime();
    //movement functions
    double profileSpeed = 0;
    int counterTurn = 0;
    public double calculateAngleError(double expAngle, double AKp, double AKd, double AKi) {
        ApError = expAngle - Math.toDegrees(heading);
        AdError = (ApError - AlastError) / AerrorTime.seconds();
        AiError += ApError * AerrorTime.seconds();
        AerrorTime.reset();
        AlastError = ApError;
        return (AKp * ApError) + (AKd * AdError) + (AKi * AiError);
    }
    public void moveFwd(double expAngle, double startY, double finalY, double speed, int expStep, int profile) {
        if (currentStep == expStep) {
            e = calculateAngleError(expAngle, .01, 0, 0);
            if (YPos <= startY + 0.3 * (finalY - startY)) {
                profileSpeed = Math.abs((YPos - startY) / (finalY - startY) * 10 / 3.0 * speed) + 0.15;
            } else if (startY + 0.3 * (finalY - startY) < YPos && YPos < startY + 0.7 * (finalY - startY)) {
                profileSpeed = speed + 0.15;
            } else if (startY + 0.7 * (finalY - startY) <= YPos && YPos <= finalY) {
                profileSpeed = ((YPos - startY) / (finalY - startY) * -10 / 3.0 + 10 / 3.0) * speed + 0.15;
            } else if (YPos >= finalY) {
                profileSpeed = ((YPos - startY) / (finalY - startY) * -10 / 3.0 + 10 / 3.0) * speed - 0.15;
            }
            if (profile == 0) {
                leftFront.setPower(speed);
                rightFront.setPower(speed);
                leftRear.setPower(speed += e);
                rightRear.setPower(speed -= e);
            } else {
                leftFront.setPower(profileSpeed);
                rightFront.setPower(profileSpeed);
                leftRear.setPower(profileSpeed += e);
                rightRear.setPower(profileSpeed -= e);
            }
            if (YPos - 0.325 < finalY && finalY < YPos + 0.325) {
                currentStep++;
            }
        }
    }
    public void prepareArm(int expStep) {
        if (currentStep == expStep && counterTurn == 0) {
            //elbow = 0.41, rotator = 0, cone = 0.16
            cone.setPosition(.135);
            elbow.setPosition(0.65);
            sleep(300);
            rotator.setPosition(0.265);
            counterTurn += 1;
        }
    }
    public void moveRight(double expAngle, double startX, double finalX, double speed, int expStep) {
        if (currentStep == expStep) {
            e = calculateAngleError(expAngle, .01, 0, 0);
            if (XPos <= startX + 0.3 * (finalX - startX)) {
                profileSpeed = Math.abs((XPos - startX) / (finalX - startX) * 10 / 3.0 * speed) + 0.15;
            } else if (startX + 0.3 * (finalX - startX) < XPos && XPos < startX + 0.7 * (finalX - startX)) {
                profileSpeed = speed + 0.15;
            } else if (startX + 0.7 * (finalX - startX) <= XPos) {
                profileSpeed = ((XPos - startX) / (finalX - startX) * -10 / 3.0 + 10 / 3.0) * speed + 0.15;
            } else if (XPos > finalX) {
                profileSpeed = ((XPos - startX) / (finalX - startX) * -10 / 3.0 + 10 / 3.0) * speed - 0.15;
            }
            leftFront.setPower(-1 * profileSpeed);
            rightFront.setPower(profileSpeed);
            leftRear.setPower(profileSpeed + e);
            rightRear.setPower(-1 * profileSpeed + e);
            if (XPos - 1 < finalX && finalX < XPos + 1) {
                currentStep++;
            }
        }
    }
    public void moveBack(double expAngle, double startY, double finalY, double speed, int expStep, int profile) {
        if (currentStep == expStep) {
            e = calculateAngleError(expAngle, .01, 0, 0);
            if (YPos >= startY + 0.3 * (finalY - startY)) {
                profileSpeed = Math.abs((YPos - startY) / (finalY - startY) * 10 / 3.0 * speed) + 0.15;
            } else if (startY + 0.3 * (finalY - startY) > YPos && YPos > startY + 0.7 * (finalY - startY)) {
                profileSpeed = speed + 0.15;
            } else if (startY + 0.7 * (finalY - startY) >= YPos) {
                profileSpeed = ((YPos - startY) / (finalY - startY) * -10 / 3.0 + 10 / 3.0) * speed + 0.15;
            } else if (YPos < finalY) {
                profileSpeed = ((YPos - startY) / (finalY - startY) * -10 / 3.0 + 10 / 3.0) * speed - 0.15;
            }
            if (YPos - 0.25 < finalY && finalY < YPos + 0.25) {
                currentStep++;
            }
            if (profile == 0) {
                leftFront.setPower(-1 * speed);
                rightFront.setPower(-1 * speed);
                leftRear.setPower(-1 * speed - e);
                rightRear.setPower(-1 * speed + e);
            } else {
                leftFront.setPower(-1 * profileSpeed);
                rightFront.setPower(-1 * profileSpeed);
                leftRear.setPower(-1 * profileSpeed - e);
                rightRear.setPower(-1 * profileSpeed + e);
            }
        }
    }
    public void moveLeft(double expAngle, double startX, double finalX, double speed, int expStep) {
        if (currentStep == expStep) {
            e = calculateAngleError(expAngle, .01, 0, 0);
            if (XPos >= startX + 0.3 * (finalX - startX)) {
                profileSpeed = Math.abs((XPos - startX) / (finalX - startX) * 10 / 3.0 * speed) + 0.15;
            } else if (startX + 0.3 * (finalX - startX) > XPos && XPos > startX + 0.7 * (finalX - startX)) {
                profileSpeed = speed + 0.15;
            } else if (startX + 0.7 * (finalX - startX) >= XPos) {
                profileSpeed = ((XPos - startX) / (finalX - startX) * -10 / 3.0 + 10 / 3.0) * speed + 0.15;
            } else if (XPos < finalX) {
                profileSpeed = ((XPos - startX) / (finalX - startX) * -10 / 3.0 + 10 / 3.0) * speed - 0.15;
            }
            leftFront.setPower(profileSpeed);
            rightFront.setPower(-1 * profileSpeed);
            leftRear.setPower(-1 * profileSpeed - e);
            rightRear.setPower(profileSpeed - e);
            if (XPos - 1 < finalX && finalX < XPos + 1) {
                currentStep++;
            }
        }
    }
    public void turn(double angle, int expStep) {
        if (currentStep == expStep) {
            e = calculateAngleError(angle, (.012 * 2 / 3), 0, (0.02 * 2 / 3)); // .016, 0, .01
            leftFront.setPower(e / 2);
            leftRear.setPower(e / 2);
            rightFront.setPower(-e / 2);
            rightRear.setPower(-e / 2);
            if (Math.toDegrees(heading) - 0.2 < angle && angle < Math.toDegrees(heading) + 0.2) {
                leftFront.setPower(0);
                rightFront.setPower(0);
                leftRear.setPower(0);
                rightRear.setPower(0);
                currentStep++;
            }
        }
    }
    public void coneStack(int expStep) {
        if (expStep == currentStep) {
            leftFront.setPower(0);
            rightFront.setPower(0);
            leftRear.setPower(0);
            rightRear.setPower(0);
            // rot, elbow, cone
            elbow.setPosition(.57);
            cone.setPosition(.18);
            sleep(900);
            popper.setPosition(.35);
            sleep(200);
            // hover - .08, .65, .08
            rotator.setPosition(.08);
            elbow.setPosition(.65);
            cone.setPosition(.08);
            popper.setPosition(.35);
            sleep(1000);
            // height 5 - .06, .645, .08
            rotator.setPosition(.045);
            elbow.setPosition(.645);
            cone.setPosition(.08);
            sleep(200);
            popper.setPosition(0.19);
            sleep(200);
            // hover lift
            rotator.setPosition(.07);
            elbow.setPosition(.645);
            cone.setPosition(.07);
            sleep(200);
            // high stack
            rotator.setPosition(.265);
            elbow.setPosition(.57);
            sleep(500);
            cone.setPosition(.18);
            sleep(900);
            elbow.setPosition(.56);
            popper.setPosition(.35);
            sleep(200);
            // hover
            rotator.setPosition(.08);
            elbow.setPosition(.65);
            cone.setPosition(.08);
            popper.setPosition(.35);
            sleep(1000);
            // height 4 - .045, .64, .075
            rotator.setPosition(.045);
            elbow.setPosition(.645);
            cone.setPosition(.075);
            sleep(200);
            popper.setPosition(0.19);
            sleep(200);
            // hover lift
            rotator.setPosition(.065);
            elbow.setPosition(.645);
            cone.setPosition(.065);
            sleep(200);
            // high stack
            rotator.setPosition(.265);
            elbow.setPosition(.57);
            sleep(500);
            cone.setPosition(.18);
            sleep(900);
            elbow.setPosition(.56);
            popper.setPosition(.35);
            sleep(200);
            // hover
            rotator.setPosition(.08);
            elbow.setPosition(.65);
            cone.setPosition(.08);
            popper.setPosition(.35);
            sleep(1000);
            // height 3 - .035, .64, .075
            rotator.setPosition(.035);
            elbow.setPosition(.645);
            cone.setPosition(.07);
            sleep(200);
            popper.setPosition(0.19);
            sleep(200);
            // hover lift
            rotator.setPosition(.06);
            elbow.setPosition(.634);
            cone.setPosition(.065);
            sleep(200);
            // high stack
            rotator.setPosition(.265);
            elbow.setPosition(.57);
            sleep(500);
            cone.setPosition(.18);
            sleep(900);
            elbow.setPosition(.56);
            popper.setPosition(.35);
            sleep(200);
            // hover
            rotator.setPosition(.08);
            elbow.setPosition(.65);
            cone.setPosition(.08);
            popper.setPosition(.35);
            sleep(1000);
            // height 2 - .025, .64, .075
            rotator.setPosition(.035);
            elbow.setPosition(.645);
            cone.setPosition(.075);
            sleep(200);
            popper.setPosition(0.19);
            sleep(200);
            // hover lift
            rotator.setPosition(.06);
            elbow.setPosition(.645);
            cone.setPosition(.07);
            sleep(200);
            // high stack
            rotator.setPosition(.265);
            elbow.setPosition(.57);
            sleep(500);
            cone.setPosition(.18);
            sleep(900);
            elbow.setPosition(.56);
            popper.setPosition(.35);
            sleep(200);
            // hover
            rotator.setPosition(.08);
            elbow.setPosition(.65);
            cone.setPosition(.08);
            popper.setPosition(.35);
            sleep(1000);
            // height 1 - .015, .64, .07
            rotator.setPosition(.025);
            elbow.setPosition(.645);
            cone.setPosition(.075);
            sleep(200);
            popper.setPosition(0.19);
            sleep(200);
            // hover lift
            rotator.setPosition(.06);
            elbow.setPosition(.675);
            cone.setPosition(.075);
            sleep(200);
            // high stack
            rotator.setPosition(.265);
            elbow.setPosition(.57);
            sleep(500);
            cone.setPosition(.18);
            sleep(900);
            elbow.setPosition(.56);
            popper.setPosition(.35);
            sleep(200);
            //low
            rotator.setPosition(0.26);
            elbow.setPosition(0.65);
            cone.setPosition(0.14);
            sleep(1500);
            currentStep++;
        }
    }
    public void skipStep(int expStep) {
        if (currentStep == expStep) {
            currentStep++;
        }
    }
    public void stopAndReset(int expStep) {
        if (currentStep == expStep) {
            leftFront.setPower(0);
            rightFront.setPower(0);
            leftRear.setPower(0);
            rightRear.setPower(0);
            popper.setPosition(0.19);
            currentStep++;
        }
    }
    public void runOpMode() {
        //declare and default everything
        leftFront = hardwareMap.dcMotor.get("leftFront");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightRear = hardwareMap.dcMotor.get("rightRear");
        xOdometer = hardwareMap.dcMotor.get("leftRear");
        yRightOdometer = hardwareMap.dcMotor.get("leftFront");
        yLeftOdometer = hardwareMap.dcMotor.get("rightFront");
        rotator = hardwareMap.servo.get("rotator");
        cone = hardwareMap.servo.get("cone");
        popper = hardwareMap.servo.get("popper");
        elbow = hardwareMap.servo.get("elbow");
        release = hardwareMap.servo.get("release");
        xOdometer.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        yRightOdometer.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        yLeftOdometer.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        leftRear.setDirection(DcMotor.Direction.FORWARD);
        rightRear.setDirection(DcMotor.Direction.REVERSE);
        rotator.setDirection(Servo.Direction.FORWARD);
        cone.setDirection(Servo.Direction.FORWARD);
        popper.setDirection(Servo.Direction.FORWARD);
        elbow.setDirection(Servo.Direction.FORWARD);
        release.setDirection(Servo.Direction.FORWARD);
        //LEAVE THESE FOR LAST
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //initiate the vision programs and set zoom
        initVuforia();
        initTfod();
        if (tfod != null) {
            tfod.activate();
            tfod.setZoom(1.5, 16.0/9.0);
        }
        telemetry.addData("Camera Stream is", "Ready!");
        telemetry.update();
        waitForStart();
        time.reset();
        rotator.setPosition(0);
        elbow.setPosition(0.41);
        release.setPosition(0.34);
        popper.setPosition(0.15);
        cone.setPosition(0.16);
        while (opModeIsActive()) {
            //setting delta-variables in the incremental linear odometry math
            deltaX = xOdometer.getCurrentPosition() - xvalue;
            deltaYL = yLeftOdometer.getCurrentPosition() - ylvalue;
            deltaYR = -1 * yRightOdometer.getCurrentPosition() - yrvalue;
            xvalue += deltaX;
            ylvalue += deltaYL;
            yrvalue += deltaYR;
            //incrementing main variables using the delta-variables
            deltaYPos = 1.0257 * DISTANCE_PER_PULSE * (deltaYL + deltaYR) / 2;
            deltaHeading = 0.9759 * DISTANCE_PER_PULSE * (deltaYL - deltaYR) / 24.5;
            deltaXPos = 1.0257 * (DISTANCE_PER_PULSE * deltaX - 9.5 * deltaHeading);
            //changing x and y variables using the cc rotation matrix
            XPos += deltaXPos * Math.cos(heading) - deltaYPos * Math.sin(heading);
            YPos += deltaXPos * Math.sin(heading) + deltaYPos * Math.cos(heading);
            heading += -1 * deltaHeading;
            //updating what object is sensed for the first 6 seconds
            if (tfod != null && time.seconds() < 6) {
                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    for (Recognition recognition : updatedRecognitions) {
                        if (recognition.getLabel().equals("Donut")) {
                            donut++;
                        } else if (recognition.getLabel().equals("Present")) {
                            present++;
                        } else if (recognition.getLabel().equals("Zebra")) {
                            zebra++;
                        }
                    }
                }
            }
            if (time.seconds() > 2) {
                release.setPosition(0.1);
            }
            moveFwd(0, 0, 122.5, 0.3, 1, 1);
            prepareArm(1);
            turn(107.0, 2);
            moveBack(107, 122.5, 119, 0.115, 3, 0); //when profile = 0 the second
            coneStack(4);
            moveFwd(107, 123.5, 115.5, 0.25, 5, 0); //parameter is irrelevant
            turn(0, 6);
            moveFwd(0, 123.5, 131.5, 0.2, 7, 0);
            if (donut > present && donut > zebra) { //state 1
                moveLeft(0.0, 0, 53, 0.35, 8);
            } else if (present > zebra && present > donut) { //state 2
                skipStep(8);
            } else if (zebra > present && zebra > donut) { //state 3
                moveRight(0, 0, 53, 0.35, 8);
            } else { //backup
                moveLeft(0.0, 0, 53, 0.35, 8);
            }
            stopAndReset(9);
            telemetry.addData("Position matrix", "[" + XPos + ", " + YPos + ", " + Math.toDegrees(heading) + "]");
            telemetry.addData("Donut-Present-Zebra matrix", "[" + donut + ", " + present + ", " + zebra + "]");
            telemetry.addData("Step", currentStep);
            telemetry.update();
        } //end of while
    } //end of runOpMode
} //end of class
