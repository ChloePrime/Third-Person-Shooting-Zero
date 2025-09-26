package mod.chloeprime.thirdpersonshooting.mixin.client;

import com.tacz.guns.client.event.CameraSetupEvent;
import mod.chloeprime.thirdpersonshooting.client.eventhandler.TacCameraPriorityFix;
import net.neoforged.neoforge.client.event.ViewportEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = CameraSetupEvent.class, remap = false)
public class MixinTacRecoilHandler {
    /**
     * @see TacCameraPriorityFix#onCameraRotate
     */
    @Inject(method = "applyCameraRecoil", at = @At("HEAD"), cancellable = true)
    private static void fixNoRecoilOnSsBug(ViewportEvent.ComputeCameraAngles event, CallbackInfo ci) {
        if (TacCameraPriorityFix.IS_FIX_CALL.get().isFalse()) {
            ci.cancel();
        }
    }
}
