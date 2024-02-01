package mod.chloeprime.thirdpersonshooting.mixin.client;

import com.tac.guns.client.handler.ShootingHandler;
import mod.chloeprime.thirdpersonshooting.client.RotationFixer;
import mod.chloeprime.thirdpersonshooting.client.TpsPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ShootingHandler.class, remap = false)
public class MixinTacShootingHandler {
    @Inject(
            method = "fire",
            at = @At(value = "INVOKE", target = "Lcom/tac/guns/network/PacketHandler;getPlayChannel()Lnet/minecraftforge/network/simple/SimpleChannel;")
    )
    private void prepareRotation(Player player, ItemStack heldItem, CallbackInfo ci) {
        RotationFixer.fixRotationToLocalPlayer();
    }

    @Redirect(
            method = "fire",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getViewXRot(F)F", remap = true),
            remap = false
    )
    private float aimAtLookX(Player player, float partial) {
        if (RotationFixer.ready) {
            return RotationFixer.xRot;
        }
        return player.getViewXRot(partial);
    }

    @Redirect(
            method = "fire",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getViewYRot(F)F", remap = true),
            remap = false
    )
    private float aimAtLookY(Player player, float partial) {
        if (RotationFixer.ready) {
            return player.isPassenger() ? player.getViewYRot(partial) : RotationFixer.yRot;
        }
        return player.getViewYRot(partial);
    }

    @Inject(
            method = "fire",
            at = @At(value = "INVOKE", target = "Lcom/tac/guns/network/PacketHandler;getPlayChannel()Lnet/minecraftforge/network/simple/SimpleChannel;", remap = false),
            remap = false
    )
    private void inject_beforeShoot(Player player, ItemStack heldItem, CallbackInfo ci) {
        if (player instanceof TpsPlayer tpsPlayer) {
            tpsPlayer.TPSMOD_applyRotation(TpsPlayer.ApplyRotationMethod.APPLY_IMMEDIATELY);
        }
    }
}
