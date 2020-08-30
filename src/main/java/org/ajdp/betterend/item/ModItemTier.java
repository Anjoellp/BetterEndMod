package org.ajdp.betterend.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;

public class ModItemTier implements IItemTier {
	public static final ModItemTier ENDSTONE = new ModItemTier(1, 151, 7.0F, 1.2F, 5,
			() -> Ingredient.fromItems(ModItems.ENDSTONE_BRICK), 7.0F, -3.2F);
	public static final ModItemTier AZULIUM = new ModItemTier(2, 250, 8.0F, 2.0F, 14,
			() -> Ingredient.fromItems(ModItems.AZULIUM_INGOT), 6.0F, -3.1F);
	public static final ModItemTier RUBY = new ModItemTier(5, 2500, 12.0F, 5F, 20,
			() -> Ingredient.fromItems(ModItems.RUBY), 5.0F, -3.0F);
	private int maxUses;
	private int harvestLevel;
	private float efficiency;
	private float attackDamage;
	private int enchantability;
	private LazyOptional<Ingredient> repairMaterial;
	private float[] axeData;

	public ModItemTier(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability,
			NonNullSupplier<Ingredient> repairMaterial, float... axeData) {
		this.harvestLevel = harvestLevel;
		this.maxUses = maxUses;
		this.efficiency = efficiency;
		this.attackDamage = attackDamage;
		this.enchantability = enchantability;
		this.repairMaterial = LazyOptional.of(repairMaterial);
		this.axeData = axeData;
	}

	@Override
	public int getMaxUses() {
		return maxUses;
	}

	@Override
	public float getEfficiency() {
		return efficiency;
	}

	@Override
	public float getAttackDamage() {
		return attackDamage;
	}

	@Override
	public int getHarvestLevel() {
		return harvestLevel;
	}

	@Override
	public int getEnchantability() {
		return enchantability;
	}

	@Override
	public Ingredient getRepairMaterial() {
		return repairMaterial.orElseGet(() -> null);
	}

	public float[] getAxeData() {
		return axeData;
	}
}
