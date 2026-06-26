package mod.chloeprime.thirdpersonshooting.mixin.client.bugfix;

import com.github.exopandora.shouldersurfing.api.client.IShoulderSurfing;
import com.tacz.guns.client.event.CameraSetupEvent;
import com.tacz.guns.compat.shouldersurfing.ShoulderSurfingCompat;
import mod.chloeprime.thirdpersonshooting.client.TpsSsPlugin;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = CameraSetupEvent.class, remap = false, priority = 0)
public class FixIncompatibleClassChangeInCameraSetupEvent {
    /**
     * SSR's camera does not have rotation at this stage.
     * So we need to set SSR's camera rotation elsewhere.
     *
     * @author ChloePrime
     * @reason To fix incompatible class change
     * @see mod.chloeprime.thirdpersonshooting.client.TpsSsPlugin
     */
    @Overwrite
    @SubscribeEvent
    public static void applyCameraRecoil(ViewportEvent.ComputeCameraAngles event) {
        var player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        var timeTotal = System.currentTimeMillis() - shootTimeStamp;
        if (pitchSplineFunction != null && pitchSplineFunction.isValidPoint(timeTotal)) {
            var value = pitchSplineFunction.value(timeTotal);
            if (ShoulderSurfingCompat.isInstalled() && IShoulderSurfing.getInstance().isShoulderSurfing()) {
                TpsSsPlugin.getInstance().deltaXRot = xRotO - value;
            } else {
                TpsSsPlugin.getInstance().deltaXRot = 0;
                player.setXRot(player.getXRot() - (float) (value - xRotO));
            }
            xRotO = value;
        }
        if (yawSplineFunction != null && yawSplineFunction.isValidPoint(timeTotal)) {
            var value = yawSplineFunction.value(timeTotal);
            if (ShoulderSurfingCompat.isInstalled() && IShoulderSurfing.getInstance().isShoulderSurfing()) {
                TpsSsPlugin.getInstance().deltaYRot = yRotO - value;
            } else {
                TpsSsPlugin.getInstance().deltaYRot = 0;
                player.setYRot(player.getYRot() - (float) (value - yRotO));
            }
            yRotO = value;
        }
    }

    @Shadow private static long shootTimeStamp;
    @Shadow private static PolynomialSplineFunction pitchSplineFunction;
    @Shadow private static PolynomialSplineFunction yawSplineFunction;
    @Shadow private static double xRotO;
    @Shadow private static double yRotO;
}
