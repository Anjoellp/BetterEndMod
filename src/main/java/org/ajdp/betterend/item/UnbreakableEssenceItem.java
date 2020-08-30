package org.ajdp.betterend.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class UnbreakableEssenceItem extends Item {
	public UnbreakableEssenceItem(Item.Properties properties) {
		super(properties);
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}
}
