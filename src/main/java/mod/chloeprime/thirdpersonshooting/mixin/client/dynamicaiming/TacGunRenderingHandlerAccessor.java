package mod.chloeprime.thirdpersonshooting.mixin.client.dynamicaiming;

import com.tac.guns.client.handler.GunRenderingHandler;
import com.tac.guns.util.math.SecondOrderDynamics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = GunRenderingHandler.class, remap = false)
public interface TacGunRenderingHandlerAccessor {
    @Accessor
    SecondOrderDynamics getAimingDynamics();
}
