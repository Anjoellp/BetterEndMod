package org.ajdp.betterend.capabillities;

import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class TeleportDelayCapabillity implements ICapabilitySerializable<IntNBT> {
	@CapabilityInject(ITeleportDelay.class)
	public static final Capability<ITeleportDelay> TELEPORT_DELAY = null;
	private LazyOptional<ITeleportDelay> instance = LazyOptional.of(TELEPORT_DELAY::getDefaultInstance);

	public static void register() {
		CapabilityManager.INSTANCE.register(ITeleportDelay.class, new TeleportDelayStorage(), TeleportDelay::new);
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return TELEPORT_DELAY.orEmpty(cap, instance);
	}

	@Override
	public IntNBT serializeNBT() {
		return (IntNBT) TELEPORT_DELAY.getStorage().writeNBT(TELEPORT_DELAY,
				instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null);
	}

	@Override
	public void deserializeNBT(IntNBT nbt) {
		TELEPORT_DELAY.getStorage().readNBT(TELEPORT_DELAY,
				instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null, nbt);
	}

}
