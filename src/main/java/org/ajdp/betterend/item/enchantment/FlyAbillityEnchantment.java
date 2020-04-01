package org.ajdp.betterend.item.enchantment;

import org.ajdp.betterend.items.ModArmorMaterials;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

public class FlyAbillityEnchantment extends Enchantment {
	private int maxLevel;

	public FlyAbillityEnchantment(Rarity rarityIn, EnchantmentType typeIn, EquipmentSlotType[] slots, int maxLevel) {
		super(rarityIn, typeIn, slots);
		this.maxLevel = maxLevel;
	}

	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}

	@Override
	public int getMaxLevel() {
		return maxLevel;
	}

	@Override
	public boolean canApply(ItemStack stack) {
		return isRuby(stack);
	}

	private boolean isRuby(ItemStack stack) {
		return stack.getItem() instanceof ArmorItem
				&& ((ArmorItem) stack.getItem()).getArmorMaterial() == ModArmorMaterials.RUBY;
	}

	public int getMinEnchantability(int enchantmentLevel) {
		return 15;
	}

	public int getMaxEnchantability(int enchantmentLevel) {
		return super.getMinEnchantability(enchantmentLevel) + 50;
	}

	@Override
	public boolean isTreasureEnchantment() {
		return true;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return false;
	}

}
