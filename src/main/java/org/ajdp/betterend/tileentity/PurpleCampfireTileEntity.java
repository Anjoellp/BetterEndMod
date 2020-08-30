package org.ajdp.betterend.tileentity;

import java.util.Optional;
import java.util.stream.IntStream;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CampfireCookingRecipe;
import net.minecraft.tileentity.CampfireTileEntity;
import net.minecraft.util.Direction;

public class PurpleCampfireTileEntity extends CampfireTileEntity implements IInventory, ISidedInventory {

	@Override
	public int getSizeInventory() {
		return getInventory().size();
	}

	@Override
	public boolean isEmpty() {
		return getInventory().isEmpty();
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		if (index < 0 || index >= getInventory().size())
			return ItemStack.EMPTY;
		return getInventory().get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		ItemStack itemstack = ItemStackHelper.getAndSplit(getInventory(), index, count);
		if (!itemstack.isEmpty())
			markDirty();
		return itemstack;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack itemstack = getInventory().get(index);
		if (itemstack.isEmpty()) {
			return ItemStack.EMPTY;
		} else {
			getInventory().set(index, ItemStack.EMPTY);
			return itemstack;
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		Optional<CampfireCookingRecipe> recipe = findMatchingRecipe(stack);
		if (!recipe.isPresent() || !getInventory().get(index).isEmpty())
			return;
		cookingTimes[index] = 0;
		cookingTotalTimes[index] = recipe.get().getCookTime();
		getInventory().set(index, stack.split(1));
		this.inventoryChanged();
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
		return true;
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		if (side == Direction.DOWN)
			return new int[0];
		return IntStream.range(0, getSizeInventory()).toArray();
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, Direction direction) {
		Optional<CampfireCookingRecipe> recipe = findMatchingRecipe(itemStackIn);
		if (!recipe.isPresent())
			return false;
		if (itemStackIn.getCount() > 1)
			return false;
		return getStackInSlot(index).isEmpty();
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
		return false;
	}

}
