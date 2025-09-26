package mod.chloeprime.thirdpersonshooting.client.eventhandler;

import com.tacz.guns.client.event.CameraSetupEvent;
import mod.chloeprime.thirdpersonshooting.mixin.client.MixinTacRecoilHandler;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.Optional;

@EventBusSubscriber(Dist.CLIENT)
public class TacCameraPriorityFix {
    /**
     * @see MixinTacRecoilHandler#fixNoRecoilOnSsBug
     */
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onCameraRotate(ViewportEvent.ComputeCameraAngles event) {
        var player = Optional.ofNullable(Minecraft.getInstance().player);
        float xr, yr;
        if (player.isPresent()) {
            xr = player.get().getXRot();
            yr = player.get().getYRot();
        } else {
            xr = yr = 0;
        }
        var isFixCall = IS_FIX_CALL.get();
        try {
            isFixCall.setTrue();
            CameraSetupEvent.applyCameraRecoil(event);
        } finally {
            isFixCall.setFalse();
        }
        player.ifPresent(pl -> {
            var drx = pl.getXRot() - xr;
            var dry = pl.getYRot() - yr;
            pl.setXRot(xr);
            pl.setYRot(yr);
            pl.turn(dry / 0.15F, drx / 0.15F);
        });
    }

    /**
     * @since 1.21.1-5.1.1
     */
    public static final ThreadLocal<MutableBoolean> IS_FIX_CALL = ThreadLocal.withInitial(MutableBoolean::new);
}
