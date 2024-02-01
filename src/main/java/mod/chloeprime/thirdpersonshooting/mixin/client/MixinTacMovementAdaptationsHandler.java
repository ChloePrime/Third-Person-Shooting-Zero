package mod.chloeprime.thirdpersonshooting.mixin.client;

import com.tac.guns.client.handler.MovementAdaptationsHandler;
import net.minecraftforge.client.event.FOVModifierEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = MovementAdaptationsHandler.class, remap = false)
public class MixinTacMovementAdaptationsHandler {
    @Redirect(
            method = "onFovUpdate",
            at = @At(value = "INVOKE", target = "Lnet/minecraftforge/client/event/FOVModifierEvent;setNewfov(F)V", remap = false),
            remap = false
    )
    private void changeSetToBlend(FOVModifierEvent e, float newFov) {
        e.setNewfov(e.getNewfov() * newFov);
    }
}
