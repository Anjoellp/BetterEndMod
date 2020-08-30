package org.ajdp.betterend.item;

import java.util.List;

import org.ajdp.betterend.entity.ModBoatTypes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.BoatEntity.Type;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BoatItem;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class EndwoodBoatItem extends BoatItem {
	private final boolean flying;

	public EndwoodBoatItem(Type typeIn, boolean flying, Properties properties) {
		super(typeIn, properties);
		this.flying = flying;
	}

	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		RayTraceResult raytraceresult = rayTrace(worldIn, playerIn, RayTraceContext.FluidMode.ANY);
		if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
			return ActionResult.resultPass(itemstack);
		} else {
			Vector3d vec3d = playerIn.getLook(1.0F);
			// double d0 = 5.0D;
			List<Entity> list = worldIn.getEntitiesInAABBexcluding(playerIn,
					playerIn.getBoundingBox().expand(vec3d.scale(5.0D)).grow(1.0D), field_219989_a);
			if (!list.isEmpty()) {
				Vector3d vec3d1 = playerIn.getEyePosition(1.0F);

				for (Entity entity : list) {
					AxisAlignedBB axisalignedbb = entity.getBoundingBox()
							.grow((double) entity.getCollisionBorderSize());
					if (axisalignedbb.contains(vec3d1)) {
						return ActionResult.resultPass(itemstack);
					}
				}
			}

			if (raytraceresult.getType() == RayTraceResult.Type.BLOCK) {
				BoatEntity boatentity = new BoatEntity(worldIn, raytraceresult.getHitVec().x,
						raytraceresult.getHitVec().y, raytraceresult.getHitVec().z);
				boatentity.setBoatType(flying ? ModBoatTypes.FLYING_ENDWOOD : ModBoatTypes.ENDWOOD);
				boatentity.setNoGravity(flying);
				boatentity.rotationYaw = playerIn.rotationYaw;
				if (!worldIn.hasNoCollisions(boatentity, boatentity.getBoundingBox().grow(-0.1D))) {
					return ActionResult.resultFail(itemstack);
				} else {
					if (!worldIn.isRemote) {
						worldIn.addEntity(boatentity);
						if (!playerIn.abilities.isCreativeMode) {
							itemstack.shrink(1);
						}
					}

					playerIn.addStat(Stats.ITEM_USED.get(this));
					return ActionResult.resultSuccess(itemstack);
				}
			} else {
				return ActionResult.resultPass(itemstack);
			}
		}
	}

	@Override
	public int getItemStackLimit(ItemStack stack) {
		return 1;
	}
}
