package org.ajdp.betterend.block;

import java.util.Random;

import org.ajdp.betterend.biomes.ModFeatures;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public class EndwoodTree extends Tree {

	@Override
	protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean p_225546_2_) {
		return Feature.NORMAL_TREE.withConfiguration(ModFeatures.ENDWOOD_TREE_CONFIG);
	}

}
