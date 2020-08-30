package org.ajdp.betterend.container;

import java.util.ArrayList;
import java.util.List;

import org.ajdp.betterend.BetterEndMod;

import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.ContainerType.IFactory;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(bus = Bus.MOD)
public class ModContainerTypes {
	private static final List<ContainerType<?>> registry = new ArrayList<>();
	public static final ContainerType<EndstoneFurnaceContainer> ENDSTONE_FURNACE = register("endstone_furnace",
			EndstoneFurnaceContainer::new);
	public static final ContainerType<DisenchantmentContainer> DISENCHANTMENT = register("disenchantment",
			DisenchantmentContainer::new);
	public static final ContainerType<AzuliumRepairContainer> AZULIUM_ANVIL = register("azulium_anvil",
			AzuliumRepairContainer::new);
	public static final ContainerType<EssenceCrafterContainer> ESSENCE_ENCHANTER = register("essence_enchanter",
			EssenceCrafterContainer::new);

	@SubscribeEvent
	public static void registerContainerTypes(RegistryEvent.Register<ContainerType<?>> event) {
		IForgeRegistry<ContainerType<?>> registry = event.getRegistry();
		registry.registerAll(ModContainerTypes.registry.toArray(new ContainerType[0]));
	}

	@SuppressWarnings("unchecked")
	public static <T extends Container> ContainerType<T> register(String name, IFactory<T> factory) {
		ContainerType<T> c = (ContainerType<T>) new ContainerType<>(factory)
				.setRegistryName(BetterEndMod.location(name));
		registry.add(c);
		return c;
	}
}
