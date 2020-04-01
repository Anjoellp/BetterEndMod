package org.ajdp.betterend.tileentity;

import java.util.Set;
import java.util.function.Supplier;

import org.ajdp.betterend.BetterEndMod;
import org.ajdp.betterend.block.ModBlocks;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.types.Type;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.IForgeRegistry;

public class ModTileEntityTypes {
	public static TileEntityType<EndstoneFurnaceTileEntity> ENDSTONE_FURNACE;

	public static void registerTileEntityTypes(IForgeRegistry<TileEntityType<?>> registry) {
		registry.register(ENDSTONE_FURNACE = registerNewTileEntityType(EndstoneFurnaceTileEntity::new,
				ImmutableSet.of(ModBlocks.ENDSTONE_FURNACE), null, "endstone_furnace"));
	}

	@SuppressWarnings("unchecked")
	public static <R extends TileEntity> TileEntityType<R> registerNewTileEntityType(Supplier<R> factory,
			Set<Block> blocks, Type<?> fixer, String name) {
		return (TileEntityType<R>) new TileEntityType<R>(factory, blocks, fixer)
				.setRegistryName(BetterEndMod.location(name));
	}

	public static <R extends TileEntity> TileEntityType<R> register(String name, Supplier<R> factory, Type<?> fixer,
			Block... blocks) {
		return (TileEntityType<R>) registerNewTileEntityType(factory,
				new ImmutableSet.Builder<Block>().add(blocks).build(), fixer, name);
	}
}
