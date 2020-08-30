package org.ajdp.betterend.sounds;

import java.util.ArrayList;
import java.util.List;

import org.ajdp.betterend.BetterEndMod;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(bus = Bus.MOD)
public class ModSoundEvents {
	private static final List<SoundEvent> registry = new ArrayList<>();
	public static final SoundEvent ENTITY_DRUIDE_AMBIENT = register("entity.druide.ambient");
	public static final SoundEvent ENTITY_DRUIDE_ATTACK = register("entity.druide.attack");
	public static final SoundEvent ENTITY_DRUIDE_DAMAGE = register("entity.druide.damage");
	public static final SoundEvent ENTITY_DRUIDE_DEATH = register("entity.druide.death");

	@SubscribeEvent
	protected static void registerSoundEvents(RegistryEvent.Register<SoundEvent> event) {
		event.getRegistry().registerAll(registry.toArray(new SoundEvent[0]));
	}

	private static SoundEvent register(String name) {
		ResourceLocation loc = BetterEndMod.location(name);
		SoundEvent ret = new SoundEvent(loc).setRegistryName(loc);
		registry.add(ret);
		return ret;
	}

}
