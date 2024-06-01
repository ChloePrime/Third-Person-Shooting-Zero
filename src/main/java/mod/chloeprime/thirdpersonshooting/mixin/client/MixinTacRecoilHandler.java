package mod.chloeprime.thirdpersonshooting.mixin.client;

import com.tacz.guns.client.event.CameraSetupEvent;
import mod.chloeprime.thirdpersonshooting.client.eventhandler.TacCameraPriorityFix;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.EventPriority;
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
        if (event.getPhase() == EventPriority.NORMAL) {
            ci.cancel();
        }
    }
}
