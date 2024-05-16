package mod.chloeprime.thirdpersonshooting.mixin.client;

import com.tac.guns.client.handler.AimingHandler;
import com.tac.guns.common.AimingManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = AimingHandler.class, remap = false)
public interface TacAimingHandlerAccessor {
    @Accessor
    AimingManager.AimTracker getLocalTracker();

    @Accessor
    void setNormalisedAdsProgress(double normalisedAdsProgress);

    @Accessor
    void setNewProgress(double newProgress);

    @Accessor
    void setOldProgress(double oldProgress);
}
