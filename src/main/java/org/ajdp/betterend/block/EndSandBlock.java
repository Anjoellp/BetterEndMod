package org.ajdp.betterend.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.SandBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.IPlantable;

public class EndSandBlock extends SandBlock {

	public EndSandBlock(int dustColorIn, Properties properties) {
		super(dustColorIn, properties);
	}

	@Override
	public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing,
			IPlantable plantable) {
		BlockState plant = plantable.getPlant(world, pos.offset(facing));
		return plant.getBlock() == ModBlocks.ENDER_CACTUS;
	}

}
