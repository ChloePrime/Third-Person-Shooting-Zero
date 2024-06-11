package mod.chloeprime.thirdpersonshooting.client;

import mod.chloeprime.thirdpersonshooting.mixin.client.dynamicaiming.MixinKeyMapping;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


/**
 * @see MixinKeyMapping;
 */
@Mod.EventBusSubscriber(Dist.CLIENT)
public class DynamicAimMode {
    /**
     * 200毫秒等于多少纳秒
     */
    public static final long SHORT_PRESS_THRESHOLD_NANOS = 200_000_000;
    public static long lastAimKeyPressedTime = -1;

    public static void onKeyPressed(KeyMapping key) {
//        if (key != AimKey.AIM_KEY || !KeyConfig.HOLD_TO_AIM.get()) {
//            return;
//        }
//        lastAimKeyPressedTime = System.nanoTime();
    }

    public static void onKeyReleased(KeyMapping key) {
//        if (key != AimKey.AIM_KEY || !KeyConfig.HOLD_TO_AIM.get()) {
//            return;
//        }
//        var nanos = System.nanoTime() - lastAimKeyPressedTime;
//        lastAimKeyPressedTime = -1;
//        if (!ClientConfig.DYNAMIC_AIM_MODE.get() || !Helpers.isHoldingGun()) {
//            return;
//        }
//        Optional.ofNullable(Minecraft.getInstance().player)
//                .map(IClientPlayerGunOperator::fromLocalPlayer)
//                .ifPresent(gunner -> {
//                    var currentPerspective = current();
//                    if (nanos <= SHORT_PRESS_THRESHOLD_NANOS) {
//                        var nextPerspective = currentPerspective == FIRST_PERSON ? SHOULDER_SURFING : FIRST_PERSON;
//                        ShoulderInstance.getInstance().changePerspective(nextPerspective);
//
//                        var forceAim = nextPerspective == FIRST_PERSON;
//                        gunner.aim(forceAim);
//                    }
//                });
    }

    @SubscribeEvent
    public static void tick(TickEvent.ClientTickEvent event) {
//        if (event.phase != TickEvent.Phase.END) {
//            return;
//        }
//        if (Perspective.current() == FIRST_PERSON) {
//            if (Helpers.isHoldingGun()) {
//                return;
//            }
//        }
//        var handler = (OverrideableAimingHandler) AimingHandler.get();
//        if (handler.tp_shooting$isOverrideAiming()) {
//            handler.tp_shooting$setOverrideAiming(false);
//            ((TacGunRenderingHandlerAccessor) GunRenderingHandler.get()).getAimingDynamics().update(0.05F, 0);
//        }
    }

    private DynamicAimMode() {}
}