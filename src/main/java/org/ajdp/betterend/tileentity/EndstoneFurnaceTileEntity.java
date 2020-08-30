package org.ajdp.betterend.tileentity;

import org.ajdp.betterend.container.EndstoneFurnaceContainer;
import org.ajdp.betterend.recipe.ModRecipeTypes;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class EndstoneFurnaceTileEntity extends AbstractFurnaceTileEntity {

	public EndstoneFurnaceTileEntity() {
		super(ModTileEntityTypes.ENDSTONE_FURNACE, ModRecipeTypes.PURPLE_BLASTING);
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container.endstone_furnace");
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return new EndstoneFurnaceContainer(id, player, this, this.furnaceData);
	}

}
