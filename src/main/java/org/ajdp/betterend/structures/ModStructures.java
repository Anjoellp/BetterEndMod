package org.ajdp.betterend.structures;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ajdp.betterend.BetterEndMod;
import org.ajdp.betterend.util.EnumHelper;

import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

@Mod.EventBusSubscriber(bus = Bus.MOD)
public class ModStructures {
	protected static final Map<Structure<?>, StructureSeparationSettings> customSpreadSettings = new HashMap<>();
	private static final List<Structure<?>> registry = new ArrayList<>();
	public static final Structure<NoFeatureConfig> DRUIDE_HUT = register("druide_hut", Decoration.SURFACE_STRUCTURES,
			new DruideHutStructure(NoFeatureConfig.field_236558_a_), new StructureSeparationSettings(200, 1, 12956427));

	@SubscribeEvent
	protected static void registerStructures(RegistryEvent.Register<Structure<?>> event) {
		event.getRegistry().registerAll(ModStructures.registry.toArray(new Structure[0]));
	}

	@SuppressWarnings("unchecked")
	private static <T extends Structure<?>> T register(String name, GenerationStage.Decoration decoration, T t,
			StructureSeparationSettings settings) {
		T ret = (T) t.setRegistryName(BetterEndMod.location(name));
		registry.add(ret);
		Structure.field_236365_a_.put(name, t);
		Structure.field_236385_u_.put(t, decoration);
		customSpreadSettings.put(t, settings);
		return ret;
	}

	public static void replaceEndStructureSettings() throws Exception {
		Field dimSettings = ObfuscationReflectionHelper.findField(DimensionSettings.Preset.class, "field_236131_k_");
		EnumHelper.makeAccessible(dimSettings);
		DimensionSettings dim = (DimensionSettings) dimSettings.get(DimensionSettings.Preset.field_236125_e_);
		Field structureSettings = ObfuscationReflectionHelper.findField(DimensionSettings.class, "field_236099_c_");
		EnumHelper.makeAccessible(structureSettings);
		DimensionStructuresSettings struc = (DimensionStructuresSettings) structureSettings.get(dim);
		struc.func_236195_a_().put(DRUIDE_HUT, new StructureSeparationSettings(15, 1, 1020585));
		structureSettings.set(dim, struc);
		dimSettings.set(DimensionSettings.Preset.field_236125_e_, dim);
	}
}
