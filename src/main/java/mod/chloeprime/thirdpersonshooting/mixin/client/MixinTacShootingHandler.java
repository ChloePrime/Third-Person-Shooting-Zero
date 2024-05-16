package mod.chloeprime.thirdpersonshooting.mixin.client;

import com.github.exopandora.shouldersurfing.client.ShoulderInstance;
import com.tac.guns.client.handler.ShootingHandler;
import mod.chloeprime.thirdpersonshooting.client.TpsSsPlugin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ShootingHandler.class, remap = false)
public class MixinTacShootingHandler {
    @Inject(
            method = "fire",
            at = @At(value = "INVOKE", target = "Lcom/tac/guns/network/PacketHandler;getPlayChannel()Lnet/minecraftforge/network/simple/SimpleChannel;")
    )
    private void prepareRotation(Player player, ItemStack heldItem, CallbackInfo ci) {
        try {
            TpsSsPlugin.overrideIsAiming = true;
            ShoulderInstance.getInstance().onMovementInputUpdate(TpsSsPlugin.DUMMY_INPUT);
            player.setYHeadRot(player.getYRot());
        } finally {
            TpsSsPlugin.overrideIsAiming = false;
        }
    }
}
