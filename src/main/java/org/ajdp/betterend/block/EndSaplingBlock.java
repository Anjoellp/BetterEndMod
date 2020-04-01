package org.ajdp.betterend.block;

import org.ajdp.betterend.BetterEndMod;

import net.minecraft.block.BlockState;
import net.minecraft.block.SaplingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class EndSaplingBlock extends SaplingBlock {

	protected EndSaplingBlock(Properties properties) {
		super(new EndwoodTree(), properties);
	}

	@Override
	public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return state.propagatesSkylightDown(worldIn, pos) ? 0 : 1;
	}

	@Override
	public boolean isTransparent(BlockState state) {
		return true;
	}

	@Override
	protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return BetterEndMod.isPurpleFireBlock(state.getBlock());
	}
}
