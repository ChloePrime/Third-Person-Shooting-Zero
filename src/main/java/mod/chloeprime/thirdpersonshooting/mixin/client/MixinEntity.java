package mod.chloeprime.thirdpersonshooting.mixin.client;

import com.teamderpy.shouldersurfing.client.ShoulderInstance;
import mod.chloeprime.thirdpersonshooting.client.TpsPlayer;
import mod.chloeprime.thirdpersonshooting.client.internal.TurnWarnable;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class MixinEntity implements TurnWarnable {
    @Unique private boolean thirdPersonShooting$willTurn;

    @Inject(method = "turn", at = @At("HEAD"), cancellable = true)
    private void virtualTurnIfIsPlayer(double yRot, double xRot, CallbackInfo ci) {
        if (!thirdPersonShooting$willTurn) {
            return;
        }
        thirdPersonShooting$willTurn = false;

        if (!ShoulderInstance.getInstance().doShoulderSurfing()) {
            return;
        }

        if ((Object)this instanceof LocalPlayer player) {
            if (player.isFallFlying()) {
                return;
            }
            ((TpsPlayer)player).TPSMOD_turnVirtual(yRot, xRot);
            ((TpsPlayer)player).TPSMOD_applyRotation(TpsPlayer.howToApplyRotation(player));
            ci.cancel();
        }
    }

    @Override
    public void thirdPersonShooting$warnTurning() {
        thirdPersonShooting$willTurn = true;
    }
}
