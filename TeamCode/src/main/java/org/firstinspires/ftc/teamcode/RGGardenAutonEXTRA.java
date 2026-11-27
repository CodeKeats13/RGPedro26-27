package org.firstinspires.ftc.teamcode;

import static com.pedropathing.api.Paths.curve;
import static com.pedropathing.api.Paths.line;
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
public class RGGardenAutonEXTRA extends OpMode {
    private Follower follower;
    private GusBot gus;
    private final PoseFactory p = PoseFactory.degrees();

    private final Pose startShootPose = p.of(61.5, 9, 270);
    private final Pose gardenPose = p.of(10, 10, 180);
    private final Pose gardenControlPose = p.of(64, 23, 0);
    private final Pose flowerPose = p.of(13, 55, 180);
    private final Pose flowerControlPose = p.of(25, 56, 0);
    private final Pose Shoot2Pose = p.of(31, 126, 120);
    private final Pose parkControlPose = p.of(46, 65, 0);
    private final Pose parkPose = p.of(10, 93, 0);
    private Path shootToGarden() {
        return curve(startShootPose, gardenControlPose, gardenPose).linear(startShootPose, gardenPose);
    }

    private Path gardenToShoot2() {
        return line(gardenPose,Shoot2Pose).linear(gardenPose, Shoot2Pose);
    }

    private Path shoot2ToFlower() {
        return line(Shoot2Pose,flowerPose).linear(Shoot2Pose, flowerPose);
    }

    private Path flowerToShoot2() {
        return line(flowerPose, Shoot2Pose).linear(flowerPose, Shoot2Pose);
    }

    private Path shoot2ToPark() {
        return line(Shoot2Pose, parkPose).linear(Shoot2Pose, parkPose);
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
                waitMs(2000),
                shoot,
                instant(() -> {gus.intake();}),
                race(follow(follower, shootToGarden()),
                     waitMs(3000)),
                waitMs(2000),
                shootPrep,
                race(follow(follower, gardenToShoot2()),
                        waitMs(3000)),
                shoot,
                instant(() -> {gus.intake();}),
                race(follow(follower, shoot2ToFlower()),
                        waitMs(3000)),
                waitMs(3000),
                shootPrep,
                follow(follower, flowerToShoot2()),
                shoot,
                instant(() -> {gus.shootersOff();}),
                follow(follower, shoot2ToPark())

        );
    }

    @Override
    public void init() {
        gus = new GusBot();
        gus.init(hardwareMap);
        gus.openNectar();
        Scheduler.reset();

        follower = Constants.create(hardwareMap);
        follower.setPose(startShootPose);
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
