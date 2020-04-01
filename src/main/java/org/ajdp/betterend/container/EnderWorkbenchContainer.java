package org.ajdp.betterend.container;

import java.util.List;
import java.util.Optional;

import org.ajdp.betterend.block.ModBlocks;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.util.RecipeBookCategories;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EnderWorkbenchContainer extends RecipeBookContainer<CraftingInventory> {
	private final CraftingInventory craftMatrix = new CraftingInventory(this, 5, 5);
	private final CraftResultInventory craftResult = new CraftResultInventory();
	private final IWorldPosCallable pos;
	private final PlayerEntity player;

	public EnderWorkbenchContainer(int id, PlayerInventory inventory) {
		this(id, inventory, IWorldPosCallable.DUMMY);
	}

	public EnderWorkbenchContainer(int id, PlayerInventory inventory, IWorldPosCallable pos) {
		super(ModContainerTypes.ENDER_CRAFTING, id);
		this.pos = pos;
		this.player = inventory.player;
		this.addSlot(new CraftingResultSlot(player, this.craftMatrix, this.craftResult, 0, 140, 60));
		for (int i = 0; i < 5; ++i) {
			for (int j = 0; j < 5; ++j) {
				this.addSlot(new Slot(this.craftMatrix, j + i * 5, 8 + j * 18, 17 + i * 18));
			}
		}

		for (int k = 0; k < 3; ++k) {
			for (int i1 = 0; i1 < 9; ++i1) {
				this.addSlot(new Slot(inventory, i1 + k * 9 + 9, 8 + i1 * 18, 116 + k * 18));
			}
		}

		for (int l = 0; l < 9; ++l) {
			this.addSlot(new Slot(inventory, l, 8 + l * 18, 174));
		}

	}

	public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (index == 0) {
				this.pos.consume((p_217067_2_, p_217067_3_) -> {
					itemstack1.getItem().onCreated(itemstack1, p_217067_2_, playerIn);
				});
				if (!this.mergeItemStack(itemstack1, getSize(), getSize() + 9 * 4 - 1, true)) {
					return ItemStack.EMPTY;
				}
				slot.onSlotChange(itemstack1, itemstack);
			} else if (index >= getSize() && index < getSize() + 9 * 4) {
				if (!this.mergeItemStack(itemstack1, 1, 10, false)) {
					if (index < 37) {
						if (!this.mergeItemStack(itemstack1, getSize() + 9 * 3, getSize() + 9 * 3 - 1, false)) {
							return ItemStack.EMPTY;
						}
					} else if (!this.mergeItemStack(itemstack1, getSize(), getSize() + 9 * 3, false)) {
						return ItemStack.EMPTY;
					}
				}
			} else if (!this.mergeItemStack(itemstack1, getSize(), getSize() + 9 * 4 - 1, false)) {
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			ItemStack itemstack2 = slot.onTake(playerIn, itemstack1);
			if (index == 0) {
				playerIn.dropItem(itemstack2, false);
			}
		}

		return itemstack;
	}

	@Override
	public List<RecipeBookCategories> getRecipeBookCategories() {
		return ImmutableList.of(RecipeBookCategories.BUILDING_BLOCKS, RecipeBookCategories.EQUIPMENT,
				RecipeBookCategories.REDSTONE, RecipeBookCategories.MISC);
	}

	protected static void close(int windowId, World world, PlayerEntity player, CraftingInventory inventory,
			CraftResultInventory result) {
		if (!world.isRemote) {
			ServerPlayerEntity serverplayerentity = (ServerPlayerEntity) player;
			ItemStack itemstack = ItemStack.EMPTY;
			Optional<ICraftingRecipe> optional = world.getServer().getRecipeManager().getRecipe(IRecipeType.CRAFTING,
					inventory, world);
			if (optional.isPresent()) {
				ICraftingRecipe icraftingrecipe = optional.get();
				if (result.canUseRecipe(world, serverplayerentity, icraftingrecipe)) {
					itemstack = icraftingrecipe.getCraftingResult(inventory);
				}
			}

			result.setInventorySlotContents(0, itemstack);
			serverplayerentity.connection.sendPacket(new SSetSlotPacket(windowId, 0, itemstack));
		}
	}

	public void onContainerClosed(PlayerEntity playerIn) {
		super.onContainerClosed(playerIn);
		this.pos.consume((p_217068_2_, p_217068_3_) -> {
			this.clearContainer(playerIn, p_217068_2_, this.craftMatrix);
		});
	}

	public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
		return slotIn.inventory != this.craftResult && super.canMergeSlot(stack, slotIn);
	}

	public void onCraftMatrixChanged(IInventory inventoryIn) {
		this.pos.consume((world, pos) -> {
			close(this.windowId, world, this.player, this.craftMatrix, this.craftResult);
		});
	}

	@Override
	public void fillStackedContents(RecipeItemHelper itemHelperIn) {
		this.craftMatrix.fillStackedContents(itemHelperIn);
	}

	@Override
	public void clear() {
		this.craftMatrix.clear();
		this.craftResult.clear();
	}

	@Override
	public boolean matches(IRecipe<? super CraftingInventory> recipeIn) {
		return recipeIn.matches(this.craftMatrix, this.player.world);
	}

	@Override
	public int getOutputSlot() {
		return 0;
	}

	@Override
	public int getWidth() {
		return this.craftMatrix.getWidth();
	}

	@Override
	public int getHeight() {
		return this.craftMatrix.getHeight();
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public int getSize() {
		return 26;
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return isWithinUsableDistance(this.pos, playerIn, ModBlocks.ENDER_CRAFTING_TABLE);
	}

}
