package org.firstinspires.ftc.teamcode;

import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@TeleOp
public class RGTeleOp extends OpMode {

    private Follower follower;
    private GusBot gus;
    private boolean slowMode;

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        gus = new GusBot();
        gus.init(hardwareMap);
        slowMode = false;
    }

    @Override
    public void loop() {
        double multiplier = 1.0;
        if (slowMode) {
            multiplier = 0.4;
        }
        DrivePowers powers = ManualDrive.fieldCentric(
                -gamepad1.left_stick_y * multiplier,
                gamepad1.left_stick_x * multiplier,
                -gamepad1.right_stick_x * multiplier,
                follower.pose().heading()
        );

        follower.manual(powers);
        follower.update();

        if (gamepad1.right_bumper) {
            gus.intakeForward();
        }

        if (gamepad1.left_bumper) {
            gus.intakeBackward();
        }

        if (gamepad1.right_trigger_pressed) {
            gus.intakeOff();
        }

        if (gamepad1.left_trigger_pressed) {
            slowMode = !slowMode;
        }

        if (gamepad2.a) {
            gus.shootersOn();
        }

        if (gamepad2.b) {
            gus.shootersOff();
        }

        if (gamepad2.dpad_up) {
            gus.openGates();
        }

        if (gamepad2.dpad_down) {
            gus.closeGates();
        }
    }
}
