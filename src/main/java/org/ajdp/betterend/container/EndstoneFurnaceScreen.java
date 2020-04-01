package org.ajdp.betterend.container;

import org.ajdp.betterend.BetterEndMod;

import net.minecraft.client.gui.screen.inventory.AbstractFurnaceScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class EndstoneFurnaceScreen extends AbstractFurnaceScreen<EndstoneFurnaceContainer> {
	private static final ResourceLocation FURNACE_GUI_TEXTURES = BetterEndMod
			.location("textures/gui/container/endstone_furnace.png");

	public EndstoneFurnaceScreen(EndstoneFurnaceContainer screenContainer, PlayerInventory inv,
			ITextComponent titleIn) {
		super(screenContainer, new EndstoneFurnaceRecipeGui(), inv, titleIn, FURNACE_GUI_TEXTURES);
	}

}
