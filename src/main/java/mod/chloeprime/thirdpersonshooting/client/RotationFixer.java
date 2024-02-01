package mod.chloeprime.thirdpersonshooting.client;

import com.teamderpy.shouldersurfing.client.ShoulderHelper;
import com.teamderpy.shouldersurfing.client.ShoulderInstance;
import com.teamderpy.shouldersurfing.config.Config;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

import static java.lang.Math.*;

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
            var delta = hit.target().subtract(hit.start());
            var distance = 1 / Mth.fastInvSqrt(delta.lengthSqr());

            xRot = Mth.wrapDegrees((float) toDegrees(-(asin(delta.y / distance))));
            yRot = Mth.wrapDegrees((float) toDegrees(atan2(delta.z, delta.x)) - 90);
            ready = true;
        }
    }

    private static TpsHitResult traceBlock(Camera camera, Entity player, double reach) {
        var partials = Minecraft.getInstance().getFrameTime();
        var blockHit = traceBlocksIgnoreGrass(camera, player, ClipContext.Fluid.NONE, reach, partials, true);
        if (blockHit == null) {
            return null;
        }
        var start = player.getEyePosition();
        var target = blockHit.getLocation();
        return new TpsHitResult(start, target, target.distanceToSqr(start));
    }

    @Nullable
    @SuppressWarnings("DataFlowIssue")
    public static BlockHitResult traceBlocksIgnoreGrass(Camera camera, Entity entity, ClipContext.Fluid fluidContext, double distance, float partialTick, boolean shoulderSurfing) {
        Vec3 eyePosition = entity.getEyePosition(partialTick);
        Vec3 view;
        Vec3 to;
        if (shoulderSurfing) {
            ShoulderHelper.ShoulderLook look = ShoulderHelper.shoulderSurfingLook(camera, entity, partialTick, distance * distance);
            view = eyePosition.add(look.headOffset());
            to = look.traceEndPos();
            return entity.level.clip(new ClipContext(view, to, ClipContext.Block.COLLIDER, fluidContext, entity));
        } else {
            view = entity.getViewVector(partialTick);
            to = eyePosition.add(view.scale(distance));
            return entity.level.clip(new ClipContext(eyePosition, to, ClipContext.Block.COLLIDER, fluidContext, entity));
        }
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
