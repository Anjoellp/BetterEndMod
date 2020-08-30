package org.ajdp.betterend.item;

import java.util.List;
import java.util.function.Consumer;

import org.ajdp.betterend.BetterEndMod;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class MagicArtefactItem extends Item {

	public MagicArtefactItem(Properties properties) {
		super(properties);
		ItemModelsProperties.func_239418_a_(this, BetterEndMod.location("empty"),
				(item, world, entity) -> isEmpty(item) ? 1 : 0);
	}

	public static boolean isEmpty(ItemStack stack) {
		return stack.getDamage() >= stack.getMaxDamage() - 1;
	}

	@Override
	public boolean isRepairable(ItemStack stack) {
		return stack.getItem() == this;
	}

	@Override
	public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
		return stack.getDamage() + amount >= getMaxDamage(stack) - 1 ? getMaxDamage(stack) - 1 - stack.getDamage()
				: amount;
	}

	@Override
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
		if (this.isInGroup(group)) {
			ItemStack stack1 = new ItemStack(this);
			ItemStack stack2 = stack1.copy();
			stack1.setDamage(100);
			stack2.setDamage(0);
			items.add(stack1);
			items.add(stack2);
		}
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		float charge = 100 - ((float) (getDamage(stack)));
		String percent = String.format("%.0f%%", charge);
		ITextComponent text;
		if (charge == 0)
			text = new TranslationTextComponent("artefact.betterend.empty");
		else if (charge == 100)
			text = new TranslationTextComponent("artefact.betterend.full");
		else
			text = new TranslationTextComponent("artefact.betterend.charged", percent);
		tooltip.add(text);
	}
}
