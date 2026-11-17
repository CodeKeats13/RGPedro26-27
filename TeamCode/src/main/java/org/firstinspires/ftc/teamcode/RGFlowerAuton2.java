package org.firstinspires.ftc.teamcode;

import static com.pedropathing.api.Paths.curve;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.instant;
import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.groups.Groups.race;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous
public class RGFlowerAuton2 extends OpMode {
    private Follower follower;
    private GusBot gus;
    private final PoseFactory p = PoseFactory.degrees();

    private final Pose startPose = p.of(61.5, 135, 90);
    private final Pose flowerPose = p.of(56, 131.5, 90);
    private final Pose flowerControlPose = p.of(59, 100, 0);
    private final Pose parkControlPose = p.of(50, 97, 0);
    private final Pose parkPose = p.of(11, 124, 0);
    private Path Flower() {
        return curve(startPose, flowerControlPose, flowerPose).linear(startPose, flowerPose);
    }

    private Path FlowerShoot() {
        return curve(flowerPose, flowerControlPose, startPose).linear(flowerPose, startPose);
    }
    private Path Park() {
        return curve(flowerPose, parkControlPose, parkPose).linear(flowerPose, parkPose);
    }

    Command shoot = sequential(
            instant(() -> {gus.shoot();}),
            waitMs(2000),
            instant(() -> {gus.shootersOff();}),
            instant(() -> {gus.closeGates();})
    );

    Command shootPrep = sequential(
            instant(() -> {gus.shootersOn();}),
            instant(() -> {gus.intakeOff();})
    );


    private Command autoRoutine() {
        return sequential(
                shootPrep,
                waitMs(10000),
                shoot,
                instant(() -> {gus.intake();}),
                race(follow(follower, Flower()),
                     waitMs(3000)),
                waitMs(3000),
                shootPrep,
                race(follow(follower, FlowerShoot()),
                        waitMs(3000)),
                instant(() -> {gus.shoot();}),
                waitMs(2000),
                follow(follower, Park())
        );
    }

    @Override
    public void init() {
        gus = new GusBot();
        gus.init(hardwareMap);
        gus.openNectar();
        Scheduler.reset();

        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);
        follower.update();
        telemetry.addLine("Init Complete. Good Luck Drivers!!");
        telemetry.update();
    }

    @Override
    public void start() {
        schedule(autoRoutine());
    }

    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();

        telemetry.addData("X", follower.pose().x());
        telemetry.addData("Y", follower.pose().y());
        telemetry.addData("Heading", Math.toDegrees(follower.pose().heading()));
        telemetry.addData("Follower Mode", follower.mode());
        telemetry.addData("pollenVelocity", gus.getPollenVelocity());
        telemetry.addData("nectarVelocity", gus.getNectarVelocity());
        telemetry.addData("Pollen", gus.getPollenVelocity() > 1600 ? "READY" : "NOT_READY");
        telemetry.addData("Nectar", gus.getNectarVelocity() > 1600 ? "READY" : "NOT_READY");
        telemetry.update();
    }

    @Override
    public void stop() {
        OpModeStorage.autonomousEndPose = follower.pose();
    }
}
