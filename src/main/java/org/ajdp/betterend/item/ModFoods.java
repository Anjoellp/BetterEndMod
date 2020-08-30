package org.ajdp.betterend.item;

import org.ajdp.betterend.effect.ModEffects;

import net.minecraft.item.Food;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class ModFoods {
	public static final Food ENDER_PARSNIP = new Food.Builder().hunger(2).saturation(1).build();
	public static final Food ENDER_PARSNIP_SOUP = new Food.Builder().hunger(6).saturation(1).build();
	public static final Food COOKED_CHORUS_FRUIT = new Food.Builder().hunger(5).saturation(1).build();
	public static final Food ENCHANTED_AZULIUM_APPLE = new Food.Builder().hunger(8).saturation(2)
			.effect(() -> new EffectInstance(Effects.REGENERATION, 400, 1), 1.0F)
			.effect(() -> new EffectInstance(Effects.RESISTANCE, 6000, 0), 1.0F)
			.effect(() -> new EffectInstance(ModEffects.TELEPORT, 6000, 0), 1.0F)
			.effect(() -> new EffectInstance(Effects.ABSORPTION, 2400, 3), 1.0F).setAlwaysEdible().build();
	public static final Food AZULIUM_APPLE = new Food.Builder().hunger(8).saturation(2)
			.effect(() -> new EffectInstance(Effects.REGENERATION, 100, 1), 1.0F)
			.effect(() -> new EffectInstance(ModEffects.TELEPORT, 600), 1.0F)
			.effect(() -> new EffectInstance(Effects.ABSORPTION, 2400, 0), 1.0F).setAlwaysEdible().build();
	public static final Food ENDER_CACTUS_FRUIT = new Food.Builder().hunger(4).saturation(0.6f).build();
}
