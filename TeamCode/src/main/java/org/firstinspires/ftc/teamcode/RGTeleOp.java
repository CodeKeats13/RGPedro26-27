package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
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
    public void loop() {
        double multiplier = 1.0;
        if (slowMode) {
            multiplier = 0.4;
        }
        DrivePowers powers = ManualDrive.fieldCentric(
                gamepad1.left_stick_y * multiplier,
                gamepad1.left_stick_x * multiplier,
                -gamepad1.right_stick_x * multiplier,
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

        if (gamepad1.leftBumperWasPressed()) {
            gus.outtake();
        }

        if (gamepad1.rightTriggerWasPressed()) {
            gus.intakeOff();
        }

        if (gamepad1.leftTriggerWasPressed()) {
            slowMode = !slowMode;
        }

        if (gamepad1.rightStickButtonWasPressed()) {
            holdMode = !holdMode;
        }

        if (gamepad2.aWasPressed()) {
            gus.shootersOn();
        }

        if (gamepad2.bWasPressed()) {
            gus.shootersOff();
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
        dashboardTelemetry.addData("pollenVelocity", gus.getPollenVelocity());
        dashboardTelemetry.addData("nectarVelocity", gus.getNectarVelocity());
        telemetry.addData("pollenVelocity", gus.getPollenVelocity());
        telemetry.addData("nectarVelocity", gus.getNectarVelocity());
        telemetry.addData("pollenSetVelocity", gus.getPollenSetVelocity());
        telemetry.addData("Pollen", gus.getPollenVelocity() > 1600 ? "READY" : "NOT_READY");
        telemetry.addData("Nectar", gus.getNectarVelocity() > 1600 ? "READY" : "NOT_READY");
        telemetry.update();
        dashboardTelemetry.update();
    }
}
