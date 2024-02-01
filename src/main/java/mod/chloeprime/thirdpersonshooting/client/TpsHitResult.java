package mod.chloeprime.thirdpersonshooting.client;

import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
record TpsHitResult(Vec3 start, Vec3 target, double distanceSq) {}
