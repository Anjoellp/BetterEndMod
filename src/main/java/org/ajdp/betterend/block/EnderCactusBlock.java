package org.ajdp.betterend.block;

import java.util.Random;

import org.ajdp.betterend.item.ModItems;

import net.minecraft.block.BlockState;
import net.minecraft.block.CactusBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;

public class EnderCactusBlock extends CactusBlock {

	public EnderCactusBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		BlockPos blockpos = pos.up();
		if (worldIn.isAirBlock(blockpos)) {
			int i;
			for (i = 1; worldIn.getBlockState(pos.down(i)).isIn(this); ++i) {
			}

			if (i < 4) {
				int j = state.get(AGE);
				if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, blockpos, state, true)) {
					if (j == 15) {
						if (i < 3) {
							worldIn.setBlockState(blockpos, this.getDefaultState());
							BlockState blockstate = state.with(AGE, Integer.valueOf(0));
							worldIn.setBlockState(pos, blockstate, 4);
							blockstate.neighborChanged(worldIn, blockpos, this, pos, false);
						} else {
							worldIn.setBlockState(blockpos, ModBlocks.ENDER_CACTUS_FRUIT.getDefaultState());
							state.neighborChanged(worldIn, blockpos, this, pos, false);
						}
					} else {
						worldIn.setBlockState(pos, state.with(AGE, Integer.valueOf(j + 1)), 4);
					}
				}
				net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
			}
		}
	}

	@Override
	public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing,
			IPlantable plantable) {
		return state.getBlock() == ModBlocks.ENDER_CACTUS;
	}

	@Override
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
		if (entityIn instanceof ItemEntity) {
			Item item = ((ItemEntity) entityIn).getItem().getItem();
			if (item == ModItems.ENDER_CACTUS || item == ModItems.ENDER_CACTUS_FRUIT)
				return;
		}
		super.onEntityCollision(state, worldIn, pos, entityIn);
		if (entityIn instanceof LivingEntity) {
			if (entityIn instanceof PlayerEntity && ((PlayerEntity) entityIn).isCreative())
				return;
			LivingEntity living = (LivingEntity) entityIn;
			living.addPotionEffect(new EffectInstance(Effects.POISON, 120));
		}
	}
}
