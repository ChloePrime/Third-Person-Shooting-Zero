package mod.chloeprime.thirdpersonshooting.client.eventhandler;

import com.github.exopandora.shouldersurfing.api.client.IShoulderSurfing;
import com.tacz.guns.api.event.common.GunShootEvent;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import java.util.Optional;

@EventBusSubscriber(Dist.CLIENT)
public class ClientGunFireEvent {
    @SubscribeEvent
    public static void onClientFire(GunShootEvent event) {
        if (event.getLogicalSide().isServer()) {
            return;
        }
        if (!IShoulderSurfing.getInstance().isShoulderSurfing()) {
            return;
        }

        var camera = IShoulderSurfing.getInstance().getCamera();
        Optional.ofNullable(Minecraft.getInstance().player).ifPresent(gunner -> {
            gunner.setYRot(camera.getYRot());
            gunner.setXRot(camera.getXRot());
        });
    }
}
