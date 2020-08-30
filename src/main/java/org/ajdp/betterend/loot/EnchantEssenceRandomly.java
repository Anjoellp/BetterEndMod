package org.ajdp.betterend.loot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.ajdp.betterend.item.EnchantedEssenceItem;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.functions.ILootFunction;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class EnchantEssenceRandomly extends LootFunction {
	private List<Enchantment> enchantments;

	protected EnchantEssenceRandomly(ILootCondition[] conditionsIn, Collection<? extends Enchantment> enchantments) {
		super(conditionsIn);
		this.enchantments = new ArrayList<>(enchantments);
	}

	@Override
	public LootFunctionType func_230425_b_() {
		return ModLootFunctions.ENCHANT_ESSENCE_RANDOMLY;
	}

	@Override
	protected ItemStack doApply(ItemStack stack, LootContext context) {
		Random rand = context.getRandom();
		List<Enchantment> es = enchantments;
		if (enchantments.isEmpty()) {
			es = new ArrayList<>(ForgeRegistries.ENCHANTMENTS.getValues());
		}
		Enchantment e = es.get(rand.nextInt(es.size()));
		ItemStack ret = EnchantedEssenceItem.getEnchantedStack(e);
		ret.setCount(stack.getCount());
		if (stack.hasTag())
			ret.setTag(stack.getTag());
		return ret;
	}

	public static class Builder extends LootFunction.Builder<EnchantEssenceRandomly.Builder> {
		private final List<Enchantment> enchantments = new ArrayList<>();

		@Override
		public ILootFunction build() {
			return new EnchantEssenceRandomly(getConditions(), enchantments);
		}

		public Builder addEnchantment(Enchantment e) {
			enchantments.add(e);
			return this;
		}

		@Override
		protected Builder doCast() {
			return this;
		}

	}

	public static class Serializer extends LootFunction.Serializer<EnchantEssenceRandomly> {

		@Override
		public EnchantEssenceRandomly deserialize(JsonObject object, JsonDeserializationContext deserializationContext,
				ILootCondition[] conditionsIn) {
			List<Enchantment> enchantments = new ArrayList<>();
			if (object.has("enchantments")) {
				for (JsonElement el : JSONUtils.getJsonArray(object, "enchantments")) {
					String enchantment = JSONUtils.getString(el, "enchantment");
					enchantments.add(ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(enchantment)));
				}
			}
			return new EnchantEssenceRandomly(conditionsIn, enchantments);
		}

		@Override
		public void func_230424_a_(JsonObject object, EnchantEssenceRandomly function,
				JsonSerializationContext context) {
			super.func_230424_a_(object, function, context);
			if (!function.enchantments.isEmpty()) {
				JsonArray arr = new JsonArray();
				for (Enchantment e : function.enchantments) {
					arr.add(e.getRegistryName().toString());
				}
				object.add("enchantments", arr);
			}
		}

	}

}
