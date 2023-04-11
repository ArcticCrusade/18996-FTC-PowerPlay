//unknonwn state teleop program
//goals: use servos through incrementation
package org.firstinspires.ftc.teamcode.TeleOp;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@TeleOp(name="Servo indexer", group="Linear Opmode")
public class ServoIncrementer extends LinearOpMode{

    //define servo and servo variables
    private ElapsedTime runtime = new ElapsedTime();
    private Servo rotator = null;
    private Servo extender = null;
    private Servo cone = null;
    private Servo popper = null;

    private Servo release = null;
   // private Servo elbow = null;


    double rotatorPos, extenderPos, conePos, popperPos, releasePos, elbowPos;
    public static double rotPos = 0.26;
    public static double exdPos = 0.65;
    public static double conPos = 0.6;
    public static double popPos = 0.1;
    public static double relPos = 0;
    String servo;

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        //configure servos
        rotator = hardwareMap.servo.get("rotator"); //0e
        extender = hardwareMap.servo.get("extender"); //0
        cone = hardwareMap.servo.get("cone"); //2
        popper = hardwareMap.servo.get("popper"); //3
        release = hardwareMap.servo.get("release");//1e
        //elbow = hardwareMap.servo.get("elbow");
        rotator.setDirection(Servo.Direction.FORWARD);
        extender.setDirection(Servo.Direction.FORWARD);
        cone.setDirection(Servo.Direction.FORWARD);
        popper.setDirection(Servo.Direction.FORWARD);
        //elbow.setDirection(Servo.Direction.FORWARD);
        //set default servo position

        waitForStart();
        runtime.reset();


        while (opModeIsActive()) {


            //rotator
            if (gamepad1.dpad_up==true){
                rotPos+=0.005;
                sleep(200);
            }
            if (gamepad1.dpad_down==true){
                rotPos-=0.005;
                sleep(200);
            }
            rotator.setPosition(rotPos);
//elbow
            if (gamepad1.y==true){
                exdPos+=0.005;
                sleep(200);
            }
            if (gamepad1.a==true){
                exdPos-=0.005;
                sleep(200);
            }
            extender.setPosition(exdPos);
//cone
            if (gamepad1.x==true){
                conPos+=0.005;
                sleep(200);
            }
            if (gamepad1.b==true){
                conPos-=0.005;
                sleep(200);
            }
            cone.setPosition(conPos);
//grabber
            if (gamepad1.dpad_left==true){
                popPos+=0.01;
                sleep(200);
            }
            if (gamepad1.dpad_right==true){
                popPos-=0.01;
                sleep(200);
            }
            popper.setPosition(popPos);
            release.setPosition(relPos);
            //elbowPos = elbow.getPosition();
            rotatorPos = rotator.getPosition();
            extenderPos = extender.getPosition();
            conePos = cone.getPosition();
            popperPos = popper.getPosition();
            releasePos = release.getPosition();

            //find current servo positions
/* rotatorPos = rotator.getPosition();
 extenderPos = extender.getPosition();
 conePos = cone.getPosition();
 popperPos = popper.getPosition();
 releasePos = release.getPosition();
 elbowPos = elbow.getPosition();

 if (gamepad1.dpad_up==true){
  rotPos+=0.005;
  sleep(200);
 }
  if (gamepad1.dpad_down==true){
  rotPos-=0.005;
  sleep(200);
 }
 rotator.setPosition(rotPos);

  if (gamepad1.y==true){
  extPos+=0.005;
  sleep(200);
 }
  if (gamepad1.a==true){
  extPos-=0.005;
  sleep(200);
 }
 elbow.setPosition(extPos);*/
            //chooses servo to be modified.
/*if(gamepad1.y==true){
 servo=("extender");
}
if (gamepad1.b==true){
 servo=("rotator");
 }
if (gamepad1.a==true){
 servo=("cone");
}
if (gamepad1.x==true){
 servo=("popper");
}
if (gamepad1.start==true){
 servo=("release");
}
//incrementation code
if (gamepad1.dpad_up==true){
 servoPos+=0.01;
 extPos+=0.01;
 rotPos+=0.005;
  sleep(200);
}
if (gamepad1.dpad_down==true){
 servoPos-=0.01;
 extPos-=0.01;
 rotPos-=0.005;
  sleep(200);
}
//extender has seperate incrementation
 if (gamepad1.left_bumper==true){
  extender.setPosition(0.02);
 }
 if(gamepad1.right_bumper==true){
//  extender.setPosition(0.11);
  light.setState(true);
 } else {
  light.setState(false);
 }
//sets servos to updated values
if (servo==("extender")){
 extender.setPosition(extPos);
}
if (servo==("rotator")){
 elbow.setPosition(rotPos);
}
if (servo==("cone")){
 cone.setPosition(servoPos);
}
if (servo==("popper")){
 popper.setPosition(servoPos);
}
if (servo==("release")){
 release.setPosition(servoPos);
}
*/
//telemetry outputs
            telemetry.addData("Servo",servo);
            telemetry.addData("Rotator Position",rotatorPos);
            telemetry.addData("Extender Position",extenderPos);
            telemetry.addData("Cone Position",conePos);
            telemetry.addData("Popper Position",popperPos);
            telemetry.addData("Release Position",releasePos);
            //telemetry.addData("Elbow Position",elbowPos);
            telemetry.update();
        }
    }
}
