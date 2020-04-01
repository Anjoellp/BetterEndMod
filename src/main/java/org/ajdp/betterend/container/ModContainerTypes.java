package org.ajdp.betterend.container;

import org.ajdp.betterend.BetterEndMod;

import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.ContainerType.IFactory;
import net.minecraftforge.registries.IForgeRegistry;

public class ModContainerTypes {
	public static ContainerType<EndstoneFurnaceContainer> ENDSTONE_FURNACE;
	public static ContainerType<EnderWorkbenchContainer> ENDER_CRAFTING;
	public static ContainerType<DisenchantmentContainer> DISENCHANTMENT;
	public static ContainerType<AzuliumRepairContainer> AZULIUM_ANVIL;

	public static void registerContainerTypes(IForgeRegistry<ContainerType<?>> registry) {
		registry.register(ENDSTONE_FURNACE = register("endstone_furnace", EndstoneFurnaceContainer::new));
		registry.register(ENDER_CRAFTING = register("ender_workbench", EnderWorkbenchContainer::new));
		registry.register(DISENCHANTMENT = register("disenchantment", DisenchantmentContainer::new));
		registry.register(AZULIUM_ANVIL = register("azulium_anvil", AzuliumRepairContainer::new));
	}

	@SuppressWarnings("unchecked")
	public static <T extends Container> ContainerType<T> register(String name, IFactory<T> factory) {
		return (ContainerType<T>) new ContainerType<>(factory).setRegistryName(BetterEndMod.location(name));
	}
}
