package org.ajdp.betterend.items;

import java.util.function.Supplier;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EnchantedEssenceItem extends Item {
	private Supplier<Enchantment> enchantment;

	public EnchantedEssenceItem(Supplier<Enchantment> enchantmentSupplier, Properties properties) {
		super(properties);
		this.enchantment = enchantmentSupplier;
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}

	@Override
	public int getItemStackLimit(ItemStack stack) {
		return enchantment.get().getMaxLevel();
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

	public Enchantment getEnchantment() {
		return enchantment.get();
	}

}
