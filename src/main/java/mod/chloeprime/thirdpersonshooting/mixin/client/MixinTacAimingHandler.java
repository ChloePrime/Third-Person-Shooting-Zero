package mod.chloeprime.thirdpersonshooting.mixin.client;

import com.github.exopandora.shouldersurfing.client.ShoulderInstance;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.client.event.CameraSetupEvent;
import com.tacz.guns.client.gameplay.LocalPlayerAim;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.util.thread.EffectiveSide;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static mod.chloeprime.thirdpersonshooting.client.ClientConfig.CONSTANT_AIMING_ZOOM_SCALE;
import static mod.chloeprime.thirdpersonshooting.client.ClientConfig.CONSTANT_AIMING_ZOOM_SCALE_VALUE;

@Mixin(value = LocalPlayerAim.class, remap = false)
public class MixinTacAimingHandler {
    @Inject(method = "getAlphaProgress", at = @At("HEAD"), cancellable = true)
    private void lockSpeedAtSsMode(GunData gunData, ItemStack mainhandItem, CallbackInfoReturnable<Float> cir) {
        if (EffectiveSide.get().isClient() &&
                CONSTANT_AIMING_ZOOM_SCALE.get() &&
                ShoulderInstance.getInstance().doShoulderSurfing()
        ) {
            cir.setReturnValue(1F);
        }
    }

    @Mixin(value = CameraSetupEvent.class, remap = false)
    public static class MixinFovEvent {
        @Redirect(
                method = {"applyScopeMagnification"},
                at = @At(
                        value = "INVOKE",
                        target = "Lcom/tacz/guns/api/item/IGun;getAimingZoom(Lnet/minecraft/world/item/ItemStack;)F"))
        private static float redirectFov(IGun gun, ItemStack stack) {
            if (CONSTANT_AIMING_ZOOM_SCALE.get() && ShoulderInstance.getInstance().doShoulderSurfing()) {
                return CONSTANT_AIMING_ZOOM_SCALE_VALUE.get().floatValue();
            }
            return gun.getAimingZoom(stack);
        }
    }
}
