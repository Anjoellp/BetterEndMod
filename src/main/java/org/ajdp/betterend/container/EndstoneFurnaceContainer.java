package org.ajdp.betterend.container;

import java.util.List;

import org.ajdp.betterend.recipe.ModRecipeTypes;

import com.google.common.collect.ImmutableList;

import net.minecraft.client.util.RecipeBookCategories;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.AbstractFurnaceContainer;
import net.minecraft.util.IIntArray;

public class EndstoneFurnaceContainer extends AbstractFurnaceContainer {

	public EndstoneFurnaceContainer(int id, PlayerInventory playerInventoryIn, IInventory furnaceInventoryIn,
			IIntArray p_i50104_6_) {
		super(ModContainerTypes.ENDSTONE_FURNACE, ModRecipeTypes.PURPLE_BLASTING, id, playerInventoryIn,
				furnaceInventoryIn, p_i50104_6_);
	}

	public EndstoneFurnaceContainer(int id, PlayerInventory playerInventoryIn) {
		super(ModContainerTypes.ENDSTONE_FURNACE, ModRecipeTypes.PURPLE_BLASTING, id, playerInventoryIn);
	}

	@Override
	public List<RecipeBookCategories> getRecipeBookCategories() {
		return ImmutableList.copyOf(ModRecipeBookCategories.values());
	}

}
