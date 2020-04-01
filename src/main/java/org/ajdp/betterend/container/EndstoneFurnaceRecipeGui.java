package org.ajdp.betterend.container;

import java.util.Set;

import net.minecraft.client.gui.recipebook.AbstractRecipeBookGui;
import net.minecraft.item.Item;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;

public class EndstoneFurnaceRecipeGui extends AbstractRecipeBookGui {

	@Override
	protected boolean func_212962_b() {
		return recipeBook.isFurnaceFilteringCraftable();
	}

	@Override
	protected void func_212959_a(boolean p_212959_1_) {
		this.recipeBook.setFurnaceFilteringCraftable(p_212959_1_);
	}

	@Override
	protected boolean func_212963_d() {
		return recipeBook.isFurnaceGuiOpen();
	}

	@Override
	protected void func_212957_c(boolean p_212957_1_) {
		recipeBook.setFurnaceGuiOpen(p_212957_1_);
	}

	@Override
	protected String func_212960_g() {
		return "gui.recipebook.toggleRecipes.purpleBlastable";
	}

	@SuppressWarnings("deprecation")
	@Override
	protected Set<Item> func_212958_h() {
		return AbstractFurnaceTileEntity.getBurnTimes().keySet();
	}

}
