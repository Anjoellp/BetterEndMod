package org.ajdp.betterend.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegistryEntryReplacer {
	public static <T extends IForgeRegistryEntry<T>> void replace(ResourceLocation key, Class<?> holderType,
			IForgeRegistry<T> registry, T newValue) throws Exception {
		T oldValue = registry.getValue(key);
		for (Field field : holderType.getDeclaredFields()) {
			if (!Modifier.isStatic(field.getModifiers()))
				continue;
			field.setAccessible(true);
			Object obj = field.get(null);
			if (obj == oldValue) {
				field.set(null, newValue);
				return;
			}
		}
		throw new IllegalArgumentException("registry entry '" + key + "' not found!");
	}
}
