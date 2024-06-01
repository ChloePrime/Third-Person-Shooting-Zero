package mod.chloeprime.thirdpersonshooting.client.eventhandler;

import com.github.exopandora.shouldersurfing.client.ShoulderInstance;
import com.tacz.guns.api.event.common.GunShootEvent;
import mod.chloeprime.thirdpersonshooting.client.TpsSsPlugin;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientGunFireEvent {
    @SubscribeEvent
    public static void onClientFire(GunShootEvent event) {
        if (event.getLogicalSide().isServer()) {
            return;
        }
        try {
            TpsSsPlugin.overrideIsAiming = true;
            ShoulderInstance.getInstance().onMovementInputUpdate(TpsSsPlugin.DUMMY_INPUT);
            event.getShooter().setYHeadRot(event.getShooter().getYRot());
        } finally {
            TpsSsPlugin.overrideIsAiming = false;
        }
    }
}
