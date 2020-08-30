package org.ajdp.betterend.particles;

import java.util.ArrayList;
import java.util.List;

import org.ajdp.betterend.BetterEndMod;

import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(bus = Bus.MOD)
public class ModParticleTypes {
	private static final List<ParticleType<?>> registry = new ArrayList<>();
	public static final BasicParticleType PURPLE_FLAME = registerBasic("purple_flame", false);

	@SubscribeEvent
	public static void registerParticleTypes(RegistryEvent.Register<ParticleType<?>> event) {
		IForgeRegistry<ParticleType<?>> registry = event.getRegistry();
		registry.registerAll(ModParticleTypes.registry.toArray(new ParticleType[0]));
	}

	private static BasicParticleType registerBasic(String name, boolean stay) {
		return (BasicParticleType) register(name, new BasicParticleType(stay));
	}

	private static <T extends IParticleData> ParticleType<T> register(String name, ParticleType<T> type) {
		@SuppressWarnings("unchecked")
		ParticleType<T> t = (ParticleType<T>) type.setRegistryName(BetterEndMod.location(name));
		registry.add(t);
		return t;
	}
}
