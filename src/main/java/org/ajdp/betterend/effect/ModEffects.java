package org.ajdp.betterend.effect;

import java.util.ArrayList;
import java.util.List;

import org.ajdp.betterend.BetterEndMod;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(bus = Bus.MOD)
public class ModEffects {
	private static final List<Effect> registry = new ArrayList<>();
	public static final Effect TELEPORT = register("teleport", EffectType.BENEFICIAL, "258474");

	private static Effect register(String name, Effect effect) {
		Effect e = effect.setRegistryName(BetterEndMod.location(name));
		registry.add(e);
		return e;
	}

	private static Effect register(String name, EffectType type, String color) {
		return register(name, new Effect(type, Integer.parseInt(color, 16)));
	}

	@SubscribeEvent
	protected static void register(RegistryEvent.Register<Effect> event) {
		IForgeRegistry<Effect> registry = event.getRegistry();
		registry.registerAll(ModEffects.registry.toArray(new Effect[0]));
	}
}
