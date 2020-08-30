package org.ajdp.betterend.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ajdp.betterend.BetterEndMod;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

public class EnchantedEssenceItem extends EssenceItem {

	public EnchantedEssenceItem(Properties properties) {
		super(properties);
	}

	public EnchantedEssenceItem(boolean isCurse, Properties p) {
		super(p);
	}

	@Override
	public boolean isEnchantable(ItemStack stack) {
		return getEnchantment(stack) == null;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return (stack.getItem() == ModItems.CURSE_ESSENCE && enchantment.isCurse())
				|| (stack.getItem() == ModItems.ENCHANTMENT_ESSENCE && !enchantment.isCurse());
	}

	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		super.fillItemGroup(group, items);
		if (group == ItemGroup.SEARCH) {
			for (Enchantment e : ForgeRegistries.ENCHANTMENTS) {
				if (hasEnchantedEssence(items, e))
					continue;
				items.add(getEnchantedStack(e));
			}
		} else if (group.getRelevantEnchantmentTypes().length != 0) {
			for (Enchantment e : ForgeRegistries.ENCHANTMENTS) {
				if (hasEnchantedEssence(items, e))
					continue;
				if (group.hasRelevantEnchantmentType(e.type))
					items.add(getEnchantedStack(e));
			}
		}
	}

	private boolean hasEnchantedEssence(NonNullList<ItemStack> list, Enchantment e) {
		for (ItemStack stack : list) {
			if (EnchantedEssenceItem.getEnchantment(stack) == e) {
				return true;
			}
		}
		return false;
	}

	public static ItemStack getEnchantedStack(Enchantment e, int count) {
		if (e == null)
			return new ItemStack(ModItems.ENCHANTMENT_ESSENCE, count);
		ItemStack stack = new ItemStack(e.isCurse() ? ModItems.CURSE_ESSENCE : ModItems.ENCHANTMENT_ESSENCE, 1);
		setEnchantment(stack, e);
		return stack;
	}

	public static ItemStack getEnchantedStack(Enchantment e) {
		return getEnchantedStack(e, 1);
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		Enchantment e = getEnchantment(stack);
		if (e == null)
			return;
		tooltip.add(new TranslationTextComponent(e.getName()).func_240699_a_(TextFormatting.GRAY));
	}

	public static void setEnchantment(ItemStack stack, Enchantment enchantment) {
		if (enchantment == null)
			return;
		if ((stack.getItem() == ModItems.ENCHANTMENT_ESSENCE && !enchantment.isCurse())
				|| (stack.getItem() == ModItems.CURSE_ESSENCE && enchantment.isCurse()))
			stack.getOrCreateTag().putString("Enchantment", enchantment.getRegistryName().toString());
		else
			BetterEndMod.LOGGER.error("cannot set enchantment " + enchantment.getRegistryName() + " on the item "
					+ stack.getItem().getRegistryName());
	}

	public static Enchantment getEnchantment(ItemStack stack) {
		if (stack.isEmpty()
				|| (stack.getItem() != ModItems.ENCHANTMENT_ESSENCE && stack.getItem() != ModItems.CURSE_ESSENCE))
			return null;
		CompoundNBT tag = stack.getTag();
		Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
		if (!enchantments.isEmpty()) {
			Map.Entry<Enchantment, Integer> entry = new ArrayList<>(enchantments.entrySet()).get(0);
			stack.removeChildTag("Enchantments");
			if (!tag.contains("Enchantment"))
				setEnchantment(stack, entry.getKey());
		}
		if (tag == null || !tag.contains("Enchantment"))
			return null;
		return ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(tag.getString("Enchantment")));
	}

	@Override
	public int getItemEnchantability() {
		return 15;
	}

}
