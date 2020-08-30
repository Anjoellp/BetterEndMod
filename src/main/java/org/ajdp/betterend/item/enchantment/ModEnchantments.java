package org.ajdp.betterend.item.enchantment;

import java.util.ArrayList;
import java.util.List;

import org.ajdp.betterend.BetterEndMod;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(bus = Bus.MOD)
public class ModEnchantments {
	public static final List<Enchantment> registry = new ArrayList<>();
	private static final EquipmentSlotType[] ARMOR_SLOTS = new EquipmentSlotType[] { EquipmentSlotType.HEAD,
			EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET };
	public static final Enchantment FLY_ABILLITY = register("fly_abillity",
			new FlyAbillityEnchantment(Enchantment.Rarity.VERY_RARE, EnchantmentType.ARMOR, ARMOR_SLOTS, 1));
	public static final Enchantment SMELTING_TOUCH = register("smelting_touch",
			new SmeltingTouchEnchantment(Rarity.VERY_RARE, EquipmentSlotType.MAINHAND));
	public static final Enchantment MAGIC_PROTECTION = register("magic_protection",
			new MagicProtectionEnchantment(Rarity.RARE, ARMOR_SLOTS));

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void registerEnchantments(RegistryEvent.Register<Enchantment> event) {
		IForgeRegistry<Enchantment> registry = event.getRegistry();
		registry.registerAll(ModEnchantments.registry.toArray(new Enchantment[0]));
	}

	public static Enchantment register(String name, Enchantment enchantment) {
		Enchantment e = enchantment.setRegistryName(BetterEndMod.location(name));
		registry.add(e);
		return e;
	}

	protected static Enchantment registerEnchantment(String name, Enchantment.Rarity rarity, EnchantmentType type,
			EquipmentSlotType[] slots, int maxLevel, boolean treasure) {
		return register(name, new Enchantment(rarity, type, slots) {
			@Override
			public int getMaxLevel() {
				return maxLevel;
			}

			public boolean isTreasureEnchantment() {
				return treasure;
			};
		});
	}
}
