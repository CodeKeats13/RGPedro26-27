package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class GusBot {

    // CONSTANTS
    public static double POLLEN_GATE_OPEN_POS = 0.15;
    public static double NECTAR_GATE_OPEN_POS = 0.3;
    public static double POLLEN_GATE_CLOSED_POS = 0.285;
    public static double NECTAR_GATE_CLOSED_POS = 0;
    public static double INTAKE_TOP_FORWARD_PWR = 0.7;
    public static double INTAKE_BOTTOM_FORWARD_PWR = 0.5;
    public static double INTAKE_TOP_BACKWARD_PWR = -0.7;
    public static double INTAKE_BOTTOM_BACKWARD_PWR = -0.5;
    public static double POLLEN_SHOOTER_ON_VELOCITY = 1625;
    public static double NECTAR_SHOOTER_ON_VELOCITY = 1625;
    public static double POLLEN_P = 200;
    public static double POLLEN_F = 15;
    public static double NECTAR_P = 200;
    public static double NECTAR_F = 13;


    private DcMotor intakeTop;
    private DcMotor intakeBottom;
    private DcMotorEx pollen;
    private DcMotorEx nectar;
    private Servo pollenGate;
    private Servo nectarGate;


    public void init(HardwareMap hardwareMap){

        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.

        intakeTop = hardwareMap.get(DcMotor.class, "IntakeT");
        intakeBottom = hardwareMap.get(DcMotor.class,"IntakeB");
        //Hehe --> (Vineeth made the change) Respect to Digital Dinos
        pollenGate = hardwareMap.get(Servo.class, "PollenGate");
        nectarGate = hardwareMap.get(Servo.class, "NectarGate");
        pollen = hardwareMap.get(DcMotorEx.class, "Pollen");
        nectar = hardwareMap.get(DcMotorEx.class, "Nectar");

        intakeTop.setDirection(DcMotor.Direction.REVERSE);
        intakeBottom.setDirection(DcMotor.Direction.REVERSE);
        nectar.setDirection(DcMotor.Direction.REVERSE);
        nectarGate.setDirection(Servo.Direction.REVERSE);

        PIDFCoefficients pidfPollen = new PIDFCoefficients(POLLEN_P, 0, 0, POLLEN_F);
        pollen.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfPollen);

        PIDFCoefficients pidfNectar = new PIDFCoefficients(NECTAR_P, 0, 0, NECTAR_F);
        nectar.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfNectar);


        intakeTop.setPower(0);
        intakeBottom.setPower(0);
        pollen.setPower(0);
        nectar.setPower(0);
        pollenGate.setPosition(POLLEN_GATE_CLOSED_POS);
        nectarGate.setPosition(NECTAR_GATE_CLOSED_POS);
        pollen.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        nectar.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pollen.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        nectar.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void shoot() {
        pollenGate.setPosition(POLLEN_GATE_OPEN_POS);
        nectarGate.setPosition(NECTAR_GATE_OPEN_POS);
    }

    public void closeGates() {
        pollenGate.setPosition(POLLEN_GATE_CLOSED_POS);
        nectarGate.setPosition(NECTAR_GATE_CLOSED_POS);
    }

    public void openPollen() {
        pollenGate.setPosition(POLLEN_GATE_OPEN_POS);
    }

    public void closePollen() {
        pollenGate.setPosition(POLLEN_GATE_CLOSED_POS);
    }

    public void openNectar() {
        nectarGate.setPosition(NECTAR_GATE_OPEN_POS);
    }

    public void closeNectar() {
        nectarGate.setPosition(NECTAR_GATE_CLOSED_POS);
    }

    public void intake() {
        intakeTop.setPower(INTAKE_TOP_FORWARD_PWR);
        intakeBottom.setPower(INTAKE_BOTTOM_FORWARD_PWR);
    }

    public void outtake() {
        intakeTop.setPower(INTAKE_TOP_BACKWARD_PWR);
        intakeBottom.setPower(INTAKE_BOTTOM_BACKWARD_PWR);
    }

    public void intakeOff() {
        intakeTop.setPower(0);
        intakeBottom.setPower(0);
    }

    public void shootersOn() {
        pollen.setVelocity(POLLEN_SHOOTER_ON_VELOCITY);
        nectar.setVelocity(NECTAR_SHOOTER_ON_VELOCITY);
    }

    public void shootersOff() {
        pollen.setVelocity(0);
        nectar.setVelocity(0);
    }

    public double getPollenVelocity() {
        return pollen.getVelocity();
    }

    public double getNectarVelocity() {
        return nectar.getVelocity();
    }

    public double getPollenSetVelocity() {
    return POLLEN_SHOOTER_ON_VELOCITY;
    }


}
