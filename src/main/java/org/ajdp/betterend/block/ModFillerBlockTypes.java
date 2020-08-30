package org.ajdp.betterend.block;

import net.minecraft.block.Blocks;
import net.minecraft.world.gen.feature.OreFeatureConfig.FillerBlockType;

public class ModFillerBlockTypes {
	public static final FillerBlockType ENDSTONE = FillerBlockType.create("ENDSTONE", "endstone",
			state -> state.getBlock() == Blocks.END_STONE);
}
