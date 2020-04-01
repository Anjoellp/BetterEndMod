package org.ajdp.betterend.items;

import org.ajdp.betterend.BetterEndMod;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.ActionResultType;

public class EndSaplingItem extends BlockItem {

	public EndSaplingItem(Block blockIn, Properties builder) {
		super(blockIn, builder);
	}

	@Override
	public ActionResultType tryPlace(BlockItemUseContext context) {
		if (!BetterEndMod.isPurpleFireBlock(context.getWorld().getBlockState(context.getPos().down()).getBlock()))
			return ActionResultType.FAIL;
		return super.tryPlace(context);
	}
}
