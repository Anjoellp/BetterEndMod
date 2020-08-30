package org.ajdp.betterend.biomes;

import org.ajdp.betterend.entity.ModEntityTypes;

import com.google.common.collect.ImmutableList;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class EndForestBiome extends Biome {

	protected EndForestBiome() {
		super((new Biome.Builder()).surfaceBuilder(SurfaceBuilder.DEFAULT, ModSurfaceBuilder.END_GRASS_CONFIG)
				.precipitation(Biome.RainType.NONE).category(Biome.Category.THEEND).depth(0.1F).scale(0.2F)
				.temperature(0.5F).downfall(0.5F)
				.func_235097_a_((new BiomeAmbience.Builder()).func_235246_b_(4159204).func_235248_c_(329011)
						.func_235239_a_(10518688).func_235243_a_(MoodSoundAmbience.field_235027_b_).func_235238_a_())
				.parent((String) null).func_235098_a_(ImmutableList.of(new Biome.Attributes(-0.5f, 1f, 1f, 0, 0.3f))));
		this.func_235063_a_(ModFeatures.DRUIDE_HUT);
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION,
				ModFeatures.END_TREE.withConfiguration(ModFeatures.ENDWOOD_TREE_CONFIG).withPlacement(
						Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));
		this.func_235063_a_(DefaultBiomeFeatures.field_235179_q_);
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 10, 4, 4));
		this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(ModEntityTypes.ENDER_SQUID, 2, 1, 4));

	}
}
