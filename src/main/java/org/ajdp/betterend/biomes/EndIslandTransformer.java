package org.ajdp.betterend.biomes;

import java.util.List;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public class EndIslandTransformer implements IAreaTransformer0 {

	@Override
	public int apply(INoiseRandom context, int x, int z) {
		List<Biome> max = getHighestBiomeList();
		if (max == null)
			return 0;
		for (Biome b : max) {
			int weight = CustomEndBiomeProvider.BIOME_WEIGHTS.get(b);
			if (context.random(weight) == 0)
				return max.indexOf(b);
		}
		return 0;
	}

	private List<Biome> getHighestBiomeList() {
		int max = -1;
		List<Biome> ret = null;
		for (List<Biome> replacement : ModBiomes.replacements.values()) {
			if (replacement.size() >= max) {
				max = replacement.size();
				ret = replacement;
			}
		}
		return ret;
	}

}
