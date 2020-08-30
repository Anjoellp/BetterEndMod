package org.ajdp.betterend.item;

import org.ajdp.betterend.block.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.ActionResultType;

public class EndSaplingItem extends BlockItem {

	public EndSaplingItem(Block blockIn, Properties builder) {
		super(blockIn, builder);
	}

	@Override
	public ActionResultType tryPlace(BlockItemUseContext context) {
		if (!isValidGround(context.getWorld().getBlockState(context.getPos().down())))
			return ActionResultType.FAIL;
		return super.tryPlace(context);
	}

	private boolean isValidGround(BlockState block) {
		return block.getBlock() == Blocks.END_STONE || block.getBlock() == ModBlocks.END_GRASS;
	}
}
