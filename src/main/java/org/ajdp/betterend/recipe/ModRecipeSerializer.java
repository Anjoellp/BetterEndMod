package org.ajdp.betterend.recipe;

import java.util.ArrayList;

import org.ajdp.betterend.BetterEndMod;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(bus = Bus.MOD)
public class ModRecipeSerializer {
	private static final ArrayList<IRecipeSerializer<?>> registry = new ArrayList<>();
	public static final IRecipeSerializer<PurpleBlastingRecipe> PURPLE_BLASTING = register("purple_blasting",
			new EndstoneRecipeSerializer(200));
	public static final IRecipeSerializer<EssenceCraftingRecipe> ESSENCE_CRAFTING = register("essence_crafting",
			new EssenceCraftingRecipeSerializer());

	@SubscribeEvent
	public static void registerSerializer(RegistryEvent.Register<IRecipeSerializer<?>> event) {
		event.getRegistry().registerAll(registry.toArray(new IRecipeSerializer[0]));
		CraftingHelper.register(BetterEndMod.location("enchantment-essence"),
				EnchantmentEssenceIngredient.Serializer.INSTANCE);
	}

	@SuppressWarnings("unchecked")
	public static <T extends IRecipe<?>> IRecipeSerializer<T> register(String name, IRecipeSerializer<T> serializer) {
		IRecipeSerializer<T> ret = (IRecipeSerializer<T>) serializer.setRegistryName(BetterEndMod.location(name));
		registry.add(ret);
		return ret;
	}
}
