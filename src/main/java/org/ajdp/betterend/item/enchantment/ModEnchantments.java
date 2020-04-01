package org.ajdp.betterend.item.enchantment;

import org.ajdp.betterend.BetterEndMod;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.registries.IForgeRegistry;

public class ModEnchantments {
	private static final EquipmentSlotType[] ARMOR_SLOTS = new EquipmentSlotType[] { EquipmentSlotType.HEAD,
			EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET };
	public static Enchantment FLY_ABILLITY;
	public static Enchantment SMELTING_TOUCH;

	public static void registerEnchantments(IForgeRegistry<Enchantment> registry) {
		registry.register(FLY_ABILLITY = new FlyAbillityEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentType.ARMOR,
				ARMOR_SLOTS, 1).setRegistryName(BetterEndMod.location("fly_abillity")));
		registry.register(SMELTING_TOUCH = new SmeltingTouchEnchantment(Rarity.VERY_RARE, EquipmentSlotType.MAINHAND)
				.setRegistryName(BetterEndMod.location("smelting_touch")));
	}

	protected static Enchantment registerEnchantment(String name, Enchantment.Rarity rarity, EnchantmentType type,
			EquipmentSlotType[] slots, int maxLevel) {
		return new Enchantment(rarity, type, slots) {
			@Override
			public int getMaxLevel() {
				return maxLevel;
			}

			public boolean isTreasureEnchantment() {
				return true;
			};
		}.setRegistryName(BetterEndMod.location(name));
	}
}
