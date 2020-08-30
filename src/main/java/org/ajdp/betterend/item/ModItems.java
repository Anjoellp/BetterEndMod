package org.ajdp.betterend.item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.ajdp.betterend.BetterEndMod;
import org.ajdp.betterend.block.ModBlocks;
import org.ajdp.betterend.entity.ModEntityTypes;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity.Type;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTier;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.Rarity;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SignItem;
import net.minecraft.item.SoupItem;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.TallBlockItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(bus = Bus.MOD)
public class ModItems {
	private static final List<Item> registry = new ArrayList<>();
	public static final Item ENDWOOD_SAPLING = registerBlockItem(ModBlocks.ENDWOOD_SAPLING, "endwood_sapling",
			ItemGroup.DECORATIONS);
	public static final Item ENDWOOD_LOG = registerBlockItem(ModBlocks.ENDWOOD_LOG, "endwood_log",
			ItemGroup.BUILDING_BLOCKS);
	public static final Item ENDWOOD_LEAVES = registerBlockItem(ModBlocks.ENDWOOD_LEAVES, "endwood_leaves",
			ItemGroup.BUILDING_BLOCKS);
	public static final Item ENDWOOD_WOOD = registerBlockItem(ModBlocks.ENDWOOD_WOOD, "endwood_wood",
			ItemGroup.BUILDING_BLOCKS);
	public static final Item ENDWOOD_PLANKS = registerBlockItem(ModBlocks.ENDWOOD_PLANKS, "endwood_planks",
			ItemGroup.BUILDING_BLOCKS);
	public static final Item ENDWOOD_FENCE = registerBlockItem(ModBlocks.ENDWOOD_FENCE, "endwood_fence",
			ItemGroup.DECORATIONS);
	public static final Item ENDWOOD_FENCE_GATE = registerBlockItem(ModBlocks.ENDWOOD_FENCE_GATE, "endwood_fence_gate",
			ItemGroup.REDSTONE);
	public static final Item ENDWOOD_BUTTON = registerBlockItem(ModBlocks.ENDWOOD_BUTTON, "endwood_button",
			ItemGroup.REDSTONE);
	public static final Item ENDWOOD_PRESSURE_PLATE = registerBlockItem(ModBlocks.ENDWOOD_PRESSURE_PLATE,
			"endwood_pressure_plate", ItemGroup.REDSTONE);
	public static final Item ENDWOOD_SIGN = registerItem(
			p -> new SignItem(p, ModBlocks.ENDWOOD_SIGN, ModBlocks.ENDWOOD_WALL_SIGN), "endwood_sign",
			ItemGroup.DECORATIONS);
	public static final Item ENDWOOD_SLAB = registerBlockItem(ModBlocks.ENDWOOD_SLAB, "endwood_slab",
			ItemGroup.BUILDING_BLOCKS);
	public static final Item ENDWOOD_STAIRS = registerBlockItem(ModBlocks.ENDWOOD_STAIRS, "endwood_stairs",
			ItemGroup.BUILDING_BLOCKS);
	public static final Item ENDWOOD_TRAPDOOR = registerBlockItem(ModBlocks.ENDWOOD_TRAPDOOR, "endwood_trapdoor",
			ItemGroup.REDSTONE);
	public static final Item ENDWOOD_DOOR = registerItem(p -> new TallBlockItem(ModBlocks.ENDWOOD_DOOR, p),
			"endwood_door", ItemGroup.REDSTONE);
	public static final Item STRIPPED_ENDWOOD_LOG = registerBlockItem(ModBlocks.STRIPPED_ENDWOOD_LOG,
			"stripped_endwood_log", ItemGroup.BUILDING_BLOCKS);
	public static final Item STRIPPED_ENDWOOD_WOOD = registerBlockItem(ModBlocks.STRIPPED_ENDWOOD_WOOD,
			"stripped_endwood_wood", ItemGroup.BUILDING_BLOCKS);
	public static final Item ENDSTONE_BRICK = registerItem(Item::new, "endstone_brick", ItemGroup.MATERIALS);
	public static final Item ENDSTONE_FURNACE = registerBlockItem(ModBlocks.ENDSTONE_FURNACE, "endstone_furnace",
			ItemGroup.DECORATIONS);
	public static final Item ENDSTONE_PICKAXE;
	public static final Item ENDSTONE_AXE;
	public static final Item ENDSTONE_SHOVEL;
	public static final Item ENDSTONE_SWORD;
	public static final Item ENDSTONE_HOE;
	public static final Item AZULIUM_INGOT = registerItem(Item::new, "azulium_ingot", ItemGroup.MATERIALS);
	public static final Item AZULIUM_BLOCK = registerBlockItem(ModBlocks.AZULIUM_BLOCK, "azulium_block",
			ItemGroup.BUILDING_BLOCKS);
	public static final Item AZULIUM_ORE = registerBlockItem(ModBlocks.AZULIUM_ORE, "azulium_ore",
			ItemGroup.BUILDING_BLOCKS);
	public static final Item AZULIUM_HELMET;
	public static final Item AZULIUM_CHESTPLATE;
	public static final Item AZULIUM_LEGGINGS;
	public static final Item AZULIUM_BOOTS;
	public static final Item AZULIUM_PICKAXE;
	public static final Item AZULIUM_SHOVEL;
	public static final Item AZULIUM_AXE;
	public static final Item AZULIUM_HOE;
	public static final Item AZULIUM_SWORD;
	public static final Item AZULIUM_NUGGET = registerItem(Item::new, "azulium_nugget", ItemGroup.MATERIALS);
	public static final Item AZULIUM_ANVIL = registerBlockItem(ModBlocks.AZULIUM_ANVIL, "azulium_anvil",
			ItemGroup.DECORATIONS);
	public static final Item AZULIUM_APPLE = registerItem(Item::new, "azulium_apple",
			p -> p.group(ItemGroup.FOOD).food(ModFoods.AZULIUM_APPLE));
	public static final Item ENCHANTED_AZULIUM_APPLE = registerItem(p -> new Item(p) {
		@Override
		public boolean hasEffect(ItemStack stack) {
			return true;
		}
	}, "enchanted_azulium_apple",
			p -> p.group(ItemGroup.FOOD).food(ModFoods.ENCHANTED_AZULIUM_APPLE).rarity(Rarity.EPIC));
	public static final Item RUBY_ORE = registerBlockItem(ModBlocks.RUBY_ORE, "ruby_ore", ItemGroup.BUILDING_BLOCKS);
	public static final Item RUBY_BLOCK = registerBlockItem(ModBlocks.RUBY_BLOCK, "ruby_block",
			ItemGroup.BUILDING_BLOCKS);
	public static final Item RUBY = registerItem(Item::new, "ruby", ItemGroup.MATERIALS);
	public static final Item RUBY_AXE;
	public static final Item RUBY_PICKAXE;
	public static final Item RUBY_SHOVEL;
	public static final Item RUBY_SWORD;
	public static final Item RUBY_HOE;
	public static final Item RUBY_HELMET;
	public static final Item RUBY_CHESTPLATE;
	public static final Item RUBY_LEGGINGS;
	public static final Item RUBY_BOOTS;
	public static final Item DRAGON_INGOT = registerItem(Item::new, "dragon_ingot", ItemGroup.MATERIALS);
	public static final Item ELYTRIUM_MUD = registerItem(Item::new, "elytrium_mud", ItemGroup.MATERIALS);
	public static final Item ELYTRIUM_DUST = registerItem(Item::new, "elytrium_dust", ItemGroup.MATERIALS);
	public static final Item ELYTRIUM_INGOT = registerItem(Item::new, "elytrium_ingot", ItemGroup.MATERIALS);
	public static final Item DISENCHANTING_TABLE = registerBlockItem(ModBlocks.DISENCHANTING_TABLE,
			"disenchanting_table", ItemGroup.DECORATIONS);
	public static final Item ENCHANTMENT_ESSENCE = registerItem(p -> new EnchantedEssenceItem(false, p),
			"enchantment_essence", ItemGroup.MATERIALS);
	public static final Item END_GRASS = registerBlockItem(ModBlocks.END_GRASS, "end_grass", ItemGroup.BUILDING_BLOCKS);
	public static final Item ENDER_PARSNIP = registerItem(p -> new BlockNamedItem(ModBlocks.ENDER_PARSNIPS, p),
			"ender_parsnip", p -> p.group(ItemGroup.FOOD).food(ModFoods.ENDER_PARSNIP));
	public static final Item ENDER_PARSNIP_SOUP = registerItem(SoupItem::new, "ender_parsnip_soup",
			p -> p.group(ItemGroup.FOOD).food(ModFoods.ENDER_PARSNIP_SOUP));
	public static final Item ENDWOOD_BOAT = registerItem(p -> new EndwoodBoatItem(Type.OAK, false, p), "endwood_boat",
			ItemGroup.TRANSPORTATION);
	public static final Item FLYING_ENDWOOD_BOAT = registerItem(p -> new EndwoodBoatItem(Type.OAK, true, p),
			"flying_endwood_boat", ItemGroup.TRANSPORTATION);
	public static final Item CURSE_ESSENCE = registerItem(p -> new EnchantedEssenceItem(true, p), "curse_essence",
			ItemGroup.MATERIALS);
	public static final Item COOKED_CHORUS_FRUIT = registerItem(Item::new, "cooked_chorus_fruit",
			p -> p.group(ItemGroup.FOOD).food(ModFoods.COOKED_CHORUS_FRUIT));
	public static final Item PURPLE_CAMPFIRE = registerBlockItem(ModBlocks.PURPLE_CAMPFIRE, "purple_campfire",
			ItemGroup.DECORATIONS);
	public static final Item DARK_AZULIUM_INGOT = registerItem(Item::new, "dark_azulium_ingot", ItemGroup.MISC);
	public static final Item DARK_RUBY_INGOT = registerItem(Item::new, "dark_ruby_ingot", ItemGroup.MISC);
	public static final Item DRUIDE_SPAWN_EGG = registerSpawnEgg(ModEntityTypes.DRUIDE, "druide_spawn_egg",
			Integer.parseInt("6d0064", 16), Integer.parseInt("880000", 16), ItemGroup.MISC);
	public static final Item ENDERMITE_SKIN = registerItem(Item::new, "endermite_skin", ItemGroup.MISC);
	public static final Item ENDWOOD_COMPOSTER = registerBlockItem(ModBlocks.ENDWOOD_COMPOSTER, "endwood_composter",
			ItemGroup.DECORATIONS);
	public static final Item ESSENCE_CRAFTER = registerBlockItem(ModBlocks.ESSENCE_CRAFTER, "essence_crafter",
			ItemGroup.DECORATIONS);
	public static final Item ESSENCE_SWORD = registerItem(p -> new SwordItem(ItemTier.DIAMOND, 3, -2.4F, p),
			"essence_sword", ItemGroup.COMBAT);
	public static final Item PHANTOM_ESSENCE = registerItem(EssenceItem::new, "phantom_essence", ItemGroup.MISC);
	public static final Item ELYTRIUM_ESSENCE = registerItem(EssenceItem::new, "elytrium_essence", ItemGroup.MISC);
	public static final Item BOLITIUM = registerItem(p -> new Item(p) {
		@Override
		public int getBurnTime(ItemStack stack) {
			return 2000;
		}
	}, "bolitium", p -> p.group(ItemGroup.MISC));

	public static final Item BOLITIUM_ORE = registerBlockItem(ModBlocks.BOLITIUM_ORE, "bolitium_ore",
			ItemGroup.BUILDING_BLOCKS);
	public static final Item BOLITIUM_BLOCK = registerItem(p -> new BlockItem(ModBlocks.BOLITIUM_BLOCK, p) {
		@Override
		public int getBurnTime(ItemStack stack) {
			return 20000;
		}
	}, "bolitium_block", ItemGroup.BUILDING_BLOCKS);
	public static final Item END_SAND = registerBlockItem(ModBlocks.END_SAND, "end_sand", ItemGroup.BUILDING_BLOCKS);
	public static final Item MAGIC_ARTEFACT = registerItem(MagicArtefactItem::new, "magic_artefact",
			p -> p.group(ItemGroup.MISC).maxDamage(100));
	public static final Item FIRE_ESSENCE = registerItem(EssenceItem::new, "fire_essence", ItemGroup.MISC);
	public static final Item EXPERIENCE_ESSENCE = registerItem(EssenceItem::new, "experience_essence", ItemGroup.MISC);
	public static final Item ENDER_SQUID_SPAWN_EGG = registerSpawnEgg(ModEntityTypes.ENDER_SQUID,
			"ender_squid_spawn_egg", 1447446, 0, ItemGroup.MISC);
	public static final Item MAGIC_ESSENCE = registerItem(EssenceItem::new, "magic_essence", ItemGroup.MISC);
	public static final Item UNBREAKABLE_ESSENCE = registerItem(UnbreakableEssenceItem::new, "unbreakable_essence",
			ItemGroup.MISC);
	public static final Item ENDER_CACTUS = registerBlockItem(ModBlocks.ENDER_CACTUS, "ender_cactus",
			ItemGroup.DECORATIONS);
	public static final Item ENDER_CACTUS_FRUIT = registerItem(Item::new, "ender_cactus_fruit",
			p -> p.group(ItemGroup.FOOD).food(ModFoods.ENDER_CACTUS_FRUIT));
	static {
		Item[] endstone_tools = initToolsOfTier("endstone", ModItemTier.ENDSTONE);
		ENDSTONE_PICKAXE = endstone_tools[0];
		ENDSTONE_AXE = endstone_tools[1];
		ENDSTONE_SHOVEL = endstone_tools[2];
		ENDSTONE_SWORD = endstone_tools[3];
		ENDSTONE_HOE = endstone_tools[4];
		Item[] azulium_tools = initToolsOfTier("azulium", ModItemTier.AZULIUM);
		AZULIUM_PICKAXE = azulium_tools[0];
		AZULIUM_AXE = azulium_tools[1];
		AZULIUM_SHOVEL = azulium_tools[2];
		AZULIUM_SWORD = azulium_tools[3];
		AZULIUM_HOE = azulium_tools[4];
		Item[] azulium_armor = initArmorOfMaterial("azulium", ModArmorMaterials.AZULIUM);
		AZULIUM_BOOTS = azulium_armor[0];
		AZULIUM_LEGGINGS = azulium_armor[1];
		AZULIUM_CHESTPLATE = azulium_armor[2];
		AZULIUM_HELMET = azulium_armor[3];
		Item[] ruby_tools = initToolsOfTier("ruby", ModItemTier.RUBY);
		RUBY_PICKAXE = ruby_tools[0];
		RUBY_AXE = ruby_tools[1];
		RUBY_SHOVEL = ruby_tools[2];
		RUBY_SWORD = ruby_tools[3];
		RUBY_HOE = ruby_tools[4];
		Item[] ruby_armor = initArmorOfMaterial("ruby", ModArmorMaterials.RUBY);
		RUBY_BOOTS = ruby_armor[0];
		RUBY_LEGGINGS = ruby_armor[1];
		RUBY_CHESTPLATE = ruby_armor[2];
		RUBY_HELMET = ruby_armor[3];
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		IForgeRegistry<Item> registry = event.getRegistry();
		registry.registerAll(ModItems.registry.toArray(new Item[0]));
	}

	private static Item registerSpawnEgg(EntityType<?> type, String name, int color1, int color2,
			Function<Properties, Properties> prop) {
		return registerItem(p -> new SpawnEggItem(type, color1, color2, p), name, prop);
	}

	private static Item registerSpawnEgg(EntityType<?> type, String name, int color1, int color2, ItemGroup group) {
		return registerSpawnEgg(type, name, color1, color2, p -> p.group(group));
	}

	public static Item[] initArmorOfMaterial(String materialName, IArmorMaterial mat) {
		Item[] ret = new Item[4];
		String[] names = new String[] { "boots", "leggings", "chestplate", "helmet" };
		for (int i = 0; i < 4; i++) {
			final int j = i;
			Item armor = registerItem(p -> new ArmorItem(mat,
					EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, j), p),
					materialName + "_" + names[i], ItemGroup.COMBAT);
			ret[i] = armor;
		}
		return ret;
	}

	public static Item[] initToolsOfTier(String tierName, ModItemTier tier) {
		Item[] ret = new Item[5];
		ret[0] = registerItem(p -> new PickaxeItem(tier, 1, -2.8f, p), tierName + "_pickaxe", ItemGroup.TOOLS);
		ret[1] = registerItem(p -> new AxeItem(tier, tier.getAxeData()[0], tier.getAxeData()[1], p), tierName + "_axe",
				ItemGroup.TOOLS);
		ret[2] = registerItem(p -> new ShovelItem(tier, 1.5f, -3, p), tierName + "_shovel", ItemGroup.TOOLS);
		ret[4] = registerItem(p -> new HoeItem(tier, -tier.getHarvestLevel(), tier.getHarvestLevel(), p),
				tierName + "_hoe", ItemGroup.TOOLS);
		ret[3] = registerItem(p -> new SwordItem(tier, 3, -2.4f, p), tierName + "_sword", ItemGroup.COMBAT);
		return ret;
	}

	@SuppressWarnings("unused")
	private static Item registerBlockItem(Block block, String name, Function<Item.Properties, Item.Properties> func) {
		return registerItem(p -> new BlockItem(block, p), name, func);
	}

	private static Item registerBlockItem(Block block, String name, ItemGroup group) {
		return registerItem(p -> new BlockItem(block, p), name, group);
	}

	private static <T extends Item> Item registerItem(Function<Item.Properties, T> factory, String name,
			Item.Properties prop) {
		Item i = factory.apply(prop).setRegistryName(BetterEndMod.location(name));
		registry.add(i);
		return i;
	}

	private static <T extends Item> Item registerItem(Function<Item.Properties, T> factory, String name,
			Function<Item.Properties, Item.Properties> prop) {
		return registerItem(factory, name, prop.apply(new Item.Properties()));
	}

	private static <T extends Item> Item registerItem(Function<Item.Properties, T> factory, String name,
			ItemGroup group) {
		return registerItem(factory, name, p -> p.group(group));
	}
}
