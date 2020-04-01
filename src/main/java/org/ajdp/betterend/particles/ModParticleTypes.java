package org.ajdp.betterend.particles;

import org.ajdp.betterend.BetterEndMod;

import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.registries.IForgeRegistry;

public class ModParticleTypes {
	public static BasicParticleType PURPLE_FLAME;

	public static void registerParticleTypes(IForgeRegistry<ParticleType<?>> registry) {
		registry.register(PURPLE_FLAME = registerParticleType("purple_flame", false));
	}

	private static BasicParticleType registerParticleType(String name, boolean stay) {
		return (BasicParticleType) new BasicParticleType(stay).setRegistryName(BetterEndMod.location(name));
	}
}
