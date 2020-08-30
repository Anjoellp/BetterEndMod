package org.ajdp.betterend.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class PurpleFireBlock extends FireBlock {

	public PurpleFireBlock(Properties p) {
		super(p);
	}

	@SuppressWarnings("deprecation")
	@Override
	public int func_220274_q(BlockState p_220274_1_) {
		return ((FireBlock) Blocks.FIRE).func_220274_q(p_220274_1_);
	}

	@SuppressWarnings("deprecation")
	@Override
	public int func_220275_r(BlockState p_220275_1_) {
		return ((FireBlock) Blocks.FIRE).func_220275_r(p_220275_1_);
	}

	@Override
	protected BlockState func_235494_a_(IWorld world, BlockPos pos, int age) {
		BlockState state = func_235326_a_(world, pos);
		return state.isIn(ModBlocks.PURPLE_FIRE) ? state.with(AGE, age) : state;
	}
}
