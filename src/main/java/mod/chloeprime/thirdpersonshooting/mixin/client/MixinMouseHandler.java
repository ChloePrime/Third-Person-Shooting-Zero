package mod.chloeprime.thirdpersonshooting.mixin.client;

import mod.chloeprime.thirdpersonshooting.client.TpsPlayer;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MouseHandler.class)
public class MixinMouseHandler {
    @Redirect(
            method = "turnPlayer",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V")
    )
    private void turn(LocalPlayer player, double yRot, double xRot) {
        ((TpsPlayer)player).TPSMOD_turnVirtual(yRot, xRot);
        ((TpsPlayer)player).TPSMOD_applyRotation(TpsPlayer.howToApplyRotation(player));
    }
}
