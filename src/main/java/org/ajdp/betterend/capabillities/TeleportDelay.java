package org.ajdp.betterend.capabillities;

public class TeleportDelay implements ITeleportDelay {
	private int teleportDelay;

	@Override
	public int getDelay() {
		return teleportDelay;
	}

	@Override
	public void setDelay(int delayTicks) {
		this.teleportDelay = delayTicks;
	}

}
