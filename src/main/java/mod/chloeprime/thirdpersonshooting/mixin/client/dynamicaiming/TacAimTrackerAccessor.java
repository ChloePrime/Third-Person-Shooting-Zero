package mod.chloeprime.thirdpersonshooting.mixin.client.dynamicaiming;

import com.tac.guns.common.AimingManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = AimingManager.AimTracker.class, remap = false)
public interface TacAimTrackerAccessor {
    @Accessor
    void setCurrentAim(double currentAim);

    @Accessor
    void setPreviousAim(double previousAim);

    @Accessor
    void setLerpProgress(double lerpProgress);
}
