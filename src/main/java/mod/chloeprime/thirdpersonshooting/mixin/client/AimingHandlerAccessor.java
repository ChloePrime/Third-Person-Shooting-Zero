package mod.chloeprime.thirdpersonshooting.mixin.client;

import com.tac.guns.client.handler.AimingHandler;
import com.tac.guns.common.AimingManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = AimingHandler.class, remap = false)
public interface AimingHandlerAccessor {
    @Accessor
    AimingManager.AimTracker getLocalTracker();
}
