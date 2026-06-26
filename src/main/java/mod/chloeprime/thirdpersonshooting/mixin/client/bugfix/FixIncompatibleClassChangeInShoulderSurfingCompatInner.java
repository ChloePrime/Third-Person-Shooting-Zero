package mod.chloeprime.thirdpersonshooting.mixin.client.bugfix;

import com.github.exopandora.shouldersurfing.api.client.IShoulderSurfing;
import com.github.exopandora.shouldersurfing.api.client.Perspective;
import com.tacz.guns.compat.shouldersurfing.ShoulderSurfingCompatInner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = ShoulderSurfingCompatInner.class, remap = false, priority = 0)
public class FixIncompatibleClassChangeInShoulderSurfingCompatInner {
    /**
     * @author ChloePrime
     * @reason To fix incompatible class change
     */
    @Overwrite
    public static boolean showCrosshair() {
        return Perspective.current() == Perspective.SHOULDER_SURFING && !IShoulderSurfing.getInstance().isFreeLooking();
    }
}
