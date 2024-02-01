package mod.chloeprime.thirdpersonshooting.mixin.client;

import com.teamderpy.shouldersurfing.client.ShoulderHelper;
import mod.chloeprime.thirdpersonshooting.client.TpsPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ShoulderHelper.class, remap = false)
public class MixinSsShoulderHelper {
    @Redirect(
            method = "shoulderSurfingLook",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getViewVector(F)Lnet/minecraft/world/phys/Vec3;", remap = true)
    )
    private static Vec3 getRealViewVector(Entity entity, float pPartialTicks) {
        if (entity instanceof TpsPlayer player) {
            return ((EntityAccessor) entity).invokeCalculateViewVector(player.TPSMOD_getVirtualRotX(pPartialTicks), player.TPSMOD_getVirtualRotY(pPartialTicks));
        }
        return entity.getViewVector(pPartialTicks);
    }
}
