package org.ajdp.betterend.item;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class EssenceItem extends Item {

	public EssenceItem(Properties properties) {
		super(properties);
	}

	public static boolean isEssence(Item item) {
		return item == Items.PRISMARINE_CRYSTALS || item.getItem() instanceof EssenceItem;
	}

}
