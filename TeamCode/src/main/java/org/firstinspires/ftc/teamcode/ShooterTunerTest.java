package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@TeleOp
public class ShooterTunerTest extends OpMode {

    public DcMotorEx pollen;

    public DcMotorEx nectar;

    private GusBot gus;


    public static double POLLEN_SHOOTER_ON_VELOCITY = 1600;
    public static double NECTAR_SHOOTER_ON_VELOCITY = 1625;
    public static double POLLEN_P = 200;
    public static double POLLEN_F = 15;
    public static double NECTAR_P = 200;
    public static double NECTAR_F = 12;


    double[] stepSizes = {10.0, 1.0, 0.1};

    int stepIndex = 1;


    @Override
    public void init() {
        pollen = hardwareMap.get(DcMotorEx.class, "Pollen");
        nectar = hardwareMap.get(DcMotorEx.class, "Nectar");
        nectar.setDirection(DcMotor.Direction.REVERSE);
        pollen.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        nectar.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pollen.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        nectar.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        gus = new GusBot();
        gus.init(hardwareMap);

        PIDFCoefficients pidfPollen = new PIDFCoefficients(POLLEN_P, 0, 0, POLLEN_F);
        pollen.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfPollen);

        PIDFCoefficients pidfNectar = new PIDFCoefficients(NECTAR_P, 0, 0, NECTAR_F);
        nectar.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfNectar);
    }

    @Override
    public void loop() {
        //get all gamepad commands
        //set target velocity
        //update telemetry

        if (gamepad1.aWasPressed()) {
            POLLEN_SHOOTER_ON_VELOCITY -= stepSizes[stepIndex];
        }

        if (gamepad1.bWasPressed()) {
            stepIndex = (stepIndex + 1) % stepSizes.length;
        }

        if (gamepad1.yWasPressed()) {
            POLLEN_SHOOTER_ON_VELOCITY += stepSizes[stepIndex];
        }

        if (gamepad1.dpadUpWasPressed()) {
            POLLEN_P += stepSizes[stepIndex];
        }

        if (gamepad1.dpadDownWasPressed()) {
            POLLEN_P -= stepSizes[stepIndex];
        }

        if (gamepad1.dpadLeftWasPressed()) {
            POLLEN_F += stepSizes[stepIndex];
        }

        if (gamepad1.dpadRightWasPressed()) {
            POLLEN_F -= stepSizes[stepIndex];
        }

        if (gamepad1.leftBumperWasPressed()) {
            gus.shootersOff();
        }

        if (gamepad1.rightBumperWasPressed()) {
            gus.shootersOn();
        }


        if (gamepad2.aWasPressed()) {
            NECTAR_SHOOTER_ON_VELOCITY -= stepSizes[stepIndex];
        }

        if (gamepad2.bWasPressed()) {
            stepIndex = (stepIndex + 1) % stepSizes.length;
        }

        if (gamepad2.yWasPressed()) {
            NECTAR_SHOOTER_ON_VELOCITY += stepSizes[stepIndex];
        }

        if (gamepad2.dpadUpWasPressed()) {
            NECTAR_P += stepSizes[stepIndex];
        }

        if (gamepad2.dpadDownWasPressed()) {
            NECTAR_P -= stepSizes[stepIndex];
        }

        if (gamepad2.dpadLeftWasPressed()) {
            NECTAR_F += stepSizes[stepIndex];
        }

        if (gamepad2.dpadRightWasPressed()) {
            NECTAR_F -= stepSizes[stepIndex];
        }

        if (gamepad2.leftBumperWasPressed()) {
            gus.shootersOff();
        }

        if (gamepad2.rightBumperWasPressed()) {
            gus.shootersOn();
        }


        telemetry.addData("Pollen Target Velocity", POLLEN_SHOOTER_ON_VELOCITY);
        telemetry.addData("Nectar Target Velocity", NECTAR_SHOOTER_ON_VELOCITY);
        telemetry.addData("Pollen P", POLLEN_P);
        telemetry.addData("Pollen F", POLLEN_F);
        telemetry.addData("Nectar P", NECTAR_P);
        telemetry.addData("Nectar F", NECTAR_F);
        telemetry.addData("Step Size", stepSizes[stepIndex]);
        telemetry.update();

    }
}
