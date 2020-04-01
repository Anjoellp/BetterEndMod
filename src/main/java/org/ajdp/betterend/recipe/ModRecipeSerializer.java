package org.ajdp.betterend.recipe;

import org.ajdp.betterend.BetterEndMod;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.registries.IForgeRegistry;

public class ModRecipeSerializer {
	public static IRecipeSerializer<PurpleBlastingRecipe> PURPLE_BLASTING;

	public static void registerSerializer(IForgeRegistry<IRecipeSerializer<?>> registry) {
		registry.register(
				PURPLE_BLASTING = registerRecipeSerializer("purple_blasting", new EndstoneRecipeSerializer(200)));
	}

	@SuppressWarnings("unchecked")
	public static <T extends IRecipe<?>> IRecipeSerializer<T> registerRecipeSerializer(String name,
			IRecipeSerializer<T> serializer) {
		return (IRecipeSerializer<T>) serializer.setRegistryName(BetterEndMod.location(name));
	}
}
