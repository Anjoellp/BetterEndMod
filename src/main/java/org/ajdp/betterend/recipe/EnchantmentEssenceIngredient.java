package org.ajdp.betterend.recipe;

import org.ajdp.betterend.item.EnchantedEssenceItem;
import org.ajdp.betterend.item.ModItems;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import net.minecraftforge.common.crafting.NBTIngredient;
import net.minecraftforge.registries.ForgeRegistries;

public class EnchantmentEssenceIngredient extends NBTIngredient {
	private Enchantment enchantment;

	protected EnchantmentEssenceIngredient(Enchantment enchantment) {
		super(EnchantedEssenceItem.getEnchantedStack(enchantment));
		this.enchantment = enchantment;
	}

	public EnchantmentEssenceIngredient(String enchantment) {
		this(enchantment == null || enchantment.equals("empty") ? null
				: ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(enchantment)));
	}

	@Override
	public boolean test(ItemStack input) {
		if (input.getItem() == ModItems.CURSE_ESSENCE || input.getItem() == ModItems.ENCHANTMENT_ESSENCE) {
			Enchantment other = EnchantedEssenceItem.getEnchantment(input);
			return other == enchantment;
		}
		return false;
	}

	@Override
	public JsonElement serialize() {
		JsonObject obj = new JsonObject();
		obj.addProperty("type", CraftingHelper.getID(getSerializer()).toString());
		obj.addProperty("enchantment", enchantment == null ? "empty" : enchantment.getRegistryName().toString());
		return obj;
	}

	@Override
	public IIngredientSerializer<? extends Ingredient> getSerializer() {
		return Serializer.INSTANCE;
	}

	public static class Serializer implements IIngredientSerializer<EnchantmentEssenceIngredient> {
		public static final Serializer INSTANCE = new Serializer();

		@Override
		public EnchantmentEssenceIngredient parse(PacketBuffer buffer) {
			String enchantment = buffer.readString();
			return new EnchantmentEssenceIngredient(enchantment);
		}

		@Override
		public EnchantmentEssenceIngredient parse(JsonObject json) {
			return new EnchantmentEssenceIngredient(json.get("enchantment").getAsString());
		}

		@Override
		public void write(PacketBuffer buffer, EnchantmentEssenceIngredient ingredient) {
			buffer.writeString(
					ingredient.enchantment == null ? "empty" : ingredient.enchantment.getRegistryName().toString());
		}

	}

}