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
        c.frontRightDirection.set(DcMotorSimple.Direction.REVERSE);
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
                Controller primaryTranslationalForward = Controller.proportional(0.3741522335153552);
                Controller secondaryTranslationalForward = Controller.proportional(0.13823925275211704);
                Controller primaryTranslationalLateral = Controller.proportional(0.600725357880061);
                Controller secondaryTranslationalLateral = Controller.proportional(0.22195196805949197);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.01658928089890781));
                c.brake.set(Controller.proportionalFeedforward(0.014100888764071638));

                c.headingFeedback.set(Controller.proportional(5.771497784520806));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.06427526078239049, 0.007867041935382544));

                c.linearBrakeCoefficients.set(Matrix.diag(0.09454420049740417, 0.04985920782841863));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0016214825287335969, 0.00224103559936954));

                c.maxAchievableForwardVelocity.set(60.977921369356196);
                c.maxAchievableStrafeVelocity.set(13.135486577869349);
                c.naturalForwardDeceleration.set(26.73953486799459);
                c.naturalStrafeDeceleration.set(71.92336345692792);
            }
    );
}