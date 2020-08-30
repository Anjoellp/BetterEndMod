package org.ajdp.betterend.biomes;

import java.util.List;
import java.util.Random;
import java.util.Set;

import org.ajdp.betterend.BetterEndMod;
import org.ajdp.betterend.block.ModBlocks;
import org.ajdp.betterend.block.ModFillerBlockTypes;
import org.ajdp.betterend.entity.ModEntityTypes;
import org.ajdp.betterend.structures.ModStructures;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.blockplacer.ColumnBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.TwoLayerFeature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer.Foliage;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(bus = Bus.MOD)
public class ModFeatures {
	public static final Feature<BaseTreeFeatureConfig> END_TREE = register("end_tree",
			new EndTreeFeature(BaseTreeFeatureConfig.field_236676_a_));
	public static final BaseTreeFeatureConfig ENDWOOD_TREE_CONFIG = (new BaseTreeFeatureConfig.Builder(
			new SimpleBlockStateProvider(ModBlocks.ENDWOOD_LOG.getDefaultState()),
			new SimpleBlockStateProvider(ModBlocks.ENDWOOD_LEAVES.getDefaultState()),
			new BlobFoliagePlacer(2, 0, 0, 0, 3), straight(4, 2, 0), new TwoLayerFeature(1, 0, 1))).func_236700_a_()
					.build();
	public static final StructureFeature<NoFeatureConfig, ? extends Structure<NoFeatureConfig>> DRUIDE_HUT = ModStructures.DRUIDE_HUT
			.func_236391_a_(NoFeatureConfig.NO_FEATURE_CONFIG);
	public static final BlockClusterFeatureConfig ENDER_CACTUS_CONFIG = (new BlockClusterFeatureConfig.Builder(
			new SimpleBlockStateProvider(ModBlocks.ENDER_CACTUS.getDefaultState()), new ColumnBlockPlacer(1, 2)))
					.tries(10).func_227317_b_().build();

	@SubscribeEvent
	protected static void register(RegistryEvent.Register<Feature<?>> event) {
		IForgeRegistry<Feature<?>> registry = event.getRegistry();
		registry.registerAll(END_TREE);
	}

	private static AbstractTrunkPlacer straight(int i, int j, int k) {
		return new StraightTrunkPlacer(i, j, k) {
			@Override
			public List<Foliage> func_230382_a_(IWorldGenerationReader p_230382_1_, Random p_230382_2_, int p_230382_3_,
					BlockPos p_230382_4_, Set<BlockPos> p_230382_5_, MutableBoundingBox p_230382_6_,
					BaseTreeFeatureConfig p_230382_7_) {
				List<Foliage> ret = super.func_230382_a_(p_230382_1_, p_230382_2_, p_230382_3_, p_230382_4_,
						p_230382_5_, p_230382_6_, p_230382_7_);
				p_230382_1_.setBlockState(p_230382_4_.down(), Blocks.END_STONE.getDefaultState(), 8);
				return ret;
			}
		};
	}

	@SuppressWarnings("unchecked")
	private static <F extends IFeatureConfig> Feature<F> register(String name, Feature<F> feature) {
		return (Feature<F>) feature.setRegistryName(BetterEndMod.location(name));
	}

	public static void setupEnderFeatures() {
		for (Biome b : ForgeRegistries.BIOMES) {
			if (b.getCategory() == Biome.Category.THEEND) {
				if (b == Biomes.THE_END)
					continue;
				b.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
						Feature.ORE
								.withConfiguration(new OreFeatureConfig(ModFillerBlockTypes.ENDSTONE,
										ModBlocks.AZULIUM_ORE.getDefaultState(), 9))
								.withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(20, 0, 0, 128))));
				b.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
						Feature.ORE
								.withConfiguration(new OreFeatureConfig(ModFillerBlockTypes.ENDSTONE,
										ModBlocks.RUBY_ORE.getDefaultState(), 9))
								.withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(1, 0, 0, 32))));
				b.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
						Feature.ORE
								.withConfiguration(new OreFeatureConfig(ModFillerBlockTypes.ENDSTONE,
										ModBlocks.BOLITIUM_ORE.getDefaultState(), 17))
								.withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(20, 0, 0, 128))));
				b.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE
						.withConfiguration(
								new OreFeatureConfig(ModFillerBlockTypes.ENDSTONE, Blocks.STONE.getDefaultState(), 33))
						.withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(8, 0, 0, 256))));
				b.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE
						.withConfiguration(
								new OreFeatureConfig(ModFillerBlockTypes.ENDSTONE, Blocks.GRAVEL.getDefaultState(), 33))
						.withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(8, 0, 0, 256))));
				b.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
						Feature.ORE
								.withConfiguration(new OreFeatureConfig(ModFillerBlockTypes.ENDSTONE,
										ModBlocks.END_SAND.getDefaultState(), 33))
								.withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(8, 0, 0, 256))));
				b.getSpawns(EntityClassification.CREATURE)
						.add(new Biome.SpawnListEntry(ModEntityTypes.ENDER_SQUID, 50, 1, 4));
			}
		}
	}
}
