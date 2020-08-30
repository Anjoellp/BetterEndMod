package org.ajdp.betterend.biomes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum ConnectEndForestLayer implements ICastleTransformer {
	INSTANCE;

	@Override
	public int apply(INoiseRandom context, int north, int west, int south, int east, int center) {
		if (areEqual(north, south, west, east, center))
			return center;
		return matches(north, south, west, east) > 1 ? 1 : center;

	}

	public static int matches(int... is) {
		List<Integer[]> matches = new ArrayList<>();
		for (int i : is) {
			for (int j : is)
				if (j == i) {
					if (matches.stream().noneMatch(e -> (e[0] == j && e[1] == i) || (e[1] == j && e[0] == i)))
						matches.add(new Integer[] { j, i });
				}
		}
		return matches.size();
	}

	public static boolean areEqual(int... is) {
		if (is.length < 2)
			return true;
		for (int i = 1; i < is.length; i++) {
			if (is[i - 1] != is[i])
				return false;
		}
		return true;
	}

}
