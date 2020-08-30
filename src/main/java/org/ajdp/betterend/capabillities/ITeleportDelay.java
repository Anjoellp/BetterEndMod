package org.ajdp.betterend.capabillities;

public interface ITeleportDelay {
	void setDelay(int delayTicks);

	int getDelay();

	default void tick() {
		setDelay(getDelay() - 1);
	}
}
