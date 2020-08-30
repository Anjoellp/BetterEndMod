package org.ajdp.betterend.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ajdp.betterend.item.ModItems;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class UpdatedEndermanEntity extends EndermanEntity {
	public static final List<Item> ENDERMAN_INVISIBLE = new ArrayList<>(Arrays.<Item>asList(ModItems.RUBY_HELMET));

	public UpdatedEndermanEntity(EntityType<? extends EndermanEntity> type, World worldIn) {
		super(type, worldIn);
	}

	protected boolean shouldAttackPlayer(PlayerEntity player) {
		ItemStack helmet = player.inventory.armorInventory.get(3);
		if (ENDERMAN_INVISIBLE.contains(helmet.getItem()))
			return false;
		return super.shouldAttackPlayer(player);
	}

}
