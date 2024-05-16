package mod.chloeprime.thirdpersonshooting.client;

import com.github.exopandora.shouldersurfing.api.IShoulderSurfingPlugin;
import com.github.exopandora.shouldersurfing.api.IShoulderSurfingRegistrar;
import com.tac.guns.client.handler.AimingHandler;
import com.tac.guns.item.GunItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.KeyboardInput;

@SuppressWarnings("unused")
public class TpsSsPlugin implements IShoulderSurfingPlugin {
    @Override
    public void register(IShoulderSurfingRegistrar registrar) {
        registrar.registerAdaptiveItemCallback(stack -> {
            if (!(stack.getItem() instanceof GunItem)) {
                return false;
            }
            return overrideIsAiming || AimingHandler.get().isAiming();
        });
    }

    public static boolean overrideIsAiming;
    public static final Input DUMMY_INPUT = new KeyboardInput(Minecraft.getInstance().options);
}
