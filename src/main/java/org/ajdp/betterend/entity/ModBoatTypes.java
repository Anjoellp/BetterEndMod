package org.ajdp.betterend.entity;

import java.util.function.Supplier;

import org.ajdp.betterend.block.ModBlocks;
import org.ajdp.betterend.util.EnumUtils;

import net.minecraft.block.Block;
import net.minecraft.entity.item.BoatEntity;

public class ModBoatTypes {
	public static final BoatEntity.Type ENDWOOD = register("endwood", () -> ModBlocks.ENDWOOD_PLANKS);

	public static BoatEntity.Type register(String name, Supplier<Block> planks) {
		Class<?>[] types = new Class[] { Block.class, String.class };
		Object[] values = new Object[] { planks.get(), name };
		String enumName = name.toUpperCase();
		EnumUtils.addEnum(BoatEntity.Type.class, enumName, types, values);
		return BoatEntity.Type.valueOf(enumName);
	}
}
