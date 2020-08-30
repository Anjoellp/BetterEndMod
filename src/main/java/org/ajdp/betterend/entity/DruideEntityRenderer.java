package org.ajdp.betterend.entity;

import org.ajdp.betterend.BetterEndMod;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class DruideEntityRenderer extends MobRenderer<DruideEntity, DruideModel> {

	public DruideEntityRenderer(EntityRendererManager renderManager) {
		super(renderManager, new DruideModel(0), 0.5f);
	}

	@Override
	public ResourceLocation getEntityTexture(DruideEntity entity) {
		return BetterEndMod.location("textures/entity/druide.png");
	}

}
