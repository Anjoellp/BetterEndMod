package org.ajdp.betterend.recipe;

import java.util.ArrayList;
import java.util.List;

import org.ajdp.betterend.BetterEndMod;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

@Mod.EventBusSubscriber(bus = Bus.MOD)
public class ModRecipeTypes {
	private static final List<IRecipeType<?>> registry = new ArrayList<>();
	public final static RecipeType<PurpleBlastingRecipe> PURPLE_BLASTING = register("purple_blasting",
			new RecipeType<>());
	public static final RecipeType<EssenceCraftingRecipe> ESSENCE_CRAFTING = register("essence_enchanting",
			new RecipeType<>());

	@SuppressWarnings("unchecked")
	@SubscribeEvent
	public static <T extends IForgeRegistryEntry<T> & IRecipeType<?>> void registerRecipeTypes(
			RegistryEvent.Register<T> event) {
		IForgeRegistry<T> registry = event.getRegistry();
		for (IRecipeType<?> t : ModRecipeTypes.registry) {
			if (registry.getRegistrySuperType().isInstance(t))
				registry.registerAll((T) t);
		}
	}

	public static <T extends IForgeRegistryEntry<T> & IRecipeType<?>> T register(String name, T t) {
		T t1 = (T) t.setRegistryName(BetterEndMod.location(name));
		registry.add(t1);
		return t1;
	}

	public static class RecipeType<T extends IRecipe<?>> extends ForgeRegistryEntry<RecipeType<T>>
			implements IRecipeType<T> {
		@Override
		public String toString() {
			return getRegistryName().getPath();
		}

	}
}
