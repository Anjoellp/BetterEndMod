package org.ajdp.betterend.block;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.ajdp.betterend.util.EnumHelper;

import com.google.common.collect.ImmutableList;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChorusPlantBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.registries.IForgeRegistry;

public class UpdatedChorusPlantBlock extends ChorusPlantBlock {
	public static List<Block> VALID_BLOCKS = new ArrayList<>(
			ImmutableList.of(Blocks.END_STONE, ModBlocks.END_SAND, ModBlocks.END_GRASS));

	public UpdatedChorusPlantBlock(Properties builder) {
		super(builder);
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		BlockState blockstate = worldIn.getBlockState(pos.down());
		boolean flag = !worldIn.getBlockState(pos.up()).isAir(worldIn, pos) && !blockstate.isAir(worldIn, pos);

		for (Direction direction : Direction.Plane.HORIZONTAL) {
			BlockPos blockpos = pos.offset(direction);
			Block block = worldIn.getBlockState(blockpos).getBlock();
			if (block == this) {
				if (flag) {
					return false;
				}
				BlockState blockstate1 = worldIn.getBlockState(blockpos.down());
				if (blockstate1.getBlock() == this || isValidBlock(blockstate1)) {
					return true;
				}
			}
		}
		BlockState block = worldIn.getBlockState(pos.down());
		return block.getBlock() == this || isValidBlock(block);
	}

	public static boolean isValidBlock(BlockState state) {
		return VALID_BLOCKS.contains(state.getBlock());
	}

	public static void replaceChorusFruitRegistry(IForgeRegistry<Block> registry) {
		try {
			Field field = ObfuscationReflectionHelper.findField(Blocks.class, "field_185765_cR");
			EnumHelper.makeAccessible(field);
			field.set(null,
					new UpdatedChorusPlantBlock(AbstractBlock.Properties.create(Material.PLANTS, MaterialColor.PURPLE)
							.hardnessAndResistance(0.4F).sound(SoundType.WOOD).notSolid())
									.setRegistryName(new ResourceLocation("chorus_plant")));
			registry.register(Blocks.CHORUS_PLANT);
			Field fruit = ObfuscationReflectionHelper.findField(Blocks.class, "field_185766_cS");
			EnumHelper.makeAccessible(fruit);
			fruit.set(null,
					new UpdatedChorusFlowerBlock((ChorusPlantBlock) Blocks.CHORUS_PLANT,
							AbstractBlock.Properties.create(Material.PLANTS, MaterialColor.PURPLE).tickRandomly()
									.hardnessAndResistance(0.4F).sound(SoundType.WOOD).notSolid())
											.setRegistryName(new ResourceLocation("chorus_flower")));
			registry.register(Blocks.CHORUS_FLOWER);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public BlockState makeConnections(IBlockReader p_196497_1_, BlockPos pos) {
		BlockState state = p_196497_1_.getBlockState(pos.down());
		Block block = state.getBlock();
		Block block1 = p_196497_1_.getBlockState(pos.up()).getBlock();
		Block block2 = p_196497_1_.getBlockState(pos.north()).getBlock();
		Block block3 = p_196497_1_.getBlockState(pos.east()).getBlock();
		Block block4 = p_196497_1_.getBlockState(pos.south()).getBlock();
		Block block5 = p_196497_1_.getBlockState(pos.west()).getBlock();
		return this.getDefaultState()
				.with(DOWN, Boolean.valueOf(block == this || block == Blocks.CHORUS_FLOWER || isValidBlock(state)))
				.with(UP, Boolean.valueOf(block1 == this || block1 == Blocks.CHORUS_FLOWER))
				.with(NORTH, Boolean.valueOf(block2 == this || block2 == Blocks.CHORUS_FLOWER))
				.with(EAST, Boolean.valueOf(block3 == this || block3 == Blocks.CHORUS_FLOWER))
				.with(SOUTH, Boolean.valueOf(block4 == this || block4 == Blocks.CHORUS_FLOWER))
				.with(WEST, Boolean.valueOf(block5 == this || block5 == Blocks.CHORUS_FLOWER));
	}

}
