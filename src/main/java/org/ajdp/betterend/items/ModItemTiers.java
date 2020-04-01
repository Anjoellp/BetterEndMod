package org.ajdp.betterend.items;

import java.util.function.Supplier;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;

public class ModItemTiers {
	public static final IItemTier ENDSTONE = newItemTier(1, 151, 7.0F, 1.2F, 5,
			() -> Ingredient.fromItems(ModItems.ENDSTONE_BRICK));
	public static final IItemTier AZULIUM = newItemTier(2, 250, 8.0F, 2.0F, 14,
			() -> Ingredient.fromItems(ModItems.AZULIUM_INGOT));
	public static final IItemTier RUBY = newItemTier(3, 1754, 12.0F, 3.5F, 10,
			() -> Ingredient.fromItems(ModItems.RUBY));

	public static IItemTier newItemTier(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn,
			int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
		return new IItemTier() {

			@Override
			public int getMaxUses() {
				return maxUsesIn;
			}

			@Override
			public float getEfficiency() {
				return efficiencyIn;
			}

			@Override
			public float getAttackDamage() {
				return attackDamageIn;
			}

			@Override
			public int getHarvestLevel() {
				return harvestLevelIn;
			}

			@Override
			public int getEnchantability() {
				return enchantabilityIn;
			}

			@Override
			public Ingredient getRepairMaterial() {
				return repairMaterialIn.get();
			}
		};
	}
}
