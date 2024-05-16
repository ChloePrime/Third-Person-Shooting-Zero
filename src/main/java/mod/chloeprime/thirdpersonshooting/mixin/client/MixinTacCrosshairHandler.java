package mod.chloeprime.thirdpersonshooting.mixin.client;

import com.github.exopandora.shouldersurfing.config.Perspective;
import com.tac.guns.client.handler.CrosshairHandler;
import net.minecraft.client.CameraType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static com.github.exopandora.shouldersurfing.config.Perspective.FIRST_PERSON;
import static com.github.exopandora.shouldersurfing.config.Perspective.SHOULDER_SURFING;

@Mixin(value = CrosshairHandler.class, remap = false)
public class MixinTacCrosshairHandler {
    @Redirect(
            method = "onRenderOverlay",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/CameraType;isFirstPerson()Z", remap = true),
            remap = false, require = 0
    )
    private boolean fakeIsFirstPerson(CameraType en) {
        var current = Perspective.current();
        return (current == FIRST_PERSON || current == SHOULDER_SURFING);
    }
}
