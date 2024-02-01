package mod.chloeprime.thirdpersonshooting.mixin.client;

import com.tac.guns.client.handler.AimingHandler;
import com.tac.guns.common.AimingManager;
import com.tac.guns.util.GunModifierHelper;
import com.teamderpy.shouldersurfing.client.ShoulderInstance;
import com.teamderpy.shouldersurfing.config.Perspective;
import mod.chloeprime.thirdpersonshooting.client.ClientConfig;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.FOVModifierEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.util.thread.EffectiveSide;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.teamderpy.shouldersurfing.config.Perspective.FIRST_PERSON;
import static mod.chloeprime.thirdpersonshooting.client.ClientConfig.CONSTANT_AIMING_FOV_SCALE;

@Mixin(value = AimingHandler.class, remap = false)
public class MixinTacAimingHandler {
    @Shadow private double normalisedAdsProgress;

    @Inject(
            method = "onRenderOverlay",
            at = @At(
                    value = "FIELD", opcode = Opcodes.PUTFIELD, target = "Lcom/tac/guns/client/handler/AimingHandler;normalisedAdsProgress:D",
                    shift = At.Shift.AFTER, remap = false),
            cancellable = true,
            remap = false
    )
    private void keepCrosshairOnNonFp(RenderGameOverlayEvent.PreLayer event, CallbackInfo ci) {
        if (Perspective.current() != FIRST_PERSON) {
            ci.cancel();
        }
    }

    @Redirect(
            method = "onFovUpdate",
            at = @At(value = "INVOKE", target = "Lnet/minecraftforge/client/event/FOVModifierEvent;setNewfov(F)V", remap = false),
            remap = false
    )
    private void changeSetToBlend(FOVModifierEvent e, float newFov) {
        float scale;
        if (CONSTANT_AIMING_FOV_SCALE.get() && ShoulderInstance.getInstance().doShoulderSurfing()) {
            float scalev = ClientConfig.CONSTANT_AIMING_FOV_SCALE_VALUE.get().floatValue();
            scale = Mth.lerp((float)normalisedAdsProgress, 1, scalev);
        } else {
            scale = newFov;
        }
        e.setNewfov(e.getNewfov() * scale);
    }

    @Mixin(value = AimingManager.AimTracker.class, remap = false)
    public static class MixinAimTracker {
        @Redirect(
                method = "handleAiming",
                at = @At(value = "INVOKE", target = "Lcom/tac/guns/util/GunModifierHelper;getModifiedAimDownSightSpeed(Lnet/minecraft/world/item/ItemStack;D)D", remap = false),
                remap = false
        )
        private double lockSpeedAtSsMode(ItemStack weapon, double speed) {
            if ((Object)this != ((AimingHandlerAccessor) AimingHandler.get()).getLocalTracker()) {
                return GunModifierHelper.getModifiedAimDownSightSpeed(weapon, speed);
            }
            if (EffectiveSide.get().isServer() ||
                    !CONSTANT_AIMING_FOV_SCALE.get() ||
                    !ShoulderInstance.getInstance().doShoulderSurfing()
            ) {
                return GunModifierHelper.getModifiedAimDownSightSpeed(weapon, speed);
            }

            return 1;
        }
    }
}
