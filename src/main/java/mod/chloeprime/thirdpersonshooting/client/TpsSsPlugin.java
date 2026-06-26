package mod.chloeprime.thirdpersonshooting.client;

import com.github.exopandora.shouldersurfing.api.client.IShoulderSurfing;
import com.github.exopandora.shouldersurfing.api.client.event.ComputePlayerAimStateEvent;
import com.github.exopandora.shouldersurfing.api.client.event.SetupCameraRotationEvent;
import com.github.exopandora.shouldersurfing.api.event.IEventBus;
import com.github.exopandora.shouldersurfing.api.plugin.IShoulderSurfingPlugin;
import com.github.exopandora.shouldersurfing.client.InputHandler;
import com.tacz.guns.api.item.IGun;
import net.minecraft.client.Minecraft;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings("unused")
public class TpsSsPlugin implements IShoulderSurfingPlugin {
    public static TpsSsPlugin getInstance() {
        return Objects.requireNonNull(instance.get(), () -> "Accessing %s.INSTANCE too early".formatted(TpsSsPlugin.class.getSimpleName()));
    }

    public TpsSsPlugin() {
        if (instance.getAndSet(this) != null) {
            throw new IllegalStateException("Should not construct %s more than once".formatted(TpsSsPlugin.class.getSimpleName()));
        }
    }

    public double deltaXRot;
    public double deltaYRot;

    private static final AtomicReference<TpsSsPlugin> instance = new AtomicReference<>();

    @Override
    public void register(IEventBus bus) {
        bus.register(this::isUsingAdaptiveCrosshair);
        bus.register(2005, this::setupCameraRotation);
    }

    private void isUsingAdaptiveCrosshair(ComputePlayerAimStateEvent event) {
        var shooter = event.getEntity();
        if (!IGun.mainHandHoldGun(shooter)) {
            return;
        }
        if (InputHandler.FREE_LOOK.isDown()) {
            return;
        }
        event.setResult(true);
    }

    private void setupCameraRotation(SetupCameraRotationEvent event) {
        var player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        if (IShoulderSurfing.getInstance().isShoulderSurfing()) {
            event.setResult(event.getResult().add((float) deltaXRot, (float) deltaYRot));
            deltaXRot = deltaYRot = 0;
        }
    }
}
