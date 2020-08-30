package org.ajdp.betterend.event;

import java.util.function.Consumer;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;

public class ItemDamageEvent<T extends LivingEntity> extends Event {
	private ItemStack item;
	private int amount;
	private T entity;
	private Consumer<T> onBroken;

	public ItemDamageEvent(ItemStack item, int amount, T entity, Consumer<T> onBroken) {
		this.item = item;
		this.amount = amount;
		this.entity = entity;
		this.onBroken = onBroken;
	}

	public int getAmount() {
		return amount;
	}

	public T getEntity() {
		return entity;
	}

	public ItemStack getItem() {
		return item;
	}

	public Consumer<T> getOnBroken() {
		return onBroken;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
