package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

public class GusBot {

    // CONSTANTS
    public static double POLLEN_GATE_OPEN_POS = 0;
    public static double NECTAR_GATE_OPEN_POS = 0.3;
    public static double POLLEN_GATE_CLOSED_POS = 0.35;
    public static double NECTAR_GATE_CLOSED_POS = 0;
    public static double INTAKE_TOP_FORWARD_PWR = 0.7;
    public static double INTAKE_BOTTOM_FORWARD_PWR = 0.5;
    public static double INTAKE_TOP_BACKWARD_PWR = -0.7;
    public static double INTAKE_BOTTOM_BACKWARD_PWR = -0.5;
    public static double INTAKE_TOP_OFF_PWR = 0;
    public static double INTAKE_BOTTOM_OFF_PWR = 0;
    public static double POLLEN_SHOOTER_ON_PWR = 0.6;
    public static double NECTAR_SHOOTER_ON_PWR = 0.6;
    public static double POLLEN_SHOOTER_OFF_PWR = 0;
    public static double NECTAR_SHOOTER_OFF_PWR = 0;

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

        intakeTop.setPower(0);
        intakeBottom.setPower(0);
        pollen.setPower(0);
        nectar.setPower(0);
        pollenGate.setPosition(POLLEN_GATE_CLOSED_POS);
        nectarGate.setPosition(NECTAR_GATE_CLOSED_POS);
    }

    public void openGates() {
        pollenGate.setPosition(POLLEN_GATE_OPEN_POS);
        nectarGate.setPosition(NECTAR_GATE_OPEN_POS);
    }

    public void closeGates() {
        pollenGate.setPosition(POLLEN_GATE_CLOSED_POS);
        nectarGate.setPosition(NECTAR_GATE_CLOSED_POS);
    }

    public void intakeForward() {
        intakeTop.setPower(INTAKE_TOP_FORWARD_PWR);
        intakeBottom.setPower(INTAKE_BOTTOM_FORWARD_PWR);
    }

    public void intakeBackward() {
        intakeTop.setPower(INTAKE_TOP_BACKWARD_PWR);
        intakeBottom.setPower(INTAKE_BOTTOM_BACKWARD_PWR);
    }

    public void intakeOff() {
        intakeTop.setPower(INTAKE_TOP_OFF_PWR);
        intakeBottom.setPower(INTAKE_BOTTOM_OFF_PWR);
    }

    public void shootersOn() {
        pollen.setPower(POLLEN_SHOOTER_ON_PWR);
        nectar.setPower(NECTAR_SHOOTER_ON_PWR);
    }

    public void shootersOff() {
        pollen.setPower(POLLEN_SHOOTER_OFF_PWR);
        nectar.setPower(NECTAR_SHOOTER_OFF_PWR);
    }
}
