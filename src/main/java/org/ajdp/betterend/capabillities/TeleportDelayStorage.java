package org.ajdp.betterend.capabillities;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

public class TeleportDelayStorage implements Capability.IStorage<ITeleportDelay> {

	@Override
	public INBT writeNBT(Capability<ITeleportDelay> capability, ITeleportDelay instance, Direction side) {
		return IntNBT.valueOf(instance.getDelay());
	}

	@Override
	public void readNBT(Capability<ITeleportDelay> capability, ITeleportDelay instance, Direction side, INBT nbt) {
		instance.setDelay(((IntNBT) nbt).getInt());
	}

}
