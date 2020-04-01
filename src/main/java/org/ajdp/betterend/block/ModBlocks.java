package org.ajdp.betterend.block;

import java.util.function.Function;

import org.ajdp.betterend.BetterEndMod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.LogBlock;
import net.minecraft.block.OreBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WoodButtonBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.registries.IForgeRegistry;

public class ModBlocks {
	public static Block ENDWOOD_LOG;
	public static Block ENDWOOD_LEAVES;
	public static Block ENDWOOD_SAPLING;
	public static Block ENDWOOD_WOOD;
	public static Block ENDWOOD_PLANKS;
	public static Block ENDWOOD_FENCE;
	public static Block ENDWOOD_FENCE_GATE;
	public static Block ENDWOOD_BUTTON;
	public static Block ENDWOOD_PRESSURE_PLATE;
	public static Block ENDWOOD_SIGN;
	public static Block ENDWOOD_WALL_SIGN;
	public static Block ENDWOOD_SLAB;
	public static Block ENDWOOD_STAIRS;
	public static Block ENDWOOD_TRAPDOOR;
	public static Block ENDWOOD_DOOR;
	public static Block STRIPPED_ENDWOOD_LOG;
	public static Block STRIPPED_ENDWOOD_WOOD;
	public static Block ENDSTONE_FURNACE;
	public static Block PURPLE_FIRE;
	public static Block AZULIUM_ORE;
	public static Block AZULIUM_BLOCK;
	public static Block ENDER_CRAFTING_TABLE;
	public static Block AZULIUM_ANVIL;
	public static Block RUBY_ORE;
	public static Block RUBY_BLOCK;
	public static Block DISENCHANTING_TABLE;
	public static Block END_GRASS;
	public static Block END_FARMLAND;
	public static Block ENDER_PARSNIPS;

	public static void registerBlocks(IForgeRegistry<Block> registry) {
		registry.register(END_GRASS = registerBlock(EndGrassBlock::new, "end_grass", Material.ROCK,
				p -> p.hardnessAndResistance(3, 9).harvestLevel(1).tickRandomly().sound(SoundType.STONE)));
		registry.register(ENDWOOD_LOG = registerBlock(
				p -> new StrippableBlock(p, StrippableBlock.appendAxis(() -> STRIPPED_ENDWOOD_LOG.getDefaultState())),
				"endwood_log", Material.WOOD,
				p -> p.hardnessAndResistance(2.0F).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
		registry.register(ENDWOOD_LEAVES = registerBlock(LeavesBlock::new, "endwood_leaves", Material.LEAVES,
				p -> p.hardnessAndResistance(0.2F).sound(SoundType.PLANT).notSolid()));
		registry.register(ENDWOOD_SAPLING = registerBlock(EndSaplingBlock::new, "endwood_sapling", Material.PLANTS,
				p -> p.doesNotBlockMovement().tickRandomly().notSolid().variableOpacity().sound(SoundType.PLANT)));
		registry.register(ENDWOOD_WOOD = registerBlock(
				p -> new StrippableBlock(p, StrippableBlock.appendAxis(() -> STRIPPED_ENDWOOD_WOOD.getDefaultState())),
				"endwood_wood", Material.WOOD,
				p -> p.hardnessAndResistance(2).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
		registry.register(ENDWOOD_PLANKS = registerBlock(Block::new, "endwood_planks", Material.WOOD,
				p -> p.hardnessAndResistance(2).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
		registry.register(ENDWOOD_FENCE = registerBlock(FenceBlock::new, "endwood_fence", Material.WOOD,
				p -> p.hardnessAndResistance(2).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
		registry.register(ENDWOOD_FENCE_GATE = registerBlock(FenceGateBlock::new, "endwood_fence_gate", Material.WOOD,
				p -> p.hardnessAndResistance(2).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
		registry.register(ENDWOOD_BUTTON = registerBlock(WoodButtonBlock::new, "endwood_button", Material.WOOD,
				p -> p.hardnessAndResistance(1f).notSolid().harvestTool(ToolType.AXE)));
		registry.register(ENDWOOD_PRESSURE_PLATE = registerBlock(
				p -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, p), "endwood_pressure_plate",
				Material.WOOD, p -> p.hardnessAndResistance(2).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
		registry.register(ENDWOOD_SIGN = registerBlock(p -> new StandingSignBlock(p, ModWoodTypes.ENDWOOD) {
			@Override
			public BlockRenderType getRenderType(BlockState state) {
				return BlockRenderType.MODEL;
			}
		}, "endwood_sign", Material.WOOD, p -> p.hardnessAndResistance(2).harvestTool(ToolType.AXE)
				.doesNotBlockMovement().sound(SoundType.WOOD)));
		registry.register(ENDWOOD_WALL_SIGN = registerBlock(p -> new WallSignBlock(p, ModWoodTypes.ENDWOOD) {
			@Override
			public BlockRenderType getRenderType(BlockState state) {
				return BlockRenderType.MODEL;
			}
		}, "endwood_wall_sign", Material.WOOD, p -> p.hardnessAndResistance(2).doesNotBlockMovement()
				.harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
		registry.register(ENDWOOD_SLAB = registerBlock(SlabBlock::new, "endwood_slab", Material.WOOD,
				p -> p.hardnessAndResistance(2).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
		registry.register(ENDWOOD_STAIRS = registerBlock(
				p -> new StairsBlock(() -> ENDWOOD_PLANKS.getDefaultState(), p), "endwood_stairs", Material.WOOD,
				p -> p.hardnessAndResistance(2).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
		registry.register(ENDWOOD_TRAPDOOR = registerBlock(TrapDoorBlock::new, "endwood_trapdoor", Material.WOOD,
				p -> p.hardnessAndResistance(2).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
		registry.register(ENDWOOD_DOOR = registerBlock(DoorBlock::new, "endwood_door", Material.WOOD,
				p -> p.hardnessAndResistance(2).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
		registry.register(STRIPPED_ENDWOOD_LOG = registerBlock(p -> new LogBlock(MaterialColor.GREEN, p),
				"stripped_endwood_log", Material.WOOD,
				p -> p.hardnessAndResistance(2f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
		registry.register(STRIPPED_ENDWOOD_WOOD = registerBlock(RotatedPillarBlock::new, "stripped_endwood_wood",
				Material.WOOD, p -> p.hardnessAndResistance(2f).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
		registry.register(ENDSTONE_FURNACE = registerBlock(EndstoneFurnaceBlock::new, "endstone_furnace", Material.ROCK,
				p -> p.hardnessAndResistance(3f).harvestLevel(1).harvestTool(ToolType.PICKAXE).lightValue(15)
						.sound(SoundType.STONE)));
		registry.register(PURPLE_FIRE = registerBlock(PurpleFireBlock::new, "purple_fire", Material.FIRE,
				p -> p.doesNotBlockMovement().tickRandomly().hardnessAndResistance(0).lightValue(15)
						.sound(SoundType.CLOTH).noDrops()));
		registry.register(AZULIUM_ORE = registerBlock(OreBlock::new, "azulium_ore", Material.ROCK,
				p -> p.hardnessAndResistance(3.5f, 8f)));
		registry.register(AZULIUM_BLOCK = registerBlock(Block::new, "azulium_block", Material.IRON,
				p -> p.hardnessAndResistance(4f, 7f).sound(SoundType.METAL)));
		registry.register(ENDER_CRAFTING_TABLE = registerBlock(EnderCraftingTableBlock::new, "ender_crafting_table",
				Material.WOOD, p -> p.hardnessAndResistance(2).harvestTool(ToolType.AXE).sound(SoundType.WOOD)));
		registry.register(AZULIUM_ANVIL = registerBlock(AzuliumAnvilBlock::new, "azulium_anvil", Material.ANVIL,
				p -> p.hardnessAndResistance(5.0F, 1200.0F).sound(SoundType.ANVIL)));
		registry.register(RUBY_ORE = registerBlock(p -> new OreBlock(p), "ruby_ore", Material.ROCK,
				p -> p.hardnessAndResistance(5f).sound(SoundType.STONE).harvestLevel(2).harvestTool(ToolType.PICKAXE)));
		registry.register(RUBY_BLOCK = registerBlock(Block::new, "ruby_block", Material.IRON, p -> p
				.hardnessAndResistance(5.5f).sound(SoundType.METAL).harvestLevel(2).harvestTool(ToolType.PICKAXE)));
		registry.register(DISENCHANTING_TABLE = registerBlock(DisenchantmentTableBlock::new, "disenchanting_table",
				Material.ROCK, p -> p.hardnessAndResistance(5.0F, 1200.0F).harvestLevel(2).harvestTool(ToolType.PICKAXE)
						.tickRandomly().sound(SoundType.STONE)));
		registry.register(END_FARMLAND = registerBlock(EndFarmlandBlock::new, "end_farmland", Material.ROCK,
				p -> p.hardnessAndResistance(3, 9).sound(SoundType.STONE).tickRandomly()));
		registry.register(ENDER_PARSNIPS = registerBlock(EnderParsnipsBlock::new, "ender_parsnips", Material.PLANTS,
				p -> p.hardnessAndResistance(0).doesNotBlockMovement().notSolid().tickRandomly()
						.sound(SoundType.CROP)));
	}

	public static <T extends Block> Block registerBlock(Function<Block.Properties, T> factory, String name,
			Material mat, Function<Block.Properties, Block.Properties> prop) {
		return factory.apply(prop.apply(Block.Properties.create(mat))).setRegistryName(BetterEndMod.location(name));
	}
}
