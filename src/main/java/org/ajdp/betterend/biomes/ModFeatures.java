package org.ajdp.betterend.biomes;

import org.ajdp.betterend.block.ModBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraftforge.common.IPlantable;

public class ModFeatures {
	public static final BlockState ENDWOOD_LOG = ModBlocks.ENDWOOD_LOG.getDefaultState();
	public static final BlockState ENDWOOD_LEAVES = ModBlocks.ENDWOOD_LEAVES.getDefaultState();
	public static final TreeFeatureConfig ENDWOOD_TREE_CONFIG = new TreeFeatureConfig.Builder(
			new SimpleBlockStateProvider(ModBlocks.ENDWOOD_LOG.getDefaultState()),
			new SimpleBlockStateProvider(ModBlocks.ENDWOOD_LEAVES.getDefaultState()), new BlobFoliagePlacer(2, 0))
					.baseHeight(4).heightRandA(2).foliageHeight(3).ignoreVines()
					.setSapling((IPlantable) ModBlocks.ENDWOOD_SAPLING).build();
}
