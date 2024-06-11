package mod.chloeprime.thirdpersonshooting.client;

import com.github.exopandora.shouldersurfing.api.plugin.IShoulderSurfingPlugin;
import com.github.exopandora.shouldersurfing.api.plugin.IShoulderSurfingRegistrar;
import com.tacz.guns.api.item.IGun;

@SuppressWarnings("unused")
public class TpsSsPlugin implements IShoulderSurfingPlugin {
    @Override
    public void register(IShoulderSurfingRegistrar registrar) {
        registrar.registerAdaptiveItemCallback(stack -> {
            if (!(stack.getItem() instanceof IGun)) {
                return false;
            }
            return LocalGunner.isAiming();
        });
    }
}
