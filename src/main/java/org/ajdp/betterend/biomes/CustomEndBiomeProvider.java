package org.ajdp.betterend.biomes;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.provider.BiomeProviderType;
import net.minecraft.world.biome.provider.CheckerboardBiomeProviderSettings;
import net.minecraft.world.biome.provider.EndBiomeProvider;
import net.minecraft.world.biome.provider.EndBiomeProviderSettings;

public class CustomEndBiomeProvider extends EndBiomeProvider {
	private Supplier<? extends List<? extends Biome>> midlands;

	public CustomEndBiomeProvider(EndBiomeProviderSettings settings,
			Supplier<? extends List<? extends Biome>> midlands) {
		super(settings);
		this.midlands = midlands;
	}

	@Override
	public Biome getNoiseBiome(int x, int y, int z) {
		Biome b = super.getNoiseBiome(x, y, z);
		if (b == Biomes.END_MIDLANDS) {
			ArrayList<Biome> all = new ArrayList<>();
			all.add(Biomes.END_MIDLANDS);
			all.addAll(midlands.get());
			return BiomeProviderType.CHECKERBOARD
					.create(new CheckerboardBiomeProviderSettings(null).setBiomes(all.toArray(new Biome[0])).setSize(4))
					.getNoiseBiome(x, y, z);
		}
		return b;
	}
}
