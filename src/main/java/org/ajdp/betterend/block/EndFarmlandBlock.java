package org.ajdp.betterend.block;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class EndFarmlandBlock extends FarmlandBlock {

	public EndFarmlandBlock(Properties builder) {
		super(builder);
	}

	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return !this.getDefaultState().isValidPosition(context.getWorld(), context.getPos())
				? Blocks.END_STONE.getDefaultState()
				: super.getStateForPlacement(context);
	}

	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
		if (!state.isValidPosition(worldIn, pos)) {
			turnToEndStone(state, worldIn, pos);
		} else {
			int i = state.get(MOISTURE);
			if (!FarmlandBlock.hasWater(worldIn, pos) && !worldIn.isRainingAt(pos.up())) {
				if (i > 0) {
					worldIn.setBlockState(pos, state.with(MOISTURE, Integer.valueOf(i - 1)), 2);
				} else if (!hasCropsOn(worldIn, pos)) {
					turnToEndStone(state, worldIn, pos);
				}
			} else if (i < 7) {
				worldIn.setBlockState(pos, state.with(MOISTURE, Integer.valueOf(7)), 2);
			}

		}
	}

	private boolean hasCropsOn(IBlockReader worldIn, BlockPos pos) {
		BlockState state = worldIn.getBlockState(pos.up());
		return state.getBlock() instanceof net.minecraftforge.common.IPlantable && canSustainPlant(state, worldIn, pos,
				Direction.UP, (net.minecraftforge.common.IPlantable) state.getBlock());
	}

	public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
		if (!worldIn.isRemote && net.minecraftforge.common.ForgeHooks.onFarmlandTrample(worldIn, pos,
				Blocks.END_STONE.getDefaultState(), fallDistance, entityIn)) { // Forge: Move logic to Entity#canTrample
			turnToEndStone(worldIn.getBlockState(pos), worldIn, pos);
		}
		entityIn.onLivingFall(fallDistance, 1.0F);
	}

	public static void turnToEndStone(BlockState state, World worldIn, BlockPos pos) {
		worldIn.setBlockState(pos, Blocks.END_STONE.getDefaultState());
	}
}
