package org.ajdp.betterend.block;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import org.ajdp.betterend.BetterEndMod;
import org.ajdp.betterend.util.EnumHelper;

import com.google.common.collect.ImmutableSet;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.LeavesBlock;
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
import net.minecraft.item.HoeItem;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(bus = Bus.MOD)
public class ModBlocks {
	private static final List<Block> registry = new ArrayList<>();
	private static final Map<ResourceLocation, Supplier<Block>> flower_registry = new HashMap<>();
	public static final Block ENDWOOD_LEAVES = registerBlock(p -> new LeavesBlock(p) {
		@Override
		public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
			return Blocks.OAK_LEAVES.getFlammability(state, world, pos, face);
		}
	}, "endwood_leaves", Material.LEAVES, p -> p.hardnessAndResistance(0.2F).sound(SoundType.PLANT).notSolid());
	public static final Block ENDWOOD_SAPLING = registerBlock(EndSaplingBlock::new, "endwood_sapling", Material.PLANTS,
			p -> p.doesNotBlockMovement().tickRandomly().notSolid().variableOpacity().sound(SoundType.PLANT));
	public static final Block ENDWOOD_PLANKS = registerBlock(p -> new Block(p) {
		@Override
		public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
			return Blocks.OAK_PLANKS.getFlammability(state, world, pos, face);
		}
	}, "endwood_planks", Material.WOOD,
			p -> p.hardnessAndResistance(2).harvestTool(ToolType.AXE).sound(SoundType.WOOD));
	public static final Block ENDWOOD_FENCE = registerBlock(p -> new FenceBlock(p) {
		@Override
		public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
			return Blocks.OAK_FENCE.getFlammability(state, world, pos, face);
		}
	}, "endwood_fence", Material.WOOD, p -> p.hardnessAndResistance(2).harvestTool(ToolType.AXE).sound(SoundType.WOOD));
	public static final Block ENDWOOD_FENCE_GATE = registerBlock(p -> new FenceGateBlock(p) {
		@Override
		public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
			return Blocks.OAK_FENCE_GATE.getFlammability(state, world, pos, face);
		}
	}, "endwood_fence_gate", Material.WOOD,
			p -> p.hardnessAndResistance(2).harvestTool(ToolType.AXE).sound(SoundType.WOOD));
	public static final Block ENDWOOD_BUTTON = registerBlock(p -> new WoodButtonBlock(p) {
		@Override
		public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
			return Blocks.OAK_BUTTON.getFlammability(state, world, pos, face);
		}
	}, "endwood_button", Material.WOOD, p -> p.hardnessAndResistance(1f).notSolid().harvestTool(ToolType.AXE));
	public static final Block ENDWOOD_PRESSURE_PLATE = registerBlock(
			p -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, p) {
				@Override
				public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
					return Blocks.OAK_PRESSURE_PLATE.getFlammability(state, world, pos, face);
				}
			}, "endwood_pressure_plate", Material.WOOD,
			p -> p.hardnessAndResistance(2).harvestTool(ToolType.AXE).sound(SoundType.WOOD));
	public static final Block ENDWOOD_SIGN = registerBlock(p -> new StandingSignBlock(p, ModWoodTypes.ENDWOOD) {
		@Override
		public BlockRenderType getRenderType(BlockState state) {
			return BlockRenderType.MODEL;
		}

		@Override
		public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
			return Blocks.OAK_SIGN.getFlammability(state, world, pos, face);
		}
	}, "endwood_sign", Material.WOOD,
			p -> p.hardnessAndResistance(2).harvestTool(ToolType.AXE).doesNotBlockMovement().sound(SoundType.WOOD));
	public static final Block ENDWOOD_WALL_SIGN = registerBlock(p -> new WallSignBlock(p, ModWoodTypes.ENDWOOD) {
		@Override
		public BlockRenderType getRenderType(BlockState state) {
			return BlockRenderType.MODEL;
		}

		@Override
		public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
			return Blocks.OAK_WALL_SIGN.getFlammability(state, world, pos, face);
		}
	}, "endwood_wall_sign", Material.WOOD,
			p -> p.hardnessAndResistance(2).doesNotBlockMovement().harvestTool(ToolType.AXE).sound(SoundType.WOOD));
	public static final Block ENDWOOD_SLAB = registerBlock(p -> new SlabBlock(p) {
		@Override
		public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
			return Blocks.OAK_SLAB.getFlammability(state, world, pos, face);
		}
	}, "endwood_slab", Material.WOOD, p -> p.hardnessAndResistance(2).harvestTool(ToolType.AXE).sound(SoundType.WOOD));
	public static final Block ENDWOOD_STAIRS = registerBlock(
			p -> new StairsBlock(() -> ENDWOOD_PLANKS.getDefaultState(), p) {
				@Override
				public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
					return Blocks.OAK_STAIRS.getFlammability(state, world, pos, face);
				}
			}, "endwood_stairs", Material.WOOD,
			p -> p.hardnessAndResistance(2).harvestTool(ToolType.AXE).sound(SoundType.WOOD));
	public static final Block ENDWOOD_TRAPDOOR = registerBlock(p -> new TrapDoorBlock(p) {
		@Override
		public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
			return Blocks.OAK_TRAPDOOR.getFlammability(state, world, pos, face);
		}
	}, "endwood_trapdoor", Material.WOOD,
			p -> p.hardnessAndResistance(2).harvestTool(ToolType.AXE).sound(SoundType.WOOD).notSolid());
	public static final Block ENDWOOD_DOOR = registerBlock(p -> new DoorBlock(p) {
		@Override
		public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
			return Blocks.OAK_DOOR.getFlammability(state, world, pos, face);
		}
	}, "endwood_door", Material.WOOD,
			p -> p.hardnessAndResistance(2).harvestTool(ToolType.AXE).sound(SoundType.WOOD).notSolid());
	public static final Block STRIPPED_ENDWOOD_LOG = registerBlock(p -> new RotatedPillarBlock(p) {
		@Override
		public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
			return Blocks.STRIPPED_OAK_LOG.getFlammability(state, world, pos, face);
		}
	}, "stripped_endwood_log", Material.WOOD,
			p -> p.hardnessAndResistance(2f).harvestTool(ToolType.AXE).sound(SoundType.WOOD));
	public static final Block ENDWOOD_LOG = registerBlock(p -> {
		return new StrippableBlock(p, StrippableBlock.appendAxis(() -> STRIPPED_ENDWOOD_LOG.getDefaultState())) {
			@Override
			public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
				return Blocks.OAK_LOG.getFlammability(state, world, pos, face);
			}
		};
	}, "endwood_log", Material.WOOD,
			p -> p.hardnessAndResistance(2.0F).harvestTool(ToolType.AXE).sound(SoundType.WOOD));
	public static final Block STRIPPED_ENDWOOD_WOOD = registerBlock(p -> new RotatedPillarBlock(p) {
		@Override
		public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
			return Blocks.STRIPPED_OAK_WOOD.getFlammability(state, world, pos, face);
		}
	}, "stripped_endwood_wood", Material.WOOD,
			p -> p.hardnessAndResistance(2f).harvestTool(ToolType.AXE).sound(SoundType.WOOD));
	public static final Block ENDWOOD_WOOD = registerBlock(p -> {
		return new StrippableBlock(p, StrippableBlock.appendAxis(() -> {
			return STRIPPED_ENDWOOD_WOOD.getDefaultState();
		})) {
			@Override
			public int getFlammability(BlockState state, IBlockReader world, BlockPos pos, Direction face) {
				return Blocks.OAK_WOOD.getFlammability(state, world, pos, face);
			}
		};
	}, "endwood_wood", Material.WOOD, p -> p.hardnessAndResistance(2).harvestTool(ToolType.AXE).sound(SoundType.WOOD));
	public static final Block ENDSTONE_FURNACE = registerBlock(EndstoneFurnaceBlock::new, "endstone_furnace",
			Material.ROCK,
			p -> p.hardnessAndResistance(3f).harvestLevel(1).harvestTool(ToolType.PICKAXE).func_235838_a_(state -> {
				return state.get(EndstoneFurnaceBlock.LIT) ? 15 : 0;
			}).sound(SoundType.STONE));
	public static final Block PURPLE_FIRE = registerBlock(PurpleFireBlock::new, "purple_fire", Material.FIRE,
			p -> p.doesNotBlockMovement().tickRandomly().hardnessAndResistance(0).func_235838_a_(state -> {
				return 15;
			}).sound(SoundType.CLOTH).noDrops());
	public static final Block AZULIUM_ORE = registerBlock(OreBlock::new, "azulium_ore", Material.ROCK,
			p -> p.hardnessAndResistance(3.5f, 8f));
	public static final Block AZULIUM_BLOCK = registerBlock(Block::new, "azulium_block", Material.IRON,
			p -> p.hardnessAndResistance(4f, 7f).sound(SoundType.METAL));
	public static final Block AZULIUM_ANVIL = registerBlock(AzuliumAnvilBlock::new, "azulium_anvil", Material.ANVIL,
			p -> p.hardnessAndResistance(5.0F, 1200.0F).sound(SoundType.ANVIL));
	public static final Block RUBY_ORE = registerBlock(p -> new OreBlock(p), "ruby_ore", Material.ROCK,
			p -> p.hardnessAndResistance(5f).sound(SoundType.STONE).harvestLevel(2).harvestTool(ToolType.PICKAXE));
	public static final Block RUBY_BLOCK = registerBlock(Block::new, "ruby_block", Material.IRON,
			p -> p.hardnessAndResistance(5.5f).sound(SoundType.METAL).harvestLevel(2).harvestTool(ToolType.PICKAXE));
	public static final Block DISENCHANTING_TABLE = registerBlock(DisenchantmentTableBlock::new, "disenchanting_table",
			Material.ROCK, p -> p.hardnessAndResistance(5.0F, 1200.0F).harvestLevel(2).harvestTool(ToolType.PICKAXE)
					.tickRandomly().sound(SoundType.STONE));
	public static final Block END_GRASS = registerBlock(EndGrassBlock::new, "end_grass", Material.ROCK,
			p -> p.hardnessAndResistance(3, 9).harvestLevel(1).tickRandomly().sound(SoundType.STONE));
	public static final Block END_FARMLAND = registerBlock(EndFarmlandBlock::new, "end_farmland", Material.ROCK,
			p -> p.hardnessAndResistance(3, 9).sound(SoundType.STONE).tickRandomly());
	public static final Block ENDER_PARSNIPS = registerBlock(EnderParsnipsBlock::new, "ender_parsnips", Material.PLANTS,
			p -> p.hardnessAndResistance(0).doesNotBlockMovement().notSolid().tickRandomly().sound(SoundType.CROP));;
	public static final Block PURPLE_CAMPFIRE = registerBlock(PurpleCampfireBlock::new, "purple_campfire",
			Material.WOOD, p -> p.hardnessAndResistance(2.0F).sound(SoundType.WOOD).tickRandomly().notSolid()
					.sound(SoundType.WOOD).func_235838_a_(state -> {
						return state.get(PurpleCampfireBlock.LIT) ? 15 : 0;
					}));
	public static final Block ENDWOOD_COMPOSTER = registerBlock(EndwoodComposterBlock::new, "endwood_composter",
			Material.WOOD, p -> p.hardnessAndResistance(0.6f).sound(SoundType.WOOD));
	public static final Block ESSENCE_CRAFTER = registerBlock(EssenceCrafterBlock::new, "essence_crafter",
			Material.ROCK, p -> p.hardnessAndResistance(5.0F, 1200.0F).harvestLevel(2).harvestTool(ToolType.PICKAXE)
					.tickRandomly().sound(SoundType.STONE).notSolid());
	public static final Block BOLITIUM_ORE = registerBlock(OreBlock::new, "bolitium_ore", Material.ROCK,
			p -> p.hardnessAndResistance(6).harvestLevel(1).sound(SoundType.STONE));
	public static final Block BOLITIUM_BLOCK = registerBlock(Block::new, "bolitium_block", Material.ROCK,
			p -> p.hardnessAndResistance(7).harvestLevel(1).sound(SoundType.STONE));
	public static final Block END_SAND = registerBlock(p -> new EndSandBlock(Integer.valueOf("c447bb", 16), p),
			"end_sand", Material.SAND,
			p -> p.harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.5F).sound(SoundType.SAND));
	public static final Block ENDER_CACTUS = registerBlock(EnderCactusBlock::new, "ender_cactus", Material.CACTUS,
			p -> p.tickRandomly().hardnessAndResistance(0.4F).sound(SoundType.CLOTH));
	public static final Block POTTED_ENDER_CACTUS = registerFlower(() -> ENDER_CACTUS, "potted_ender_cactus");
	public static final Block ENDER_CACTUS_FRUIT = registerBlock(EnderCactusFruitBlock::new, "ender_cactus_fruit",
			Material.CACTUS,
			p -> p.doesNotBlockMovement().notSolid().zeroHardnessAndResistance().sound(SoundType.CLOTH).tickRandomly());

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		EndwoodComposterBlock.init();
		IForgeRegistry<Block> registry = event.getRegistry();
		registry.registerAll(ModBlocks.registry.toArray(new Block[0]));
		UpdatedChorusPlantBlock.replaceChorusFruitRegistry(registry);
		registerHoeEffective(ENDWOOD_LEAVES);
		flower_registry.entrySet()
				.forEach(e -> ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(e.getKey(), e.getValue()));
	}

	public static void registerHoeEffective(Block block) {
		try {
			Field field = ObfuscationReflectionHelper.findField(HoeItem.class, "field_234683_c_");
			EnumHelper.makeAccessible(field);
			@SuppressWarnings("unchecked")
			ArrayList<Block> blocks = new ArrayList<>((Set<Block>) field.get(null));
			blocks.add(block);
			field.set(null, ImmutableSet.<Block>copyOf(blocks));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T extends Block> Block registerFlower(Supplier<Block> plant, String name) {
		Block ret = registerBlock(p -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, plant, p), name,
				Material.MISCELLANEOUS, p -> p.zeroHardnessAndResistance().notSolid());
		flower_registry.put(plant.get().getRegistryName(), () -> ret);
		return ret;
	}

	public static <T extends Block> Block registerBlock(Function<AbstractBlock.Properties, T> factory, String name,
			Material mat, Function<AbstractBlock.Properties, AbstractBlock.Properties> prop) {
		Block b = factory.apply(prop.apply(Block.Properties.create(mat))).setRegistryName(BetterEndMod.location(name));
		registry.add(b);
		return b;
	}
}
