package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.pedropathing.controllers.PIDController;
import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.pedro.Constants;

@TeleOp
public class RGTeleOp extends OpMode {
    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry dashboardTelemetry = dashboard.getTelemetry();


    private Follower follower;
    private GusBot gus;
    private boolean slowMode;
    private boolean holdMode;
    private final PIDController headingController = new PIDController(2.0, 0.0, 0.1);

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        gus = new GusBot();
        gus.init(hardwareMap);
        slowMode = false;
        holdMode = true;
        telemetry.addLine("Init Complete. Good Luck Drivers!!");
        telemetry.update();
    }

    @Override
    public void start(){
        follower.setPose(OpModeStorage.autonomousEndPose);
        follower.update();
    }

    @Override
    public void loop() {
        double multiplier = 1.0;
        if (slowMode) {
            multiplier = 0.4;
        }
        double targetHeading = Math.atan2(-gamepad1.right_stick_x, -gamepad1.right_stick_y);
        DrivePowers powers = new DrivePowers(
                -gamepad1.left_stick_y * multiplier,
                -gamepad1.left_stick_x * multiplier,
                -gamepad1.right_stick_x * multiplier);

        powers = ManualDrive.headingLock(follower, headingController, powers, targetHeading);
        powers = ManualDrive.fieldCentric(
                powers,
                follower.pose().heading()
        );

        if (holdMode) {
            ManualDrive.driveOrHold(follower, powers);
            telemetry.addData("holdMode", holdMode);
        } else {
            follower.manual(powers);
            telemetry.addData("holdMode", holdMode);
        }
        follower.update();
        Pose robotPose = follower.pose(); // returns a Pose object

        telemetry.addData("Robot X", robotPose.x());
        telemetry.addData("Robot Y", robotPose.y());
        telemetry.addData("Robot Heading", Math.toDegrees(robotPose.heading()));

        if (gamepad1.rightBumperWasPressed()) {
            gus.intake();
        }

        if (gamepad1.rightTriggerWasPressed()) {
            gus.outtake();
        }

        if (gamepad1.leftBumperWasPressed()) {
            gus.intakeOff();
        }

        if (gamepad1.leftTriggerWasPressed()) {
            slowMode = !slowMode;
        }

        if (gamepad1.rightStickButtonWasPressed()) {
            holdMode = !holdMode;
        }

        if (gamepad1.backWasPressed()) {
            //Reset Heading to 0
            follower.setPose(new Pose(follower.pose().x(), follower.pose().y(), 0));
        }

        if (gamepad2.aWasPressed()) {
            gus.shootersOn();
        }

        if (gamepad2.bWasPressed()) {
            gus.shootersOff();
            gus.closeGates();
        }

        if (gamepad2.dpadUpWasPressed()) {
            gus.shoot();
        }

        if (gamepad2.dpadDownWasPressed()) {
            gus.closeGates();
        }

        if (gamepad2.leftBumperWasPressed()) {
            gus.openPollen();
        }

        if (gamepad2.leftTriggerWasPressed()) {
            gus.closePollen();
        }

        if (gamepad2.rightBumperWasPressed()) {
            gus.openNectar();
        }

        if (gamepad2.rightTriggerWasPressed()) {
            gus.closeNectar();
        }
        dashboardTelemetry.addData("Pollen Velocity", gus.getPollenVelocity());
        dashboardTelemetry.addData("NectarVelocity", gus.getNectarVelocity());
        dashboardTelemetry.addData("Pollen", gus.getPollenVelocity() > 1600 ? "READY" : "NOT_READY");
        dashboardTelemetry.addData("Nectar", gus.getNectarVelocity() > 1600 ? "READY" : "NOT_READY");
        dashboardTelemetry.update();

        telemetry.addData("Pollen Velocity", gus.getPollenVelocity());
        telemetry.addData("Nectar Velocity", gus.getNectarVelocity());
        telemetry.addData("Pollen", gus.getPollenVelocity() > 1600 ? "READY" : "NOT_READY");
        telemetry.addData("Nectar", gus.getNectarVelocity() > 1600 ? "READY" : "NOT_READY");
        telemetry.addData("Target Heading", targetHeading);
        telemetry.update();

    }
}
