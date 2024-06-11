package mod.chloeprime.thirdpersonshooting.client.eventhandler;

import com.github.exopandora.shouldersurfing.api.client.ShoulderSurfing;
import com.tacz.guns.api.event.common.GunShootEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientGunFireEvent {
    @SubscribeEvent
    public static void onClientFire(GunShootEvent event) {
        if (event.getLogicalSide().isServer()) {
            return;
        }
        if (!ShoulderSurfing.getInstance().isShoulderSurfing()) {
            return;
        }

        var camera = ShoulderSurfing.getInstance().getCamera();
        Optional.ofNullable(Minecraft.getInstance().player).ifPresent(gunner -> {
            gunner.setYRot(camera.getYRot());
            gunner.setXRot(camera.getXRot());
        });
    }
}
