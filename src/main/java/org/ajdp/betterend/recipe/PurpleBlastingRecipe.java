package org.ajdp.betterend.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class PurpleBlastingRecipe extends AbstractCookingRecipe {

	public PurpleBlastingRecipe(ResourceLocation idIn, String groupIn, Ingredient ingredientIn, ItemStack resultIn,
			float experienceIn, int cookTimeIn) {
		super(ModRecipeTypes.PURPLE_BLASTING, idIn, groupIn, ingredientIn, resultIn, experienceIn, cookTimeIn);
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ModRecipeSerializer.PURPLE_BLASTING;
	}

}
