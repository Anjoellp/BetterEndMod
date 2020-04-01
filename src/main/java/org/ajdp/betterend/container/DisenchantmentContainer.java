package org.ajdp.betterend.container;

import java.util.Map;

import org.ajdp.betterend.block.ModBlocks;
import org.ajdp.betterend.items.ModItems;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.IWorldPosCallable;

public class DisenchantmentContainer extends Container {
	private IInventory outputInventory = new CraftResultInventory();
	private IInventory inputInventory = new Inventory(2) {
		public void markDirty() {
			super.markDirty();
			DisenchantmentContainer.this.onCraftMatrixChanged(this);
		};
	};
	private IWorldPosCallable pos;

	public DisenchantmentContainer(int id, PlayerInventory player) {
		this(id, player, IWorldPosCallable.DUMMY);
	}

	public DisenchantmentContainer(int id, PlayerInventory inventory, IWorldPosCallable pos) {
		super(ModContainerTypes.DISENCHANTMENT, id);
		this.pos = pos;
		this.addSlot(new Slot(outputInventory, 0, 115, 34) {
			@Override
			public boolean canTakeStack(PlayerEntity playerIn) {
				ItemStack stack1 = inputInventory.getStackInSlot(0);
				ItemStack stack2 = inputInventory.getStackInSlot(1);
				Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack1);
				int cost = getCost(enchantments);
				return stack2.getCount() >= cost;
			}

			@Override
			public boolean isItemValid(ItemStack stack) {
				return false;
			}

			@Override
			public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
				ItemStack stack1 = inputInventory.getStackInSlot(0).copy();
				ItemStack stack2 = inputInventory.getStackInSlot(1);
				inputInventory.setInventorySlotContents(0, new ItemStack(Items.BOOK, 1));
				stack2.shrink(getCost(EnchantmentHelper.getEnchantments(stack1)));
				onSlotChanged();
				return stack;
			}
		});
		this.addSlot(new Slot(inputInventory, 0, 32, 34) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() == Items.ENCHANTED_BOOK;
			}

			@Override
			public int getSlotStackLimit() {
				return 1;
			}
		});
		this.addSlot(new Slot(inputInventory, 1, 55, 34) {
			@Override
			public boolean isItemValid(ItemStack stack) {
				return stack.getItem() == Items.QUARTZ;
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

	public int getCost(Map<Enchantment, Integer> enchantments) {
		int cost = 0;
		for (Enchantment e : enchantments.keySet()) {
			int c = enchantments.get(e);
			if (e.isTreasureEnchantment())
				c *= 2;
			cost += c;
		}
		return cost;
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventoryIn) {
		ItemStack stack1 = inventoryIn.getStackInSlot(0);
		ItemStack stack2 = inventoryIn.getStackInSlot(1);
		if (stack1.getItem() != Items.ENCHANTED_BOOK) {
			outputInventory.setInventorySlotContents(0, ItemStack.EMPTY);
		} else {
			Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(stack1);
			int cost = getCost(map);
			if (stack2.getCount() >= cost) {
				outputInventory.setInventorySlotContents(0, new ItemStack(ModItems.ENCHANTMENT_ESSENCE, cost));
			}
		}
		detectAndSendChanges();
	}

	public void onContainerClosed(PlayerEntity playerIn) {
		super.onContainerClosed(playerIn);
		this.pos.consume((p_217004_2_, p_217004_3_) -> {
			this.clearContainer(playerIn, playerIn.world, this.inputInventory);
		});
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return isWithinUsableDistance(this.pos, playerIn, ModBlocks.DISENCHANTING_TABLE);
	}

	@Override
	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		Slot slot = inventorySlots.get(index);
		ItemStack itemstack = ItemStack.EMPTY;
		if (slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			itemstack = stack.copy();
			if (index < 3) {
				if (!mergeItemStack(stack, 3, 39, true))
					return ItemStack.EMPTY;
			} else if (stack.getItem() == Items.ENCHANTED_BOOK) {
				if (!mergeItemStack(stack, 1, 2, true))
					return ItemStack.EMPTY;
			} else if (stack.getItem() == Items.QUARTZ) {
				if (!mergeItemStack(stack, 2, 3, true))
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
