package mod.chloeprime.thirdpersonshooting.mixin.client;

import com.teamderpy.shouldersurfing.config.Perspective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import static com.teamderpy.shouldersurfing.config.Perspective.FIRST_PERSON;
import static com.teamderpy.shouldersurfing.config.Perspective.SHOULDER_SURFING;

/**
 * 移除第二人称和顶头第三人称，只保留第一人称和过肩视角
 */
@Mixin(value = Perspective.class, remap = false, priority = 50)
public abstract class MixinSsPerspective {

    /**
     * @author ChloePrime
     * @reason Remove 2PP and Melee TPP.
     */
    @Overwrite(remap = false)
    @SuppressWarnings("ConstantValue")
    public Perspective next() {
        return (Object)this == FIRST_PERSON ? SHOULDER_SURFING : FIRST_PERSON;
    }
}
