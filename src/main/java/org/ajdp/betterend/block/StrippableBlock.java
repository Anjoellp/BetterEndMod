package org.ajdp.betterend.block;

import java.util.function.BiFunction;
import java.util.function.Supplier;

import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class StrippableBlock extends RotatedPillarBlock {
	private BiFunction<World, BlockPos, BlockState> strippFunction;

	public StrippableBlock(Properties properties, BiFunction<World, BlockPos, BlockState> strippFunc) {
		super(properties);
		this.strippFunction = strippFunc;
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {
		BlockState stripped = strippFunction.apply(worldIn, pos);
		ItemStack current = player.getHeldItem(handIn);
		if (stripped != null && current.getToolTypes().stream().anyMatch(e -> isToolEffective(state, e))) {
			worldIn.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
			if (!worldIn.isRemote) {
				current.damageItem(1, player, e -> e.sendBreakAnimation(handIn));
			}
			worldIn.setBlockState(pos, stripped);
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.FAIL;
	}

	public static BiFunction<World, BlockPos, BlockState> appendAxis(Supplier<BlockState> defaultStripped) {
		return (world, pos) -> defaultStripped.get().with(RotatedPillarBlock.AXIS,
				world.getBlockState(pos).get(RotatedPillarBlock.AXIS));
	}

}
