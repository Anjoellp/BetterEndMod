package org.ajdp.betterend.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChorusFlowerBlock;
import net.minecraft.block.ChorusPlantBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;

public class UpdatedChorusFlowerBlock extends ChorusFlowerBlock {

	public UpdatedChorusFlowerBlock(ChorusPlantBlock p_i48429_1_, Properties builder) {
		super(p_i48429_1_, builder);
	}

	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		BlockState blockstate = worldIn.getBlockState(pos.down());
		if (blockstate.getBlock() != this.field_196405_b && !UpdatedChorusPlantBlock.isValidBlock(blockstate)) {
			if (!blockstate.isAir(worldIn, pos.down())) {
				return false;
			} else {
				boolean flag = false;

				for (Direction direction : Direction.Plane.HORIZONTAL) {
					BlockState blockstate1 = worldIn.getBlockState(pos.offset(direction));
					if (blockstate1.isIn(this.field_196405_b)) {
						if (flag) {
							return false;
						}

						flag = true;
					} else if (!blockstate1.isAir(worldIn, pos.offset(direction))) {
						return false;
					}
				}

				return flag;
			}
		} else {
			return true;
		}
	}

	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		BlockPos blockpos = pos.up();
		if (worldIn.isAirBlock(blockpos) && blockpos.getY() < 256) {
			int i = state.get(AGE);
			if (i < 5 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, blockpos, state, true)) {
				boolean flag = false;
				boolean flag1 = false;
				BlockState blockstate = worldIn.getBlockState(pos.down());
				Block block = blockstate.getBlock();
				if (UpdatedChorusPlantBlock.isValidBlock(blockstate)) {
					flag = true;
				} else if (block == this.field_196405_b) {
					int j = 1;

					for (int k = 0; k < 4; ++k) {
						BlockState block1 = worldIn.getBlockState(pos.down(j + 1));
						if (block1.getBlock() != this.field_196405_b) {
							if (UpdatedChorusPlantBlock.isValidBlock(block1)) {
								flag1 = true;
							}
							break;
						}

						++j;
					}

					if (j < 2 || j <= random.nextInt(flag1 ? 5 : 4)) {
						flag = true;
					}
				} else if (blockstate.isAir(worldIn, pos.down())) {
					flag = true;
				}

				if (flag && areAllNeighborsEmpty(worldIn, blockpos, (Direction) null)
						&& worldIn.isAirBlock(pos.up(2))) {
					worldIn.setBlockState(pos, this.field_196405_b.makeConnections(worldIn, pos), 2);
					this.placeGrownFlower(worldIn, blockpos, i);
				} else if (i < 4) {
					int l = random.nextInt(4);
					if (flag1) {
						++l;
					}

					boolean flag2 = false;

					for (int i1 = 0; i1 < l; ++i1) {
						Direction direction = Direction.Plane.HORIZONTAL.random(random);
						BlockPos blockpos1 = pos.offset(direction);
						if (worldIn.isAirBlock(blockpos1) && worldIn.isAirBlock(blockpos1.down())
								&& areAllNeighborsEmpty(worldIn, blockpos1, direction.getOpposite())) {
							this.placeGrownFlower(worldIn, blockpos1, i + 1);
							flag2 = true;
						}
					}

					if (flag2) {
						worldIn.setBlockState(pos, this.field_196405_b.makeConnections(worldIn, pos), 2);
					} else {
						this.placeDeadFlower(worldIn, pos);
					}
				} else {
					this.placeDeadFlower(worldIn, pos);
				}
				net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
			}
		}
	}

}
