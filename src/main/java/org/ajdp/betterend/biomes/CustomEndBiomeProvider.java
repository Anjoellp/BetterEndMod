package org.ajdp.betterend.biomes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.LongFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.EndBiomeProvider;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.gen.layer.ZoomLayer;

public class CustomEndBiomeProvider extends EndBiomeProvider {
	public static final Codec<? extends EndBiomeProvider> CODEC = RecordCodecBuilder
			.<CustomEndBiomeProvider>create(
					instance -> instance
							.group(Codec.LONG.fieldOf("seed").forGetter(prov -> prov.field_235315_h_),
									supplierCodec(Codec.unboundedMap(Biome.field_235051_b_,
											Codec.list(Biome.field_235051_b_))).fieldOf("replacements")
													.forGetter(prov -> prov.replacements))
							.apply(instance, CustomEndBiomeProvider::new));
	public static final Map<Biome, Integer> BIOME_WEIGHTS = new HashMap<>();
	private Supplier<Map<Biome, List<Biome>>> replacements;
	private LazyArea area;	

	public static void registerEndBiomeWeight(Biome biome, int weight) {
		BIOME_WEIGHTS.put(biome, weight);
	}

	private static <T> Codec<Supplier<T>> supplierCodec(Codec<T> codec) {
		return codec.xmap(a -> {
			return () -> a;
		}, (s) -> s.get());
	}

	public CustomEndBiomeProvider(long seed) {
		this(seed, () -> ModBiomes.replacements);
	}

	public CustomEndBiomeProvider(long seed, Supplier<Map<Biome, List<Biome>>> replacements) {
		super(seed);
		this.replacements = replacements;
		long i = 1023;
		LongFunction<LazyAreaLayerContext> func = (mod) -> new LazyAreaLayerContext(25, field_235315_h_, mod);
		IAreaFactory<LazyArea> fac = new EndIslandTransformer().apply(func.apply(i++));
		fac = LayerUtil.repeat(i++, ZoomLayer.NORMAL, fac, 3, func);
		fac = EndZoomLayer.INSTANCE.apply(func.apply(i += 5), fac);
		area = fac.make();
	}

	@Override
	protected Codec<? extends BiomeProvider> func_230319_a_() {
		return CODEC;
	}

	@Override
	public List<Biome> func_235203_c_() {
		return replacements.get().entrySet().stream()
				.map(e -> ImmutableList.<Biome>builder().add(e.getKey()).addAll(e.getValue()).build())
				.map(ArrayList::new).reduce((a, b) -> {
					a.addAll(b);
					return a;
				}).orElse(new ArrayList<>()).stream().distinct().collect(Collectors.toList());
	}

	@Override
	public BiomeProvider func_230320_a_(long p_230320_1_) {
		return new CustomEndBiomeProvider(p_230320_1_, replacements);
	}

	@Override
	public Biome getNoiseBiome(int x, int y, int z) {
		Biome b = super.getNoiseBiome(x, y, z);
		Map<Biome, List<Biome>> map = replacements.get();
		if (map.containsKey(b)) {
			List<Biome> val = new ArrayList<>(map.get(b));
			int id = area.getValue(x, z);
			if (id == 0 || id > val.size())
				return b;
			else
				return val.get(id - 1);
		}
		return b;
	}

}
