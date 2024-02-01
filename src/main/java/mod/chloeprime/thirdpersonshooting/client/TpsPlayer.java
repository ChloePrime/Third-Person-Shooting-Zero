package mod.chloeprime.thirdpersonshooting.client;

import com.tac.guns.client.handler.AimingHandler;
import com.teamderpy.shouldersurfing.client.ShoulderHelper;
import com.teamderpy.shouldersurfing.config.Perspective;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.teamderpy.shouldersurfing.config.Perspective.SHOULDER_SURFING;
import static mod.chloeprime.thirdpersonshooting.client.TpsPlayer.ApplyRotationMethod.*;
import static mod.chloeprime.thirdpersonshooting.client.util.Math2.*;

@OnlyIn(Dist.CLIENT)
public interface TpsPlayer {
    enum ApplyRotationMethod {
        DO_NOT_APPLY,
        INTERPOLATION,
        APPLY_IMMEDIATELY,
    }

    void TPSMOD_turnVirtual(double yRot, double xRot);
    float TPSMOD_getVirtualRotX();
    float TPSMOD_getVirtualRotY();
    default float TPSMOD_getVirtualRotX(float partial) {
        return Mth.lerp(partial, TPSMOD_getVirtualRotX0(), TPSMOD_getVirtualRotX());
    }
    default float TPSMOD_getVirtualRotY(float partial) {
        return Mth.lerp(partial, TPSMOD_getVirtualRotY0(), TPSMOD_getVirtualRotY());
    }
    float TPSMOD_getVirtualRotX0();
    float TPSMOD_getVirtualRotY0();
    void TPSMOD_applyRotation(ApplyRotationMethod method);

    static ApplyRotationMethod howToApplyRotation(LocalPlayer player) {
        if (Perspective.current() != SHOULDER_SURFING) {
            return APPLY_IMMEDIATELY;
        }
        if (not0(player.xxa) || not0(player.zza)) {
            return INTERPOLATION;
        }
        if (AimingHandler.get().isAiming()) {
            return INTERPOLATION;
        }
        return ShoulderHelper.isHoldingAdaptiveItem() ? APPLY_IMMEDIATELY : DO_NOT_APPLY;
    }
}
