package org.ajdp.betterend.container;

import org.ajdp.betterend.BetterEndMod;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class EssenceEnchanterScreen extends ContainerScreen<EssenceCrafterContainer> {
	public static final ResourceLocation CONTAINER_RESOURCE = BetterEndMod
			.location("textures/gui/container/essence_enchanter.png");

	public EssenceEnchanterScreen(EssenceCrafterContainer screenContainer, PlayerInventory inv,
			ITextComponent title) {
		super(screenContainer, inv, title);
	}

	public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
		this.func_230446_a_(p_230430_1_);
		super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
		RenderSystem.disableBlend();
		this.func_230459_a_(p_230430_1_, p_230430_2_, p_230430_3_);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void func_230450_a_(MatrixStack stack, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
		func_230446_a_(stack);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.field_230706_i_.getTextureManager().bindTexture(CONTAINER_RESOURCE);
		int i = this.guiLeft;
		int j = (this.field_230709_l_ - this.ySize) / 2;
		this.func_238474_b_(stack, i, j, 0, 0, this.xSize, this.ySize);
	}

}
