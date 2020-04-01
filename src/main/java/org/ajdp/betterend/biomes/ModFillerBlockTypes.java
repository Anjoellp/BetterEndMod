package org.ajdp.betterend.biomes;

import java.util.function.Predicate;

import org.ajdp.betterend.util.EnumUtils;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockMatcher;
import net.minecraft.world.gen.feature.OreFeatureConfig.FillerBlockType;

public class ModFillerBlockTypes {

	public static final FillerBlockType ENDSTONE = newInstance("endstone", new BlockMatcher(Blocks.END_STONE));

	public static FillerBlockType newInstance(String name, Predicate<BlockState> blockState) {
		try {
			Class<?>[] types = new Class[] { String.class, Predicate.class };
			Object[] values = new Object[] { name, blockState };
			String enumName = name.toUpperCase();
			EnumUtils.addEnum(FillerBlockType.class, enumName, types, values);
			FillerBlockType ret = FillerBlockType.valueOf(enumName);
			FillerBlockType.VALUES_MAP.put(name, ret);
			return ret;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}
