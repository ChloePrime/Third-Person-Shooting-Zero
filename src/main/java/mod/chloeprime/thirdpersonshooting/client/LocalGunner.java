package mod.chloeprime.thirdpersonshooting.client;

import com.tacz.guns.api.client.gameplay.IClientPlayerGunOperator;
import com.tacz.guns.api.item.IGun;
import net.minecraft.client.Minecraft;

import java.util.Optional;

public class LocalGunner {
    public static boolean isAiming() {
        return get().filter(IClientPlayerGunOperator::isAim).isPresent();
    }

    public static Optional<IClientPlayerGunOperator> get() {
        return Optional.ofNullable(Minecraft.getInstance().player)
                .map(IClientPlayerGunOperator::fromLocalPlayer);
    }

    public static boolean isHoldingGun() {
        return Optional.ofNullable(Minecraft.getInstance().player)
                .filter(IGun::mainhandHoldGun)
                .isPresent();
    }
}
