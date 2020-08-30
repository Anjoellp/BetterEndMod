package org.ajdp.betterend.biomes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ajdp.betterend.BetterEndMod;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.EndBiomeProvider;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(bus = Bus.MOD)
public class ModBiomes {
	private static final ArrayList<Biome> registry = new ArrayList<>();
	public static final Map<Biome, List<Biome>> replacements = new HashMap<>();
	public static final Biome END_FOREST = registerExtendedEnd("end_forest", new EndForestBiome(), 10);
	public static final Biome END_DESERT = registerExtendedEnd("end_desert", new EndDesertBiome(), 20);

	@SubscribeEvent
	public static void registerBiomes(RegistryEvent.Register<Biome> event) {
		event.getRegistry().registerAll(registry.toArray(new Biome[0]));
		try {
			replaceEndBiomeProvider();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void replaceEndBiomeProvider() throws Exception {
		EndBiomeProvider.field_235314_e_ = Codec.LONG.fieldOf("seed")
				.xmap(l -> (EndBiomeProvider) new CustomEndBiomeProvider(l, () -> replacements),
						(p) -> p.field_235315_h_)
				.stable().codec();
		ImmutableList.Builder<Biome> builder = new ImmutableList.Builder<Biome>();
		builder.addAll(EndBiomeProvider.END_BIOMES);
		replacements.values().stream().distinct().forEach(builder::addAll);
		EndBiomeProvider.END_BIOMES = builder.build();
		Registry.register(Registry.field_239689_aA_, "the_end", EndBiomeProvider.field_235314_e_);
	}

	public static Biome registerEndReplacement(String name, Biome biome, int weight, Biome... replaceBiomes) {
		Biome ret = register(name, biome);
		CustomEndBiomeProvider.registerEndBiomeWeight(biome, weight);
		for (Biome b : replaceBiomes) {
			replacements.computeIfAbsent(b, (bi) -> new ArrayList<>()).add(biome);
		}
		return ret;
	}

	public static Biome registerEndReplacement(String name, Biome biome, Biome... replaceBiomes) {
		return registerEndReplacement(name, biome, 1, replaceBiomes);
	}

	public static Biome registerExtendedEnd(String name, Biome biome) {
		return registerEndReplacement(name, biome, Biomes.END_MIDLANDS, Biomes.END_HIGHLANDS);
	}

	public static Biome registerExtendedEnd(String name, Biome biome, int weight) {
		return registerEndReplacement(name, biome, weight, Biomes.END_BARRENS, Biomes.END_MIDLANDS,
				Biomes.END_HIGHLANDS);
	}

	public static Biome registerEndMidlands(String name, Biome biome) {
		return registerEndReplacement(name, biome, Biomes.END_MIDLANDS);
	}

	private static Biome register(String name, Biome b) {
		Biome ret = b.setRegistryName(BetterEndMod.location(name));
		registry.add(ret);
		return ret;
	}
}
