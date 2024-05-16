package mod.chloeprime.thirdpersonshooting.client;

import com.tac.guns.item.GunItem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;

import java.util.Optional;

public class Helpers {
    public static boolean isHoldingGun() {
        return Optional.ofNullable(Minecraft.getInstance().player)
                .map(LivingEntity::getMainHandItem)
                .filter(stack -> stack.getItem() instanceof GunItem)
                .isPresent();
    }
}
