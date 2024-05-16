package mod.chloeprime.thirdpersonshooting.mixin.client.dynamicaiming;

import mod.chloeprime.thirdpersonshooting.client.DynamicAimMode;
import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyMapping.class)
public class MixinKeyMapping {
    @Shadow boolean isDown;

    @Inject(method = "setDown", at = @At("HEAD"))
    private void onPressOrRelease(boolean pValue, CallbackInfo ci) {
        if (!this.isDown && pValue) {
            DynamicAimMode.onKeyPressed((KeyMapping) (Object) this);
        }
        if (this.isDown && !pValue) {
            DynamicAimMode.onKeyReleased((KeyMapping) (Object) this);
        }
    }
}
