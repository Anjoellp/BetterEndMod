package org.ajdp.betterend.container;

import java.util.List;

import org.ajdp.betterend.block.ModBlocks;
import org.ajdp.betterend.item.EssenceItem;
import org.ajdp.betterend.recipe.EssenceCraftingRecipe;
import org.ajdp.betterend.recipe.ModRecipeTypes;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;

public class EssenceCrafterContainer extends Container {
	private IWorldPosCallable pos;
	private IInventory inputInventory = new Inventory(3) {
		public void markDirty() {
			super.markDirty();
			EssenceCrafterContainer.this.onCraftMatrixChanged(this);
		};
	};
	private IInventory outputInventory = new CraftResultInventory();
	protected final World world;
	private EssenceCraftingRecipe lastRecipe;

	public EssenceCrafterContainer(int id, PlayerInventory inventory) {
		this(id, inventory, IWorldPosCallable.DUMMY);
	}

	public EssenceCrafterContainer(int id, PlayerInventory inventory, IWorldPosCallable pos) {
		super(ModContainerTypes.ESSENCE_ENCHANTER, id);
		this.pos = pos;
		this.world = inventory.player.world;
		this.addSlot(new Slot(inputInventory, 0, 56, 53) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return EssenceItem.isEssence(stack.getItem());
			}
		});
		this.addSlot(new Slot(inputInventory, 1, 38, 17));
		this.addSlot(new Slot(inputInventory, 2, 74, 17));
		this.addSlot(new Slot(outputInventory, 3, 127, 53) {
			@Override
			public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
				onSlotChanged();
				lastRecipe.shrink(inputInventory, world);
				return stack;
			}

			@Override
			public boolean isItemValid(ItemStack stack) {
				return false;
			}

			@Override
			public boolean canTakeStack(PlayerEntity playerIn) {
				return lastRecipeMatch();
			}
		});

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k) {
			this.addSlot(new Slot(inventory, k, 8 + k * 18, 142));
		}
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventoryIn) {
		super.onCraftMatrixChanged(inventoryIn);
		if (inventoryIn != inputInventory)
			return;
		boolean flag = lastRecipeMatch();
		if (!flag) {
			List<EssenceCraftingRecipe> availableRecipes = world.getRecipeManager()
					.getRecipes(ModRecipeTypes.ESSENCE_CRAFTING, inputInventory, world);
			if (!availableRecipes.isEmpty()) {
				lastRecipe = availableRecipes.get(0);
				flag = true;
			}
		}
		if (flag) {
			ItemStack output = lastRecipe.getCraftingResult(inputInventory);
			outputInventory.setInventorySlotContents(0, output);
		} else
			outputInventory.setInventorySlotContents(0, ItemStack.EMPTY);
	}

	private boolean lastRecipeMatch() {
		return lastRecipe != null && lastRecipe.matches(inputInventory, world);
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return isWithinUsableDistance(this.pos, playerIn, ModBlocks.ESSENCE_CRAFTER);
	}

	@Override
	public void onContainerClosed(PlayerEntity playerIn) {
		super.onContainerClosed(playerIn);
		pos.consume((world, pos) -> clearContainer(playerIn, world, inputInventory));
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		Slot slot = inventorySlots.get(index);
		ItemStack itemstack = ItemStack.EMPTY;
		if (slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			itemstack = stack.copy();
			if (index < 4) {
				if (!mergeItemStack(stack, 4, 40, true))
					return ItemStack.EMPTY;
			} else if (EssenceItem.isEssence(stack.getItem())) {
				if (!mergeItemStack(stack, 0, 1, true))
					return ItemStack.EMPTY;
			} else {
				if (!mergeItemStack(stack, 1, 3, false))
					return ItemStack.EMPTY;
			}
			if (stack.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (stack.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(playerIn, stack);
		}
		return itemstack;
	}
}
