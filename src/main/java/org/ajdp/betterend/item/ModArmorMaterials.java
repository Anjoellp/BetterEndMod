package org.ajdp.betterend.item;

import java.util.function.Supplier;

import org.ajdp.betterend.BetterEndMod;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public class ModArmorMaterials {
	private static final int[] MAX_DAMAGE_ARRAY = new int[] { 13, 15, 16, 11 };

	public static final IArmorMaterial AZULIUM = newInstance("azulium", 15, new int[] { 2, 5, 6, 2 }, 9,
			SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.0F, 0, () -> Ingredient.fromItems(ModItems.AZULIUM_INGOT));
	public static final IArmorMaterial RUBY = newInstance("ruby", 15, new int[] { 4, 7, 9, 4 }, 10,
			SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 3.0F, 0.3f, () -> Ingredient.fromItems(ModItems.RUBY));

	public static IArmorMaterial newInstance(String nameIn, int maxDamageFactorIn, int[] damageReductionAmountsIn,
			int enchantabilityIn, SoundEvent equipSoundIn, float toughness, float knockbackResistence,
			Supplier<Ingredient> repairMaterialSupplier) {
		return new IArmorMaterial() {

			@Override
			public float getToughness() {
				return toughness;
			}

			@Override
			public SoundEvent getSoundEvent() {
				return equipSoundIn;
			}

			@Override
			public Ingredient getRepairMaterial() {
				return repairMaterialSupplier.get();
			}

			@Override
			public String getName() {
				return BetterEndMod.MODID + ":" + nameIn;
			}

			@Override
			public int getEnchantability() {
				return enchantabilityIn;
			}

			@Override
			public int getDurability(EquipmentSlotType slotIn) {
				return MAX_DAMAGE_ARRAY[slotIn.getIndex()] * maxDamageFactorIn;
			}

			@Override
			public int getDamageReductionAmount(EquipmentSlotType slotIn) {
				return damageReductionAmountsIn[slotIn.getIndex()];
			}

			@Override
			public float func_230304_f_() {
				return knockbackResistence;
			}

		};
	}
}
