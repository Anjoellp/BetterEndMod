package org.ajdp.betterend.entity;

import org.ajdp.betterend.items.ModItems;

import net.minecraft.entity.item.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EndwoodBoatEntity extends BoatEntity {
	private final boolean flying;

	public EndwoodBoatEntity(World world, boolean flying, double x, double y, double z) {
		super(world, x, y, z);
		this.flying = flying;
		setNoGravity(flying);
	}

	public boolean isFlying() {
		return flying;
	}

	@Override
	public Item getItemBoat() {
		return flying ? ModItems.FLYING_ENDWOOD_BOAT : ModItems.ENDWOOD_BOAT;
	}
}
