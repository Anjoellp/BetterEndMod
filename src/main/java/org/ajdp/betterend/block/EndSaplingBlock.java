package org.ajdp.betterend.block;

import java.util.Random;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;

public class EndSaplingBlock extends SaplingBlock {

	protected EndSaplingBlock(AbstractBlock.Properties properties) {
		super(new EndwoodTree(), properties);
	}

	@Override
	public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
		return Blocks.OAK_SAPLING.getFlammability(state, world, pos, face);
	}

	public void func_226942_a_(ServerWorld p_226942_1_, BlockPos p_226942_2_, BlockState p_226942_3_,
			Random p_226942_4_) {
		if (p_226942_3_.get(STAGE) == 0) {
			p_226942_1_.setBlockState(p_226942_2_, p_226942_3_.func_235896_a_(STAGE), 4);
		} else {
			if (!net.minecraftforge.event.ForgeEventFactory.saplingGrowTree(p_226942_1_, p_226942_4_, p_226942_2_))
				return;
			System.out.println(new EndwoodTree().func_230339_a_(p_226942_1_,
					p_226942_1_.getChunkProvider().getChunkGenerator(), p_226942_2_, p_226942_3_, p_226942_4_));
		}

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
		return state.getBlock() == Blocks.END_STONE || state.getBlock() == ModBlocks.END_GRASS;
	}
}
