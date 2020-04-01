package org.ajdp.betterend.recipe;

import org.ajdp.betterend.BetterEndMod;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class ModRecipeTypes {
	public static RecipeType<PurpleBlastingRecipe> PURPLE_BLASTING = new RecipeType<PurpleBlastingRecipe>()
			.setRegistryName(BetterEndMod.location("purple_blasting"));

	@SuppressWarnings("unchecked")
	public static <T extends IForgeRegistryEntry<T> & IRecipeType<?>> void registerRecipeTypes(
			IForgeRegistry<T> registry) {
		registry.register((T) (PURPLE_BLASTING));
	}

	public static class RecipeType<T extends IRecipe<?>> extends ForgeRegistryEntry<RecipeType<T>>
			implements IRecipeType<T> {
		@Override
		public String toString() {
			return getRegistryName().getPath();
		}
	}
}
