package org.ajdp.betterend.loot;

import org.ajdp.betterend.BetterEndMod;

import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.util.registry.Registry;

public class ModLootFunctions {
	public static final LootFunctionType ENCHANT_ESSENCE_RANDOMLY = register("enchant_essence_randomly",
			new EnchantEssenceRandomly.Serializer());

	private static LootFunctionType register(String name, LootFunction.Serializer<?> serializer) {
		return Registry.register(Registry.field_239694_aZ_, BetterEndMod.location(name),
				new LootFunctionType(serializer));
	}
}
