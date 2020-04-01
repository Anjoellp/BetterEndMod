package org.ajdp.betterend.items;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import org.ajdp.betterend.BetterEndMod;
import org.ajdp.betterend.block.ModBlocks;
import org.ajdp.betterend.entity.ModBoatTypes;
import org.ajdp.betterend.item.enchantment.ModEnchantments;

import com.google.common.collect.ImmutableMap;

import net.minecraft.block.Block;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.BoatItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SignItem;
import net.minecraft.item.SoupItem;
import net.minecraft.item.SwordItem;
import net.minecraft.item.TallBlockItem;
import net.minecraftforge.registries.IForgeRegistry;

public class ModItems {
	public static final Map<Integer, float[]> AXE_DATA = new ImmutableMap.Builder<Integer, float[]>()
			.put(1, new float[] { 7.0F, -3.2F }).put(0, new float[] { 6.0F, -3.2F }).put(2, new float[] { 6.0F, -3.1F })
			.put(3, new float[] { 5.0F, -3.0F }).build();
	public static Item ENDWOOD_SAPLING;
	public static Item ENDWOOD_LOG;
	public static Item ENDWOOD_LEAVES;
	public static Item ENDWOOD_WOOD;
	public static Item ENDWOOD_PLANKS;
	public static Item ENDWOOD_FENCE;
	public static Item ENDWOOD_FENCE_GATE;
	public static Item ENDWOOD_BUTTON;
	public static Item ENDWOOD_PRESSURE_PLATE;
	public static Item ENDWOOD_SIGN;
	public static Item ENDWOOD_SLAB;
	public static Item ENDWOOD_STAIRS;
	public static Item ENDWOOD_TRAPDOOR;
	public static Item ENDWOOD_DOOR;
	public static Item STRIPPED_ENDWOOD_LOG;
	public static Item STRIPPED_ENDWOOD_WOOD;
	public static Item ENDSTONE_BRICK;
	public static Item ENDSTONE_FURNACE;
	public static Item ENDSTONE_PICKAXE;
	public static Item ENDSTONE_AXE;
	public static Item ENDSTONE_SHOVEL;
	public static Item ENDSTONE_SWORD;
	public static Item ENDSTONE_HOE;
	public static Item AZULIUM_INGOT;
	public static Item AZULIUM_BLOCK;
	public static Item AZULIUM_ORE;
	public static Item AZULIUM_HELMET;
	public static Item AZULIUM_CHESTPLATE;
	public static Item AZULIUM_LEGGINGS;
	public static Item AZULIUM_BOOTS;
	public static Item AZULIUM_PICKAXE;
	public static Item AZULIUM_SHOVEL;
	public static Item AZULIUM_AXE;
	public static Item AZULIUM_HOE;
	public static Item AZULIUM_SWORD;
	public static Item AZULIUM_NUGGET;
	public static Item ENDER_CRAFTING_TABLE;
	public static Item AZULIUM_ANVIL;
	public static Item RUBY_ORE;
	public static Item RUBY_BLOCK;
	public static Item RUBY;
	public static Item RUBY_AXE;
	public static Item RUBY_PICKAXE;
	public static Item RUBY_SHOVEL;
	public static Item RUBY_SWORD;
	public static Item RUBY_HOE;
	public static Item RUBY_HELMET;
	public static Item RUBY_CHESTPLATE;
	public static Item RUBY_LEGGINGS;
	public static Item RUBY_BOOTS;
	public static Item DRAGON_INGOT;
	public static Item ELYTRIUM_MUD;
	public static Item ELYTRIUM_DUST;
	public static Item ELYTRIUM_INGOT;
	public static Item DISENCHANTING_TABLE;
	public static Item ENCHANTMENT_ESSENCE;
	public static Item FLY_ABILLITY_ESSENCE;
	public static Item END_GRASS;
	public static Item ENDER_PARSNIP;
	public static Item ENDER_PARSNIP_SOUP;
	public static Item ENDWOOD_BOAT;

	public static void registerItems(IForgeRegistry<Item> registry) {
		registry.register(ENDWOOD_SAPLING = registerBlockItem(ModBlocks.ENDWOOD_SAPLING, "endwood_sapling",
				ItemGroup.DECORATIONS));
		registry.register(
				ENDWOOD_LOG = registerBlockItem(ModBlocks.ENDWOOD_LOG, "endwood_log", ItemGroup.BUILDING_BLOCKS));
		registry.register(ENDWOOD_LEAVES = registerBlockItem(ModBlocks.ENDWOOD_LEAVES, "endwood_leaves",
				ItemGroup.BUILDING_BLOCKS));
		registry.register(
				ENDWOOD_WOOD = registerBlockItem(ModBlocks.ENDWOOD_WOOD, "endwood_wood", ItemGroup.BUILDING_BLOCKS));
		registry.register(ENDWOOD_PLANKS = registerBlockItem(ModBlocks.ENDWOOD_PLANKS, "endwood_planks",
				ItemGroup.BUILDING_BLOCKS));
		registry.register(
				ENDWOOD_FENCE = registerBlockItem(ModBlocks.ENDWOOD_FENCE, "endwood_fence", ItemGroup.DECORATIONS));
		registry.register(ENDWOOD_FENCE_GATE = registerBlockItem(ModBlocks.ENDWOOD_FENCE_GATE, "endwood_fence_gate",
				ItemGroup.REDSTONE));
		registry.register(
				ENDWOOD_BUTTON = registerBlockItem(ModBlocks.ENDWOOD_BUTTON, "endwood_button", ItemGroup.REDSTONE));
		registry.register(ENDWOOD_PRESSURE_PLATE = registerBlockItem(ModBlocks.ENDWOOD_PRESSURE_PLATE,
				"endwood_pressure_plate", ItemGroup.REDSTONE));
		registry.register(
				ENDWOOD_SIGN = registerItem(p -> new SignItem(p, ModBlocks.ENDWOOD_SIGN, ModBlocks.ENDWOOD_WALL_SIGN),
						"endwood_sign", ItemGroup.DECORATIONS));
		registry.register(
				ENDWOOD_SLAB = registerBlockItem(ModBlocks.ENDWOOD_SLAB, "endwood_slab", ItemGroup.BUILDING_BLOCKS));
		registry.register(ENDWOOD_TRAPDOOR = registerBlockItem(ModBlocks.ENDWOOD_TRAPDOOR, "endwood_trapdoor",
				ItemGroup.REDSTONE));
		registry.register(ENDWOOD_STAIRS = registerBlockItem(ModBlocks.ENDWOOD_STAIRS, "endwood_stairs",
				ItemGroup.BUILDING_BLOCKS));
		registry.register(ENDWOOD_DOOR = registerItem(p -> new TallBlockItem(ModBlocks.ENDWOOD_DOOR, p), "endwood_door",
				ItemGroup.REDSTONE));
		registry.register(ENDWOOD_BOAT = registerItem(p -> new BoatItem(ModBoatTypes.ENDWOOD, p), "endwood_boat",
				ItemGroup.TRANSPORTATION));
		registry.register(STRIPPED_ENDWOOD_LOG = registerBlockItem(ModBlocks.STRIPPED_ENDWOOD_LOG,
				"stripped_endwood_log", ItemGroup.BUILDING_BLOCKS));
		registry.register(STRIPPED_ENDWOOD_WOOD = registerBlockItem(ModBlocks.STRIPPED_ENDWOOD_WOOD,
				"stripped_endwood_wood", ItemGroup.BUILDING_BLOCKS));
		registry.register(ENDSTONE_BRICK = registerItem(Item::new, "endstone_brick", ItemGroup.MATERIALS));
		registry.register(ENDSTONE_FURNACE = registerBlockItem(ModBlocks.ENDSTONE_FURNACE, "endstone_furnace",
				ItemGroup.DECORATIONS));
		registerToolsOfTier(registry, "endstone", ModItemTiers.ENDSTONE, (p) -> ENDSTONE_PICKAXE = p,
				(a) -> ENDSTONE_AXE = a, (s) -> ENDSTONE_SHOVEL = s, (h) -> ENDSTONE_HOE = h,
				(s) -> ENDSTONE_SWORD = s);
		registry.register(AZULIUM_INGOT = registerItem(Item::new, "azulium_ingot", ItemGroup.MATERIALS));
		registerToolsOfTier(registry, "azulium", ModItemTiers.AZULIUM, (p) -> AZULIUM_PICKAXE = p,
				(a) -> AZULIUM_AXE = a, (s) -> AZULIUM_SHOVEL = s, (h) -> AZULIUM_HOE = h, (s) -> AZULIUM_SWORD = s);
		registry.register(
				AZULIUM_ORE = registerBlockItem(ModBlocks.AZULIUM_ORE, "azulium_ore", ItemGroup.BUILDING_BLOCKS));
		registry.register(
				AZULIUM_BLOCK = registerBlockItem(ModBlocks.AZULIUM_BLOCK, "azulium_block", ItemGroup.BUILDING_BLOCKS));
		registerArmorOfMaterial(registry, "azulium", ModArmorMaterials.AZULIUM, (h) -> AZULIUM_HELMET = h,
				(c) -> AZULIUM_CHESTPLATE = c, (l) -> AZULIUM_LEGGINGS = l, (b) -> AZULIUM_BOOTS = b);
		registry.register(AZULIUM_NUGGET = registerItem(Item::new, "azulium_nugget", ItemGroup.MATERIALS));
		registry.register(ENDER_CRAFTING_TABLE = registerBlockItem(ModBlocks.ENDER_CRAFTING_TABLE,
				"ender_crafting_table", ItemGroup.DECORATIONS));
		registry.register(
				AZULIUM_ANVIL = registerBlockItem(ModBlocks.AZULIUM_ANVIL, "azulium_anvil", ItemGroup.DECORATIONS));
		registry.register(RUBY = registerItem(Item::new, "ruby", ItemGroup.MATERIALS));
		registry.register(
				RUBY_BLOCK = registerBlockItem(ModBlocks.RUBY_BLOCK, "ruby_block", ItemGroup.BUILDING_BLOCKS));
		registry.register(RUBY_ORE = registerBlockItem(ModBlocks.RUBY_ORE, "ruby_ore", ItemGroup.BUILDING_BLOCKS));
		registerArmorOfMaterial(registry, "ruby", ModArmorMaterials.RUBY, h -> RUBY_HELMET = h,
				c -> RUBY_CHESTPLATE = c, l -> RUBY_LEGGINGS = l, b -> RUBY_BOOTS = b);
		registerToolsOfTier(registry, "ruby", ModItemTiers.RUBY, p -> RUBY_PICKAXE = p, a -> RUBY_AXE = a,
				s -> RUBY_SHOVEL = s, h -> RUBY_HOE = h, s -> RUBY_SWORD = s);
		registry.register(DRAGON_INGOT = registerItem(Item::new, "dragon_ingot", ItemGroup.MATERIALS));
		registry.register(ELYTRIUM_DUST = registerItem(Item::new, "elytrium_dust", ItemGroup.MATERIALS));
		registry.register(ELYTRIUM_MUD = registerItem(Item::new, "elytrium_mud", ItemGroup.MATERIALS));
		registry.register(ELYTRIUM_INGOT = registerItem(Item::new, "elytrium_ingot", ItemGroup.MATERIALS));
		registry.register(DISENCHANTING_TABLE = registerBlockItem(ModBlocks.DISENCHANTING_TABLE, "disenchanting_table",
				ItemGroup.DECORATIONS));
		registry.register(ENCHANTMENT_ESSENCE = registerItem(p -> new Item(p) {
			public boolean hasEffect(ItemStack stack) {
				return true;
			}

			public boolean isEnchantable(ItemStack stack) {
				return false;
			};
		}, "enchantment_essence", ItemGroup.MATERIALS));
		registry.register(FLY_ABILLITY_ESSENCE = registerItem(
				p -> new EnchantedEssenceItem(() -> ModEnchantments.FLY_ABILLITY, p), "fly_abillity_essence",
				ItemGroup.COMBAT));
		registry.register(END_GRASS = registerBlockItem(ModBlocks.END_GRASS, "end_grass", ItemGroup.BUILDING_BLOCKS));
		registry.register(ENDER_PARSNIP = registerItem(p -> new BlockNamedItem(ModBlocks.ENDER_PARSNIPS, p),
				"ender_parsnip", p -> p.group(ItemGroup.FOOD).food(ModFoods.ENDER_PARSNIP)));
		registry.register(ENDER_PARSNIP_SOUP = registerItem(SoupItem::new, "ender_parsnip_soup",
				p -> p.group(ItemGroup.FOOD).food(ModFoods.ENDER_PARSNIP_SOUP)));
	}

	@SuppressWarnings("unchecked")
	public static void registerArmorOfMaterial(IForgeRegistry<Item> registry, String materialName, IArmorMaterial mat,
			Consumer<Item> helmetSetter, Consumer<Item> chestplateSetter, Consumer<Item> leggingsSetter,
			Consumer<Item> bootsSetter) {
		String[] names = new String[] { "boots", "leggings", "chestplate", "helmet" };
		Consumer<Item>[] setter = new Consumer[] { bootsSetter, leggingsSetter, chestplateSetter, helmetSetter };
		for (int i = 0; i < 4; i++) {
			final int j = i;
			Item armor = registerItem(p -> new ArmorItem(mat,
					EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, j), p),
					materialName + "_" + names[i], ItemGroup.COMBAT);
			registry.register(armor);
			setter[i].accept(armor);
		}
	}

	public static void registerToolsOfTier(IForgeRegistry<Item> registry, String tierName, IItemTier tier,
			Consumer<Item> pickaxeSetter, Consumer<Item> axeSetter, Consumer<Item> shovelSetter,
			Consumer<Item> hoeSetter, Consumer<Item> swordSetter) {
		Item pickaxe = registerItem(p -> new PickaxeItem(tier, 1, -2.8f, p), tierName + "_pickaxe", ItemGroup.TOOLS);
		registry.register(pickaxe);
		pickaxeSetter.accept(pickaxe);
		Item axe = registerItem(p -> new AxeItem(tier, AXE_DATA.get(tier.getHarvestLevel())[0],
				AXE_DATA.get(tier.getHarvestLevel())[1], p), tierName + "_axe", ItemGroup.TOOLS);
		registry.register(axe);
		axeSetter.accept(axe);
		Item shovel = registerItem(p -> new ShovelItem(tier, 1.5f, -3, p), tierName + "_shovel", ItemGroup.TOOLS);
		registry.register(shovel);
		shovelSetter.accept(shovel);
		Item hoe = registerItem(p -> new HoeItem(tier, tier.getHarvestLevel() - 3, p), tierName + "_hoe",
				ItemGroup.TOOLS);
		registry.register(hoe);
		hoeSetter.accept(hoe);
		Item sword = registerItem(p -> new SwordItem(tier, 3, -2.4f, p), tierName + "_sword", ItemGroup.COMBAT);
		registry.register(sword);
		swordSetter.accept(sword);
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
		return factory.apply(prop).setRegistryName(BetterEndMod.location(name));
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
