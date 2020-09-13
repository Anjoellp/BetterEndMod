package org.ajdp.betterend.biomes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public class EndIslandTransformer implements IAreaTransformer0 {

	@Override
	public int apply(INoiseRandom context, int x, int z) {
		List<Biome> max = getHighestBiomeList();
		System.out.println(max);
		if (max == null)
			return 0;
		for (Biome b : max) {
			int weight = CustomEndBiomeProvider.BIOME_WEIGHTS.get(b);
			if (context.random(weight) == 0) {
				System.out.println(b.getRegistryName() + " " + weight);
				return max.indexOf(b);
			}
		}
		return 0;
	}

	private List<Biome> getHighestBiomeList() {
		return ModBiomes.replacements.values().stream().reduce(new ArrayList<>(), (a, b) -> {
			a.addAll(b);
			return a;
		}).stream().distinct().collect(Collectors.toList());
	}

}
