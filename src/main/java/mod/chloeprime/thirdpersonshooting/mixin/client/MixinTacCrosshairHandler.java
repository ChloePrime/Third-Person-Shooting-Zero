package mod.chloeprime.thirdpersonshooting.mixin.client;

import com.github.exopandora.shouldersurfing.client.ShoulderInstance;
import com.github.exopandora.shouldersurfing.config.Perspective;
import com.tacz.guns.api.client.gameplay.IClientPlayerGunOperator;
import com.tacz.guns.client.animation.internal.GunAnimationStateMachine;
import com.tacz.guns.client.event.RenderCrosshairEvent;
import net.minecraft.client.CameraType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.github.exopandora.shouldersurfing.config.Perspective.FIRST_PERSON;
import static com.github.exopandora.shouldersurfing.config.Perspective.SHOULDER_SURFING;

@Mixin(value = RenderCrosshairEvent.class, remap = false)
public class MixinTacCrosshairHandler {
    @Redirect(
            method = "renderCrosshair",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/CameraType;isFirstPerson()Z", remap = true),
            remap = false, require = 0
    )
    private static boolean fakeIsFirstPerson(CameraType ignored) {
        var current = Perspective.current();
        return (current == FIRST_PERSON || current == SHOULDER_SURFING);
    }

    @Redirect(
            method = "onRenderOverlay",
            at = @At(value = "INVOKE", target = "Lcom/tacz/guns/api/client/gameplay/IClientPlayerGunOperator;getClientAimingProgress(F)F")
    )
    private static float neverHideCrosshairWhenSs(IClientPlayerGunOperator gunner, float partialTick) {
        if (ShoulderInstance.getInstance().doShoulderSurfing()) {
            return 0;
        }
        return gunner.getClientAimingProgress(partialTick);
    }

    @Mixin(value = GunAnimationStateMachine.class, remap = false)
    public static class NeverHideCrosshairWhenSs {
        @Inject(method = "shouldHideCrossHair", at = @At("HEAD"), cancellable = true)
        private void neverHideCrosshairWhenSs(CallbackInfoReturnable<Boolean> cir) {
            if (ShoulderInstance.getInstance().doShoulderSurfing()) {
                cir.setReturnValue(false);
            }
        }
    }
}
