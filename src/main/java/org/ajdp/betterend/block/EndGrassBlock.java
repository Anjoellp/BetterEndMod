package org.ajdp.betterend.block;

import java.util.ArrayList;
import java.util.Random;

import org.ajdp.betterend.biomes.ModBiomes;
import org.ajdp.betterend.item.ModItemTier;

import com.google.common.collect.ImmutableList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.server.ServerWorld;

public class EndGrassBlock extends Block {
	public static ArrayList<IItemTier> EFFECTIVE_TIERS = new ArrayList<>(
			ImmutableList.of(ItemTier.DIAMOND, ModItemTier.AZULIUM, ModItemTier.RUBY));

	public EndGrassBlock(Properties properties) {
		super(properties);
	}

	public boolean isFreeUp(BlockState state, IWorldReader world, BlockPos pos) {
		BlockPos blockpos = pos.up();
		BlockState blockstate = world.getBlockState(blockpos);
		int i = LightEngine.func_215613_a(world, state, pos, blockstate, blockpos, Direction.UP,
				blockstate.getOpacity(world, blockpos));
		return i < world.getMaxLightLevel();
	}

	@Deprecated
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {
		ItemStack current = player.getHeldItem(handIn);
		if (isEffectiveItem(current)) {
			worldIn.setBlockState(pos, ModBlocks.END_FARMLAND.getDefaultState());
			worldIn.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
			return ActionResultType.SUCCESS;
		}
		return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
	}

	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
		if (!worldIn.isAreaLoaded(pos, 3))
			return;
		if (!isFreeUp(state, worldIn, pos)) {
			worldIn.setBlockState(pos, Blocks.END_STONE.getDefaultState());
		} else if (worldIn.getLight(pos.up()) >= 9 || worldIn.getBiome(pos) == ModBiomes.END_FOREST) {
			BlockState blockstate = this.getDefaultState();
			for (int i = 0; i < 4; ++i) {
				BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
				if (worldIn.getBlockState(blockpos).getBlock() == Blocks.END_STONE
						&& (isFreeUp(blockstate, worldIn, blockpos)
								&& !worldIn.getFluidState(blockpos).isTagged(FluidTags.WATER))) {
					worldIn.setBlockState(blockpos, blockstate);
				}
			}
		}
	}

	public static boolean isEffectiveItem(ItemStack item) {
		return item != null && item.getItem() != null && item.getItem() instanceof HoeItem
				&& EFFECTIVE_TIERS.contains(((HoeItem) item.getItem()).getTier());
	}
}
