package org.ajdp.betterend.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class EnderParsnipsBlock extends CropsBlock {

	public EnderParsnipsBlock(Properties builder) {
		super(builder);
	}

	@Override
	protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return state.getBlock() == ModBlocks.END_FARMLAND;
	}

}
