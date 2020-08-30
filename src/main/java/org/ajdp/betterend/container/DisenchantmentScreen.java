package org.ajdp.betterend.container;

import org.ajdp.betterend.BetterEndMod;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class DisenchantmentScreen extends ContainerScreen<DisenchantmentContainer> {
	public static final ResourceLocation DISENCHANTMENT_RESOURCE = BetterEndMod
			.location("textures/gui/container/disenchanting_table.png");

	public DisenchantmentScreen(DisenchantmentContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
	}

	public void func_230430_a_(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
		this.func_230446_a_(p_230430_1_);
		super.func_230430_a_(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
		RenderSystem.disableBlend();
		this.func_230459_a_(p_230430_1_, p_230430_2_, p_230430_3_);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void func_230450_a_(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.field_230706_i_.getTextureManager().bindTexture(DISENCHANTMENT_RESOURCE);
		int i = this.guiLeft;
		int j = (this.field_230709_l_ - this.ySize) / 2;
		this.func_238474_b_(stack, i, j, 0, 0, this.xSize, this.ySize);
	}

}
