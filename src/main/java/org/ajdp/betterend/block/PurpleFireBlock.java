package org.ajdp.betterend.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.FireBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class PurpleFireBlock extends FireBlock {

	public PurpleFireBlock(Properties builder) {
		super(builder);
	}

	@Override
	public boolean isBurning(BlockState state, IBlockReader world, BlockPos pos) {
		return true;
	}

}
