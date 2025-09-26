package mod.chloeprime.thirdpersonshooting;

import com.mojang.logging.LogUtils;
import mod.chloeprime.thirdpersonshooting.client.ClientConfig;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(ThirdPersonShooting.MOD_ID)
public class ThirdPersonShooting {
    public static final String MOD_ID = "tp_shooting";
    private static final Logger LOGGER = LogUtils.getLogger();

    public ThirdPersonShooting(IEventBus ignoredModBus, ModContainer container) {
        container.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
    }
}
