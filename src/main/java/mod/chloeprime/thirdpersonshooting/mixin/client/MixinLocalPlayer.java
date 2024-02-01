package mod.chloeprime.thirdpersonshooting.mixin.client;

import com.mojang.authlib.GameProfile;
import mod.chloeprime.thirdpersonshooting.client.TpsPlayer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LocalPlayer.class)
public class MixinLocalPlayer extends AbstractClientPlayer implements TpsPlayer {
    @Unique
    private float thirdPersonShooting$vRotX, thirdPersonShooting$vRotY;
    @Unique
    private float thirdPersonShooting$vRotX0, thirdPersonShooting$vRotY0;


    @Override
    public void TPSMOD_turnVirtual(double yRot, double xRot) {
        float dx = (float)xRot * 0.15F;
        float dy = (float)yRot * 0.15F;
        thirdPersonShooting$vRotX += dx;
        thirdPersonShooting$vRotY += dy;
        thirdPersonShooting$vRotX = (Mth.clamp(thirdPersonShooting$vRotX, -90.0F, 90.0F));
        this.thirdPersonShooting$vRotX0 += dx;
        this.thirdPersonShooting$vRotY0 += dy;
        this.thirdPersonShooting$vRotX0 = Mth.clamp(this.thirdPersonShooting$vRotX0, -90.0F, 90.0F);
    }

    @Override
    public float TPSMOD_getVirtualRotX() {
        return thirdPersonShooting$vRotX;
    }

    @Override
    public float TPSMOD_getVirtualRotY() {
        return thirdPersonShooting$vRotY;
    }

    @Override
    public float TPSMOD_getVirtualRotX0() {
        return thirdPersonShooting$vRotX0;
    }

    @Override
    public float TPSMOD_getVirtualRotY0() {
        return thirdPersonShooting$vRotY0;
    }

    @Override
    public void TPSMOD_applyRotation(ApplyRotationMethod method) {
        float speed = switch (method) {
            case DO_NOT_APPLY -> 0;
            // 新版 ysm 疑似自带插值功能，所以这里就不插值了
//            case INTERPOLATION -> 0.1F;
            default -> 1F;
        };
        float dy = thirdPersonShooting$vRotY - getYRot();
        float dx = thirdPersonShooting$vRotX - getXRot();
        turn(dy / 0.15F * speed, dx / 0.15F * speed);
    }

    private MixinLocalPlayer(ClientLevel pClientLevel, GameProfile pGameProfile) {
        super(pClientLevel, pGameProfile);
    }
}
