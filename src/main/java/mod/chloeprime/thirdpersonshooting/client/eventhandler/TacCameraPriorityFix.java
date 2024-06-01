package mod.chloeprime.thirdpersonshooting.client.eventhandler;

import com.tacz.guns.client.event.CameraSetupEvent;
import mod.chloeprime.thirdpersonshooting.mixin.client.MixinTacRecoilHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

@Mod.EventBusSubscriber(Dist.CLIENT)
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
        CameraSetupEvent.applyCameraRecoil(event);
        player.ifPresent(pl -> {
            var drx = pl.getXRot() - xr;
            var dry = pl.getYRot() - yr;
            pl.setXRot(xr);
            pl.setYRot(yr);
            pl.turn(dry / 0.15F, drx / 0.15F);
        });
    }
}
