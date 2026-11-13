package org.firstinspires.ftc.teamcode;

import static com.pedropathing.api.Paths.curve;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.instant;
import static com.pedropathing.ivy.commands.Commands.waitMs;
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
public class RGFlowerAuton extends OpMode {
    private Follower follower;
    private GusBot gus;
    private final PoseFactory p = PoseFactory.degrees();

    private final Pose startPose = p.of(61.5, 135, 90);
    private final Pose flowerPose = p.of(53, 135, 90);
    private final Pose flowerControlPose = p.of(50, 90, 0);
    private final Pose parkControlPose = p.of(50, 103, 0);
    private final Pose parkPose = p.of(10, 93, 0);
    private Path Flower() {
        return curve(startPose, flowerControlPose, flowerPose).linear(startPose, flowerPose);
    }
    private Path Park() {
        return curve(flowerPose, parkControlPose, parkPose).linear(flowerPose, parkPose);
    }


    private Command autoRoutine() {
        return sequential(
                instant(() -> {gus.shootersOn();}),
                waitMs(12000),
                instant(() -> {gus.shoot();}),
                waitMs(2000),
                instant(() -> {gus.shootersOff();}),
                instant(() -> {gus.closeGates();}),
                instant(() -> {gus.intake();}),
                follow(follower, Flower()),
                waitMs(4000),
                instant(() -> {gus.intakeOff();}),
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
        telemetry.update();
    }

    @Override
    public void stop() {
        OpModeStorage.autonomousEndPose = follower.pose();
    }
}
