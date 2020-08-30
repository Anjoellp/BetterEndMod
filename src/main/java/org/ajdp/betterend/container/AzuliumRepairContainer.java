package org.ajdp.betterend.container;

import java.lang.reflect.Field;

import org.ajdp.betterend.block.ModBlocks;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.RepairContainer;
import net.minecraft.util.IWorldPosCallable;

public class AzuliumRepairContainer extends RepairContainer {
	public AzuliumRepairContainer(int id, PlayerInventory inventory, IWorldPosCallable pos) {
		super(id, inventory, pos);
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

	@Override
	protected boolean func_230302_a_(BlockState state) {
		return state.isIn(ModBlocks.AZULIUM_ANVIL);
	}

	public AzuliumRepairContainer(int id, PlayerInventory inventory) {
		super(id, inventory);
		try {
			replaceContainerType();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
