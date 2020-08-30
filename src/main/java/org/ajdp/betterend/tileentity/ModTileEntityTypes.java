package org.ajdp.betterend.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import org.ajdp.betterend.BetterEndMod;
import org.ajdp.betterend.block.ModBlocks;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.types.Type;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(bus = Bus.MOD)
public class ModTileEntityTypes {
	private static final List<TileEntityType<?>> registry = new ArrayList<>();
	public static final TileEntityType<EndstoneFurnaceTileEntity> ENDSTONE_FURNACE = register(
			EndstoneFurnaceTileEntity::new, ImmutableSet.of(ModBlocks.ENDSTONE_FURNACE), null, "endstone_furnace");

	@SubscribeEvent
	public static void registerTileEntityTypes(RegistryEvent.Register<TileEntityType<?>> event) {
		IForgeRegistry<TileEntityType<?>> registry = event.getRegistry();
		registry.registerAll(ModTileEntityTypes.registry.toArray(new TileEntityType[0]));
	}

	@SuppressWarnings("unchecked")
	public static <R extends TileEntity> TileEntityType<R> register(Supplier<R> factory, Set<Block> blocks,
			Type<?> fixer, String name) {
		TileEntityType<R> type = (TileEntityType<R>) new TileEntityType<R>(factory, blocks, fixer)
				.setRegistryName(BetterEndMod.location(name));
		registry.add(type);
		return type;
	}

	public static <R extends TileEntity> TileEntityType<R> register(String name, Supplier<R> factory, Type<?> fixer,
			Block... blocks) {
		return (TileEntityType<R>) register(factory, new ImmutableSet.Builder<Block>().add(blocks).build(), fixer,
				name);
	}
}
