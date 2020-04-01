package org.ajdp.betterend.container;

import org.ajdp.betterend.util.EnumUtils;

import net.minecraft.block.Blocks;
import net.minecraft.client.util.RecipeBookCategories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class ModRecipeBookCategories {

	public static final RecipeBookCategories ENDSTONE_FURNACE_SEARCH;
	public static final RecipeBookCategories ENDSTONE_FURNACE_FOOD;
	public static final RecipeBookCategories ENDSTONE_FURNACE_BLOCKS;
	public static final RecipeBookCategories ENDSTONE_FURNACE_MISC;
	private static final RecipeBookCategories[] VALUES;

	static {
		ENDSTONE_FURNACE_SEARCH = newInstance("ENDSTONE_FURNACE_SEARCH", new ItemStack(Items.COMPASS));
		ENDSTONE_FURNACE_FOOD = newInstance("ENDSTONE_FURNACE_FOOD", new ItemStack(Items.PORKCHOP));
		ENDSTONE_FURNACE_BLOCKS = newInstance("ENDSTONE_FURNACE_BLOCKS", new ItemStack(Blocks.STONE));
		ENDSTONE_FURNACE_MISC = newInstance("ENDSTONE_FURNACE_MISC", new ItemStack(Items.LAVA_BUCKET),
				new ItemStack(Items.EMERALD));
		VALUES = new RecipeBookCategories[] { ENDSTONE_FURNACE_SEARCH, ENDSTONE_FURNACE_FOOD, ENDSTONE_FURNACE_BLOCKS,
				ENDSTONE_FURNACE_MISC };
	}

	public static RecipeBookCategories[] values() {
		return VALUES;
	}

	public static RecipeBookCategories newInstance(String name, ItemStack... icons) {
		Class<?>[] types = new Class[] { String.class, ItemStack[].class };
		Object[] values = new Object[] { name, (ItemStack[]) icons };
		String enumName = name.toUpperCase();
		EnumUtils.addEnum(RecipeBookCategories.class, enumName, types, values);
		return RecipeBookCategories.valueOf(enumName);
	}
}
