package mod.chloeprime.thirdpersonshooting.mixin.client.dynamicaiming;

import com.tac.guns.client.handler.AimingHandler;
import mod.chloeprime.thirdpersonshooting.client.internal.OverrideableAimingHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = AimingHandler.class, remap = false)
public class MixinTacAimingHandler implements OverrideableAimingHandler {
    private @Unique boolean tp_shooting$overrideAiming = false;

    @Inject(
            method = "isAiming",
            at = @At(value = "FIELD", target = "Lcom/tac/guns/client/Keys;AIM_HOLD:Lnet/minecraft/client/KeyMapping;"),
            cancellable = true)
    private void injectOverrideToggleAiming(CallbackInfoReturnable<Boolean> cir) {
        if (tp_shooting$overrideAiming) {
            cir.setReturnValue(true);
        }
    }

    @Override
    public boolean tp_shooting$isOverrideAiming() {
        return tp_shooting$overrideAiming;
    }

    @Override
    public void tp_shooting$setOverrideAiming(boolean value) {
        tp_shooting$overrideAiming = value;
    }
}
