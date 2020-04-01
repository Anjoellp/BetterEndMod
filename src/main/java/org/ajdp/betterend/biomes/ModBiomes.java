package org.ajdp.betterend.biomes;

import java.util.ArrayList;
import java.util.function.Function;

import org.ajdp.betterend.BetterEndMod;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProviderType;
import net.minecraft.world.biome.provider.EndBiomeProvider;
import net.minecraft.world.biome.provider.EndBiomeProviderSettings;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBiomes {
	public static final ArrayList<Biome> end_midland_biomes = new ArrayList<Biome>();
	public static Biome END_FOREST;

	public static void registerBiomes(IForgeRegistry<Biome> registry) {
		registry.register(END_FOREST = new EndForestBiome().setRegistryName(BetterEndMod.location("end_forest")));
		registerEndBiome(END_FOREST);
		try {
			replaceEndBiomeProvider();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void replaceEndBiomeProvider() throws Exception {
		BiomeProviderType.THE_END.factory = (Function<EndBiomeProviderSettings, EndBiomeProvider>) (
				s) -> new CustomEndBiomeProvider(s, () -> end_midland_biomes);
	}

	public static void registerEndBiome(Biome biome) {
		end_midland_biomes.add(biome);
	}
}
