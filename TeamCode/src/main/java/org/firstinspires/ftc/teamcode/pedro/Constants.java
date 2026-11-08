package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static Follower create(HardwareMap h) {
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }

    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("DriveFL");
        c.frontRightName.set("DriveFR");
        c.backLeftName.set("DriveBL");
        c.backRightName.set("DriveBR");
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
    });

    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("Pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(-0.4196341206708292);
        c.yPodOffset.set(0.16538191968061794);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.36808603525404254);
                Controller secondaryTranslationalForward = Controller.proportional(0.13599795458636482);
                Controller primaryTranslationalLateral = Controller.proportional(0.8103562904169302);
                Controller secondaryTranslationalLateral = Controller.proportional(0.2994049961901846);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.017901129708371412));
                c.brake.set(Controller.proportionalFeedforward(0.0152159602521157));

                c.headingFeedback.set(Controller.proportional(6.522648436718915));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.050176099599039695, 0.010137468114306025));

                c.linearBrakeCoefficients.set(Matrix.diag(0.06761952091417107, 0.07875096159715156));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.002444664476354864, 0.0015861310556559984));

                c.maxAchievableForwardVelocity.set(58.45371116533356);
                c.maxAchievableStrafeVelocity.set(42.93183197353027);
                c.naturalForwardDeceleration.set(30.37444267593196);
                c.naturalStrafeDeceleration.set(66.13289171744098);
            }
    );
}