package org.ajdp.betterend.biomes;

import org.ajdp.betterend.block.ModBlocks;

import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class ModSurfaceBuilder {
	public static final SurfaceBuilderConfig END_GRASS_CONFIG = new SurfaceBuilderConfig(
			ModBlocks.END_GRASS.getDefaultState(), Blocks.END_STONE.getDefaultState(),
			Blocks.END_STONE.getDefaultState());
	public static final SurfaceBuilderConfig END_DESERT_CONFIG = new SurfaceBuilderConfig(
			ModBlocks.END_SAND.getDefaultState(), ModBlocks.END_SAND.getDefaultState(),
			ModBlocks.END_SAND.getDefaultState());
}
