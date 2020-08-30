package org.ajdp.betterend.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.ajdp.betterend.block.ModBlocks;
import org.ajdp.betterend.item.ModItems;
import org.ajdp.betterend.util.EnumHelper;

import net.minecraft.block.Block;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.BoatEntity.Type;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class ModBoatTypes {
	public static final Map<Type, Function<BoatEntity, Item>> ITEM_FUNCTIONS = new HashMap<>();
	public static final BoatEntity.Type ENDWOOD = register(ModBlocks.ENDWOOD_PLANKS, "endwood", ModItems.ENDWOOD_BOAT);
	public static final BoatEntity.Type FLYING_ENDWOOD = register(ModBlocks.ENDWOOD_PLANKS, "flying_endwood",
			ModItems.FLYING_ENDWOOD_BOAT);
	static {
		ITEM_FUNCTIONS.put(Type.ACACIA, e -> Items.ACACIA_BOAT);
		ITEM_FUNCTIONS.put(Type.BIRCH, e -> Items.BIRCH_BOAT);
		ITEM_FUNCTIONS.put(Type.DARK_OAK, e -> Items.DARK_OAK_BOAT);
		ITEM_FUNCTIONS.put(Type.JUNGLE, e -> Items.JUNGLE_BOAT);
		ITEM_FUNCTIONS.put(Type.OAK, e -> Items.OAK_BOAT);
		ITEM_FUNCTIONS.put(Type.SPRUCE, e -> Items.SPRUCE_BOAT);
	}

	public static final Item getBoatItem(BoatEntity e) {
		return ITEM_FUNCTIONS.get(e.getBoatType()).apply(e);
	}

	public static final Type register(Block asPlank, String name, Item asItem) {
		return register(asPlank, name, e -> asItem);

	}

	public static final Type register(Block asPlank, String name, Function<BoatEntity, Item> itemFunction) {
		try {
			Type type = EnumHelper.create(Type.class, name.toUpperCase(), Type.values().length, asPlank, name);
			ITEM_FUNCTIONS.put(type, itemFunction);
			return type;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
