package org.ajdp.betterend.block;

import java.util.Random;

import org.ajdp.betterend.biomes.ModFeatures;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class EndwoodTree extends Tree {

	@Override
	protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_) {
		return ModFeatures.END_TREE.withConfiguration(ModFeatures.ENDWOOD_TREE_CONFIG);
	}

}
