package org.ajdp.betterend.structures;

import java.util.Random;
import java.util.function.Consumer;

import com.mojang.serialization.Codec;

import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class DruideHutStructure extends Structure<NoFeatureConfig> {

	public DruideHutStructure(Codec<NoFeatureConfig> p_i231997_1_) {
		super(p_i231997_1_);
	}

	@Override
	public IStartFactory<NoFeatureConfig> getStartFactory() {
		return Start::new;
	}

	@Override
	protected boolean func_230365_b_() {
		return false;
	}

	private static int getYPosForStructure(int chunkX, int chunkY, ChunkGenerator generatorIn) {
		return getYPosForStructure(chunkX, chunkY, generatorIn, (rotation) -> {
		});
	}

	private static int getYPosForStructure(int chunkX, int chunkY, ChunkGenerator generatorIn,
			Consumer<Rotation> rotationSetter) {
		Random random = new Random((long) (chunkX + chunkY * 10387313));
		Rotation rot = Rotation.randomRotation(random);
		int i = 10;
		int j = 10;
		if (rot == Rotation.CLOCKWISE_90) {
			i = -10;
		} else if (rot == Rotation.CLOCKWISE_180) {
			i = -10;
			j = -10;
		} else if (rot == Rotation.COUNTERCLOCKWISE_90) {
			j = -10;
		}

		int k = (chunkX << 4) + 7;
		int l = (chunkY << 4) + 7;
		int i1 = generatorIn.func_222531_c(k, l, Heightmap.Type.WORLD_SURFACE_WG);
		int j1 = generatorIn.func_222531_c(k, l + j, Heightmap.Type.WORLD_SURFACE_WG);
		int k1 = generatorIn.func_222531_c(k + i, l, Heightmap.Type.WORLD_SURFACE_WG);
		int l1 = generatorIn.func_222531_c(k + i, l + j, Heightmap.Type.WORLD_SURFACE_WG);
		int biggest = Math.max(Math.max(i1, j1), Math.max(k1, l1));
		Rotation add;
		if (biggest == i1)
			add = Rotation.NONE;
		else if (biggest == j1) {
			add = Rotation.COUNTERCLOCKWISE_90;
		} else if (biggest == k1) {
			add = Rotation.CLOCKWISE_180;
		} else if (biggest == l1) {
			add = Rotation.CLOCKWISE_90;
		} else {
			System.err.println("rotation error!");
			add = Rotation.NONE;
		}
		rotationSetter.accept(rot.add(add));
		return Math.min(Math.min(i1, j1), Math.min(k1, l1));
	}

	@Override
	protected boolean func_230363_a_(ChunkGenerator p_230363_1_, BiomeProvider p_230363_2_, long p_230363_3_,
			SharedSeedRandom p_230363_5_, int p_230363_6_, int p_230363_7_, Biome p_230363_8_, ChunkPos p_230363_9_,
			NoFeatureConfig p_230363_10_) {
		return getYPosForStructure(p_230363_6_, p_230363_7_, p_230363_1_) >= 60;
	}

	@Override
	public String getStructureName() {
		return "druide_hut";
	}

	public static class Start extends StructureStart<NoFeatureConfig> {
		private Rotation rot;

		public Start(Structure<NoFeatureConfig> p_i225876_1_, int p_i225876_2_, int p_i225876_3_,
				MutableBoundingBox p_i225876_4_, int p_i225876_5_, long p_i225876_6_) {
			super(p_i225876_1_, p_i225876_2_, p_i225876_3_, p_i225876_4_, p_i225876_5_, p_i225876_6_);
		}

		@Override
		public void func_230364_a_(ChunkGenerator p_230364_1_, TemplateManager p_230364_2_, int p_230364_3_,
				int p_230364_4_, Biome p_230364_5_, NoFeatureConfig p_230364_6_) {
			int i = getYPosForStructure(p_230364_3_, p_230364_4_, p_230364_1_, (rotation) -> rot = rotation) - 4;
			if (i > 56) {
				this.components.add(
						new DruideHutPiece(p_230364_2_, p_230364_3_ * 16 + 8, i, p_230364_4_ * 16 + 8, this.rand, rot));
				recalculateStructureSize();
			}
		}

	}

}
