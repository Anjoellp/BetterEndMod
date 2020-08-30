package org.ajdp.betterend.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class EssenceCraftingRecipeSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
		implements IRecipeSerializer<EssenceCraftingRecipe> {

	@Override
	public EssenceCraftingRecipe read(ResourceLocation recipeId, JsonObject json) {
		Ingredient ingredient1 = Ingredient.deserialize(json.get("ingredient1"));
		Ingredient ingredient2 = Ingredient.deserialize(json.get("ingredient2"));
		Ingredient essence = Ingredient.deserialize(json.get("essence"));
		ItemStack result;
		if (json.has("result"))
			result = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "result"), true);
		else if (json.has("enchanted-essence-result")) {
			result = EssenceCraftingRecipe
					.readEnchantedEssenceResult(json.get("enchanted-essence-result").getAsJsonObject());
		} else
			throw new JsonParseException("recipe has no result!");
		return new EssenceCraftingRecipe(recipeId, ingredient1, ingredient2, essence, result);
	}

	@Override
	public EssenceCraftingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
		Ingredient i1 = Ingredient.read(buffer);
		Ingredient i2 = Ingredient.read(buffer);
		Ingredient essence = Ingredient.read(buffer);
		ItemStack result = buffer.readItemStack();
		return new EssenceCraftingRecipe(recipeId, i1, i2, essence, result);
	}

	@Override
	public void write(PacketBuffer buffer, EssenceCraftingRecipe recipe) {
		recipe.ingredient1.write(buffer);
		recipe.ingredient2.write(buffer);
		recipe.essence.write(buffer);
		buffer.writeItemStack(recipe.result);
	}

}
