package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

public class GusBot {

    // CONSTANTS
    public static double POLLEN_GATE_OPEN_POS = 0;
    public static double NECTAR_GATE_OPEN_POS = 0.3;
    public static double POLLEN_GATE_CLOSED_POS = 0.285;
    public static double NECTAR_GATE_CLOSED_POS = 0;
    public static double INTAKE_TOP_FORWARD_PWR = 0.7;
    public static double INTAKE_BOTTOM_FORWARD_PWR = 0.5;
    public static double INTAKE_TOP_BACKWARD_PWR = -0.7;
    public static double INTAKE_BOTTOM_BACKWARD_PWR = -0.5;
    public static double POLLEN_SHOOTER_ON_VELOCITY = 1650;
    public static double NECTAR_SHOOTER_ON_VELOCITY = 1600;


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

        PIDFCoefficients pidfPollen = new PIDFCoefficients(100, 0, 0, 10);
        pollen.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pidfPollen);

        PIDFCoefficients pidfNectar = new PIDFCoefficients(100, 0, 0, 10);
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


}
