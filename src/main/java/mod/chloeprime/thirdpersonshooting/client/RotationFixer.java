package mod.chloeprime.thirdpersonshooting.client;

import com.teamderpy.shouldersurfing.client.ShoulderHelper;
import com.teamderpy.shouldersurfing.client.ShoulderInstance;
import com.teamderpy.shouldersurfing.config.Config;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;

import static java.lang.Math.atan2;
import static java.lang.Math.toDegrees;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(Dist.CLIENT)
public class RotationFixer {
    public static float xRot, yRot;
    public static boolean ready = false;

    public static void fixRotationToLocalPlayer() {
        if (!ShoulderInstance.getInstance().doShoulderSurfing() || Config.CLIENT.getCrosshairType().isDynamic()) {
            ready = false;
            return;
        }

        var minecraft = Minecraft.getInstance();
        var mainCamera = minecraft.gameRenderer.getMainCamera();
        var player = Minecraft.getInstance().player;

        if (player == null) {
            ready = false;
            return;
        }

        var reach = minecraft.options.renderDistance * 16;
        var hit = min(traceBlock(mainCamera, player, reach), traceEntity(mainCamera, player, reach));
        if (hit != null) {
//            var delta0 = hit.target().subtract(hit.start());
//
//            var localFront = player.getLookAngle();
//            var localRight = UP.cross(localFront);
//
//            var dy = delta0.y + Config.CLIENT.getOffsetY();
//            var dx = delta0.x + localRight.x * Config.CLIENT.getOffsetX() - localFront.x * Config.CLIENT.getOffsetZ();
//            var dz = delta0.z + localRight.z * Config.CLIENT.getOffsetX() - localFront.z * Config.CLIENT.getOffsetZ();
//            var delta = new Vec3(dx, dy, dz);
            var delta = hit.target().subtract(hit.start());
            var distance = 1 / Mth.fastInvSqrt(delta.lengthSqr());

            xRot = Mth.wrapDegrees((float) toDegrees(-(atan2(delta.y, distance))));
            yRot = Mth.wrapDegrees((float) toDegrees(atan2(delta.z, delta.x)) - 90);
            ready = true;
        }
    }



    private static final Vec3 UP = new Vec3(0, 1, 0);

    private static TpsHitResult traceBlock(Camera camera, Entity player, double reach) {
        var partials = Minecraft.getInstance().getFrameTime();
        var blockHit = ShoulderHelper.traceBlocks(camera, player, ClipContext.Fluid.NONE, reach, partials, true);
        if (blockHit == null) {
            return null;
        }
        var start = player.getEyePosition();
        var target = blockHit.getLocation();
        return new TpsHitResult(start, target, target.distanceToSqr(start));
    }

    private static TpsHitResult traceEntity(Camera camera, Entity player, double reach) {
        var partials = Minecraft.getInstance().getFrameTime();
        var entityHit = ShoulderHelper.traceEntities(camera, player, reach, partials, true);
        if (entityHit == null) {
            return null;
        }
        var start = player.getEyePosition();
        var target = entityHit.getLocation();
        return new TpsHitResult(start, target, target.distanceToSqr(start));
    }

    private static TpsHitResult min(TpsHitResult a, TpsHitResult b) {
        if (b == null && a == null) {
            return null;
        }
        if (b == null) return a;
        if (a == null) return b;
        return a.distanceSq() < b.distanceSq() ? a : b;
    }
}
