package org.ajdp.betterend.container;

import java.lang.reflect.Field;

import org.ajdp.betterend.BetterEndMod;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.RepairContainer;
import net.minecraft.util.IWorldPosCallable;

/**
 * this class has the effect that in class {@code BetterEndMod.GameEvents} the
 * azulium and the normal anvil will be seperated
 * 
 * @see BetterEndMod.GameEvents.anvilRepair
 * @author Anjoellp
 *
 */
public class AzuliumRepairContainer extends RepairContainer {
	public AzuliumRepairContainer(int id, PlayerInventory inventory, IWorldPosCallable pos) {
		super(id, inventory, pos);
		System.out.println("no dummy");
		try {
			replaceContainerType();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void replaceContainerType() throws Exception {
		for (Field field : super.getClass().getDeclaredFields()) {
			if (field.getType().equals(ContainerType.class)) {
				field.setAccessible(true);
				field.set(this, ModContainerTypes.AZULIUM_ANVIL);
			}
		}
	}

	public AzuliumRepairContainer(int id, PlayerInventory inventory) {
		super(id, inventory);
		System.out.println("dummy");
		try {
			replaceContainerType();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
