package org.ajdp.betterend.recipe;

import java.util.Arrays;

import org.ajdp.betterend.item.EnchantedEssenceItem;
import org.ajdp.betterend.item.EssenceItem;
import org.ajdp.betterend.item.ModItems;

import com.google.gson.JsonObject;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

public class EssenceCraftingRecipe implements IRecipe<IInventory> {
	public final Ingredient ingredient1;
	public final Ingredient ingredient2;
	public final Ingredient essence;
	public final ItemStack result;
	private final ResourceLocation id;

	public EssenceCraftingRecipe(ResourceLocation id, Ingredient ingredient1, Ingredient ingredient2, Ingredient essence,
			ItemStack result) {
		this.id = id;
		this.ingredient1 = ingredient1;
		this.ingredient2 = ingredient2;
		this.essence = essence;
		for (ItemStack stack : essence.getMatchingStacks()) {
			if (!EssenceItem.isEssence(stack.getItem()))
				throw new IllegalArgumentException(
						"the item '" + stack.getItem().getRegistryName() + "' is not a essence!");
		}
		this.result = result;
	}

	@Override
	public boolean matches(IInventory inv, World worldIn) {
		ItemStack stack1 = inv.getStackInSlot(0);
		ItemStack stack2 = inv.getStackInSlot(1);
		ItemStack stack3 = inv.getStackInSlot(2);
		return essence.test(stack1) && (ingredient1.test(stack2) || indirectMatch(ingredient1, stack2, stack3))
				&& (ingredient2.test(stack3) || indirectMatch(ingredient2, stack3, stack2));
	}

	private boolean indirectMatch(Ingredient i, ItemStack first, ItemStack indirect) {
		return i.test(indirect) && indirect.getCount() > 1 && first.isEmpty();
	}

	public boolean simpleMatches(IInventory inv, World worldIn) {
		ItemStack stack1 = inv.getStackInSlot(0);
		ItemStack stack2 = inv.getStackInSlot(1);
		ItemStack stack3 = inv.getStackInSlot(2);
		return essence.test(stack1) && ingredient1.test(stack2) && (ingredient2.test(stack3));
	}

	public void shrink(IInventory inv, World world) {
		if (!matches(inv, world))
			return;
		if (simpleMatches(inv, world)) {
			shrink(inv, 0);
			shrink(inv, 1);
			shrink(inv, 2);
		} else {
			shrink(inv, 0);
			ItemStack stack2 = inv.getStackInSlot(1);
			ItemStack stack3 = inv.getStackInSlot(2);
			if (indirectMatch(ingredient2, stack3, stack2)) {
				shrink(inv, 1, 2);
				return;
			}
			if (indirectMatch(ingredient1, stack2, stack3)) {
				shrink(inv, 2, 2);
				return;
			}
		}
	}

	private void shrink(IInventory inv, int index) {
		shrink(inv, index, 1);
	}

	private void shrink(IInventory inv, int index, int count) {
		ItemStack stack = inv.getStackInSlot(index);
		stack.shrink(count);
		inv.setInventorySlotContents(index, stack);
	}

	@Override
	public ItemStack getCraftingResult(IInventory inv) {
		return getRecipeOutput().copy();
	}

	@Override
	public boolean canFit(int width, int height) {
		return width * height >= 3;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return result;
	}

	@Override
	public ResourceLocation getId() {
		return id;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ModRecipeSerializer.ESSENCE_CRAFTING;
	}

	@Override
	public IRecipeType<?> getType() {
		return ModRecipeTypes.ESSENCE_CRAFTING;
	}

	public static ItemStack readEnchantedEssenceResult(JsonObject json) {
		if (!json.has("enchantment"))
			return new ItemStack(ModItems.ENCHANTMENT_ESSENCE);
		int count = JSONUtils.getInt(json, "count", 1);
		Enchantment e = ForgeRegistries.ENCHANTMENTS
				.getValue(new ResourceLocation(JSONUtils.getString(json, "enchantment")));
		return EnchantedEssenceItem.getEnchantedStack(e, count);
	}

	@Override
	public String toString() {
		return Arrays.toString(ingredient1.getMatchingStacks()) + " " + Arrays.toString(ingredient2.getMatchingStacks())
				+ " " + Arrays.toString(essence.getMatchingStacks()) + " -> " + result;
	}

}
