package mod.chloeprime.thirdpersonshooting.client;

import com.github.exopandora.shouldersurfing.api.IShoulderSurfingPlugin;
import com.github.exopandora.shouldersurfing.api.IShoulderSurfingRegistrar;
import com.tacz.guns.api.item.IGun;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.KeyboardInput;

@SuppressWarnings("unused")
public class TpsSsPlugin implements IShoulderSurfingPlugin {
    @Override
    public void register(IShoulderSurfingRegistrar registrar) {
        registrar.registerAdaptiveItemCallback(stack -> {
            if (!(stack.getItem() instanceof IGun)) {
                return false;
            }
            return overrideIsAiming || LocalGunner.isAiming();
        });
    }

    public static boolean overrideIsAiming;
    public static final Input DUMMY_INPUT = new KeyboardInput(Minecraft.getInstance().options);
}
