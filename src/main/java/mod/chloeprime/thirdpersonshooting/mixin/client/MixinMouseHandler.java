package mod.chloeprime.thirdpersonshooting.mixin.client;

import mod.chloeprime.thirdpersonshooting.client.internal.TurnWarnable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(value = MouseHandler.class, priority = 995)
public class MixinMouseHandler {
    @Shadow @Final private Minecraft minecraft;

    @Inject(
            method = "turnPlayer",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V")
    )
    private void turn(CallbackInfo ci) {
        ((TurnWarnable) Objects.requireNonNull(this.minecraft.player)).thirdPersonShooting$warnTurning();
    }
}
