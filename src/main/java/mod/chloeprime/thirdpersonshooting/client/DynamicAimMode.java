package mod.chloeprime.thirdpersonshooting.client;

import com.github.exopandora.shouldersurfing.client.ShoulderInstance;
import com.github.exopandora.shouldersurfing.config.Perspective;
import com.tac.guns.Config;
import com.tac.guns.client.Keys;
import com.tac.guns.client.handler.AimingHandler;
import com.tac.guns.client.handler.GunRenderingHandler;
import mod.chloeprime.thirdpersonshooting.client.internal.OverrideableAimingHandler;
import mod.chloeprime.thirdpersonshooting.mixin.client.TacAimingHandlerAccessor;
import mod.chloeprime.thirdpersonshooting.mixin.client.dynamicaiming.TacAimTrackerAccessor;
import mod.chloeprime.thirdpersonshooting.mixin.client.dynamicaiming.MixinKeyMapping;
import mod.chloeprime.thirdpersonshooting.mixin.client.dynamicaiming.MixinTacAimingHandler;
import mod.chloeprime.thirdpersonshooting.mixin.client.dynamicaiming.TacGunRenderingHandlerAccessor;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.github.exopandora.shouldersurfing.config.Perspective.*;

/**
 * @see MixinKeyMapping;
 * @see MixinTacAimingHandler;
 */
@Mod.EventBusSubscriber(Dist.CLIENT)
public class DynamicAimMode {
    /**
     * 300毫秒等于多少纳秒
     */
    public static final long SHORT_PRESS_THRESHOLD_NANOS = 300_000_000;
    public static long lastAimKeyPressedTime = -1;

    public static void onKeyPressed(KeyMapping key) {
        if (key != Keys.AIM_HOLD) {
            return;
        }
        lastAimKeyPressedTime = System.nanoTime();
    }

    public static void onKeyReleased(KeyMapping key) {
        if (key != Keys.AIM_HOLD) {
            return;
        }
        var nanos = System.nanoTime() - lastAimKeyPressedTime;
        lastAimKeyPressedTime = -1;
        if (!ClientConfig.DYNAMIC_AIM_MODE.get() || !Config.CLIENT.controls.holdToAim.get() || !Helpers.isHoldingGun()) {
            return;
        }
        var overrider = (OverrideableAimingHandler) AimingHandler.get();
        var currentPerspective = current();
        if (nanos <= SHORT_PRESS_THRESHOLD_NANOS) {
            var nextPerspective = currentPerspective == FIRST_PERSON ? SHOULDER_SURFING : FIRST_PERSON;
            ShoulderInstance.getInstance().changePerspective(nextPerspective);
            var forceAim = nextPerspective == FIRST_PERSON;
            overrider.tp_shooting$setOverrideAiming(forceAim);
            if (!forceAim) {
                var handler = (TacAimingHandlerAccessor) AimingHandler.get();
                handler.setOldProgress(0);
                handler.setNewProgress(0);
                handler.setNormalisedAdsProgress(0);
                var tracker = (TacAimTrackerAccessor) handler.getLocalTracker();
                tracker.setCurrentAim(0);
                tracker.setPreviousAim(0);
                tracker.setLerpProgress(0);
                ((TacGunRenderingHandlerAccessor) GunRenderingHandler.get()).getAimingDynamics().update(0.05F, 0);
            }
        } else {
            // 长按松开后取消强制瞄准
            if (overrider.tp_shooting$isOverrideAiming()) {
                overrider.tp_shooting$setOverrideAiming(false);
            }
        }
    }

    @SubscribeEvent
    public static void tick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }
        if (Perspective.current() == FIRST_PERSON) {
            if (Helpers.isHoldingGun()) {
                return;
            }
        }
        var handler = (OverrideableAimingHandler) AimingHandler.get();
        if (handler.tp_shooting$isOverrideAiming()) {
            handler.tp_shooting$setOverrideAiming(false);
            ((TacGunRenderingHandlerAccessor) GunRenderingHandler.get()).getAimingDynamics().update(0.05F, 0);
        }
    }

    private DynamicAimMode() {}
}
