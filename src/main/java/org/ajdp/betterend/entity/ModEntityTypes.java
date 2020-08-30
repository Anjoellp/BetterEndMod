package org.ajdp.betterend.entity;

import java.util.ArrayList;
import java.util.List;

import org.ajdp.betterend.BetterEndMod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(bus = Bus.MOD)
public class ModEntityTypes {
	private static final List<EntityType<?>> registry = new ArrayList<>();
	public static final EntityType<DruideEntity> DRUIDE = register("druide", EntityType.Builder
			.<DruideEntity>create(DruideEntity::new, EntityClassification.MONSTER).size(0.6f, 1.95f).func_233606_a_(8));
	public static final EntityType<EnderSquidEntity> ENDER_SQUID = register("ender_squid",
			EntityType.Builder.<EnderSquidEntity>create(EnderSquidEntity::new, EntityClassification.CREATURE)
					.size(0.8F, 0.8F).func_233606_a_(8));

	@SubscribeEvent
	protected static void registerEntityTypes(RegistryEvent.Register<EntityType<?>> event) {
		event.getRegistry().registerAll(registry.toArray(new EntityType[0]));
	}

	@SuppressWarnings("unchecked")
	private static <T extends Entity> EntityType<T> register(String name, EntityType<T> type) {
		EntityType<T> ret = (EntityType<T>) type.setRegistryName(BetterEndMod.location(name));
		registry.add(ret);
		return ret;
	}

	private static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
		EntityType<T> type = builder.build(name);
		return register(name, type);
	}
}
