package org.ajdp.betterend.block;

import org.ajdp.betterend.container.EnderWorkbenchContainer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class EnderCraftingTableBlock extends Block {
	private static final ITextComponent TEXT_COMPONENT = new TranslationTextComponent("container.ender_crafting");

	public EnderCraftingTableBlock(Block.Properties builder) {
		super(builder);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {
		if (worldIn.isRemote)
			return ActionResultType.SUCCESS;
		else {
			player.openContainer(state.getContainer(worldIn, pos));
			player.addStat(ModStats.INTERACT_WITH_ENDER_CRAFTING_TABLE);
			return ActionResultType.SUCCESS;
		}
	}

	@Override
	public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
		return new SimpleNamedContainerProvider((type, inventory, player) -> new EnderWorkbenchContainer(type,
				inventory, IWorldPosCallable.of(worldIn, pos)), TEXT_COMPONENT);
	}

}
