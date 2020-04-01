package org.ajdp.betterend.recipe;

import net.minecraft.item.crafting.CookingRecipeSerializer;

public class EndstoneRecipeSerializer extends CookingRecipeSerializer<PurpleBlastingRecipe> {

	public EndstoneRecipeSerializer(int cookTime) {
		super(PurpleBlastingRecipe::new, cookTime);
	}

}
