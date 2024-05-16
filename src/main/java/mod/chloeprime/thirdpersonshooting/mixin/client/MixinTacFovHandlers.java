package mod.chloeprime.thirdpersonshooting.mixin.client;

import com.tac.guns.client.handler.AimingHandler;
import com.tac.guns.client.handler.MovementAdaptationsHandler;
import com.github.exopandora.shouldersurfing.config.Perspective;
import net.minecraft.client.CameraType;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static com.github.exopandora.shouldersurfing.config.Perspective.FIRST_PERSON;
import static com.github.exopandora.shouldersurfing.config.Perspective.SHOULDER_SURFING;

@Mixin(value = {AimingHandler.class, MovementAdaptationsHandler.class}, remap = false)
public class MixinTacFovHandlers {
    /**
     * 允许 TAC 在越肩视角下进行 FOV 变换
     */
    @Redirect(
            method = "onFovUpdate",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Options;getCameraType()Lnet/minecraft/client/CameraType;", remap = true),
            remap = false
    )
    private CameraType fakeCameraType(Options instance) {
        var current = Perspective.current();
        return (current == FIRST_PERSON || current == SHOULDER_SURFING)
                ? CameraType.FIRST_PERSON
                : instance.getCameraType();
    }
}
