package mod.chloeprime.thirdpersonshooting;

import com.mojang.logging.LogUtils;
import mod.chloeprime.thirdpersonshooting.client.ClientConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(ThirdPersonShooting.MOD_ID)
public class ThirdPersonShooting {
    public static final String MOD_ID = "tp_shooting";
    private static final Logger LOGGER = LogUtils.getLogger();

    public ThirdPersonShooting() {
        if (ModList.get().isLoaded("betterthirdperson")) {
            LOGGER.error("""
                    \n[FATAL]
                    Third Person Shooting is made for Shoulder Surfing Reload, not for or compatible with Better Third Person.
                    This mod will not work and very likely the game will crash.""");
        }

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
        MinecraftForge.EVENT_BUS.register(this);
    }
}
