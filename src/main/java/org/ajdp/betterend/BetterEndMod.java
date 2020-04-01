package org.ajdp.betterend;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.ajdp.betterend.biomes.ModBiomes;
import org.ajdp.betterend.biomes.ModFillerBlockTypes;
import org.ajdp.betterend.block.EndGrassBlock;
import org.ajdp.betterend.block.ModBlocks;
import org.ajdp.betterend.container.DisenchantmentScreen;
import org.ajdp.betterend.container.EnderCraftingScreen;
import org.ajdp.betterend.container.EndstoneFurnaceScreen;
import org.ajdp.betterend.container.ModContainerTypes;
import org.ajdp.betterend.entity.ModBoatTypes;
import org.ajdp.betterend.item.enchantment.ModEnchantments;
import org.ajdp.betterend.items.EnchantedEssenceItem;
import org.ajdp.betterend.items.ModArmorMaterials;
import org.ajdp.betterend.items.ModItems;
import org.ajdp.betterend.particles.ModParticleTypes;
import org.ajdp.betterend.potions.ModPotions;
import org.ajdp.betterend.recipe.ModRecipeSerializer;
import org.ajdp.betterend.recipe.ModRecipeTypes;
import org.ajdp.betterend.tileentity.ModTileEntityTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.merchant.villager.VillagerTrades.ITrade;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.RepairContainer;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.particles.ParticleType;
import net.minecraft.potion.Potion;
import net.minecraft.state.IProperty;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.FarmlandTrampleEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BetterEndMod.MODID)
public class BetterEndMod {
	// Directly reference a log4j logger.
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MODID = "betterend";
	public static final List<Supplier<Enchantment>> NON_TRAIDING_ENCHANTMENT = new ArrayList<>(
			ImmutableList.of(() -> ModEnchantments.FLY_ABILLITY));
	public static final List<Supplier<Block>> PURPLE_FIRE_BLOCKS = new ArrayList<>(
			ImmutableList.of(() -> Blocks.END_STONE, () -> ModBlocks.END_GRASS));

	public static final Consumer<? extends Throwable> DEFAULT_ERROR = t -> {
		throw new RuntimeException(t);
	};

	public BetterEndMod() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SuppressWarnings("unchecked")
	private void setup(final FMLCommonSetupEvent event) {
		LOGGER.info(MODID + " setup!");
		try {
			Field validBlocks = null;
			for (Field field : TileEntityType.class.getDeclaredFields()) {
				if (field.getType().isAssignableFrom(Set.class)) {
					validBlocks = field;
				}
			}
			if (validBlocks == null)
				throw new RuntimeException("field not found");
			validBlocks.set(TileEntityType.SIGN,
					new ImmutableSet.Builder<>().addAll((Set<Block>) validBlocks.get(TileEntityType.SIGN))
							.add(ModBlocks.ENDWOOD_SIGN).add(ModBlocks.ENDWOOD_WALL_SIGN).build());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
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
			}
		}
		ShapedRecipe.setCraftingSize(5, 5);
		for (Int2ObjectMap<VillagerTrades.ITrade[]> o : VillagerTrades.VILLAGER_DEFAULT_TRADES.values()) {
			for (int k : o.keySet()) {
				ITrade[] trades = o.get(k);
				for (int i = 0; i < trades.length; i++) {
					ITrade trade = trades[i];
					if (trade instanceof VillagerTrades.EnchantedBookForEmeraldsTrade
							|| trade instanceof VillagerTrades.EnchantedItemForEmeraldsTrade) {
						trades[i] = new ITrade() {

							@Override
							public MerchantOffer getOffer(Entity trader, Random rand) {
								while (true) {
									MerchantOffer orig = trade.getOffer(trader, rand);
									ItemStack selling = orig.getSellingStack();
									Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(selling);
									if (map.keySet().stream().anyMatch(e -> isNonTraidingEnchantment(e))) {
										continue;
									}
									return orig;
								}
							}
						};
					}
				}
			}
		}
	}

	public boolean isNonTraidingEnchantment(Enchantment e) {
		return NON_TRAIDING_ENCHANTMENT.stream().anyMatch(e1 -> e1.get() == e);
	}

	private void doClientStuff(final FMLClientSetupEvent event) {
		ScreenManager.registerFactory(ModContainerTypes.ENDSTONE_FURNACE, EndstoneFurnaceScreen::new);
		ScreenManager.registerFactory(ModContainerTypes.ENDER_CRAFTING, EnderCraftingScreen::new);
		ScreenManager.registerFactory(ModContainerTypes.DISENCHANTMENT, DisenchantmentScreen::new);
		RenderTypeLookup.setRenderLayer(ModBlocks.ENDWOOD_DOOR, RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(ModBlocks.PURPLE_FIRE, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.ENDWOOD_SAPLING, RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(ModBlocks.ENDER_PARSNIPS, RenderType.getTranslucent());
		BoatRenderer.BOAT_TEXTURES = new ImmutableList.Builder<ResourceLocation>().add(BoatRenderer.BOAT_TEXTURES)
				.add(location("textures/entity/boat/endwood.png")).build().toArray(new ResourceLocation[0]);
	}

	public static ResourceLocation location(String name) {
		return new ResourceLocation(MODID, name);
	}

	@Mod.EventBusSubscriber
	public static class GameEvents {

		@SubscribeEvent
		public static void onLeftClick(PlayerInteractEvent.LeftClickBlock event) {
			BlockPos pos = event.getPos();
			BlockState state = event.getWorld().getBlockState(pos);
			if (isPurpleFireBlock(state.getBlock())) {
				BlockPos up = pos.up();
				BlockState fire = event.getWorld().getBlockState(up);
				Direction face = event.getFace();
				if (face == Direction.UP && fire.getBlock() == ModBlocks.PURPLE_FIRE) {
					if (event.isCancelable())
						event.setCanceled(true);
					event.getWorld().playEvent(event.getPlayer(), 1009, pos, 0);
					event.getWorld().removeBlock(up, false);
				}
			}
		}

		@SubscribeEvent
		public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
			BlockState block = event.getPlacedBlock();
			if (block.getBlock() instanceof FireBlock && block.getBlock() != ModBlocks.PURPLE_FIRE) {
				BlockPos pos = event.getPos();
				BlockPos down = pos.down();
				BlockState origin = event.getWorld().getBlockState(down);
				if (isPurpleFireBlock(origin.getBlock())) {
					event.getWorld().setBlockState(pos, fireState(block), 8);
				}
			}
		}

		@SubscribeEvent
		public static void playerTick(TickEvent.PlayerTickEvent event) {
			PlayerEntity player = event.player;
			if (!(player.isCreative() || player.isSpectator())) {
				boolean flag = true;
				for (ItemStack slot : player.inventory.armorInventory) {
					if (!slot.isEnchanted() || slot == ItemStack.EMPTY) {
						flag = false;
						break;
					}
					if (EnchantmentHelper.getEnchantments(slot).keySet().stream()
							.noneMatch(e -> e == ModEnchantments.FLY_ABILLITY)) {
						flag = false;
						break;
					}
				}
				if (!flag && player.abilities.isFlying)
					player.abilities.isFlying = false;
				player.abilities.allowFlying = flag;
				if (player.abilities.isFlying && player.world.rand.nextInt(30) == 0) {
					for (ItemStack slot : player.inventory.armorInventory) {
						if (slot == ItemStack.EMPTY) {
							player.abilities.isFlying = false;
							break;
						}
						slot.damageItem(1, player, e -> {
						});
					}
				}
			}

			// update endwood boat exit position

			if (newPos != null) {
				player.setPositionAndUpdate(newPos.x, newPos.y, newPos.z);
				if (delayed) {
					newPos = null;
					delayed = false;
				} else
					delayed = true;
			}
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public static BlockState fireState(BlockState state) {
			BlockState current = ModBlocks.PURPLE_FIRE.getDefaultState();
			for (IProperty<?> prop : state.getBlock().getStateContainer().getProperties()) {
				current = current.with((IProperty) prop, (Comparable) state.get(prop));
			}
			return current;
		}

		@SubscribeEvent
		public static void anvilRepair(AnvilRepairEvent event) {
			PlayerEntity player = event.getPlayer();
			World world = player.world;
			RayTraceResult result = Minecraft.getInstance().objectMouseOver;
			if (result.getType() == Type.BLOCK) {
				BlockPos pos = ((BlockRayTraceResult) result).getPos();
				BlockState state = world.getBlockState(pos);
				if (state.getBlock() == Blocks.ANVIL) {
					onAnvilRepair(event, pos, state, world);
				} else if (state.getBlock() == ModBlocks.AZULIUM_ANVIL) {
					onAzuliumAnvilRepair(event, pos, state, world);
				}
			} else {
				System.err.println("anvil not found!");
			}

		}

		public static void onAnvilRepair(AnvilRepairEvent event, BlockPos pos, BlockState state, World world) {

		}

		public static void onAzuliumAnvilRepair(AnvilRepairEvent event, BlockPos pos, BlockState state, World world) {
			event.setBreakChance(0);
		}

		@SubscribeEvent
		public static void onHoeUse(PlayerInteractEvent.RightClickBlock event) {
			PlayerEntity player = event.getPlayer();
			BlockPos pos = event.getPos();
			World world = event.getWorld();
			BlockState state = world.getBlockState(pos);
			ItemStack item = player.getHeldItem(event.getHand());
			if (state.getBlock() == Blocks.END_STONE && EndGrassBlock.isEffectiveItem(item)) {
				BlockState newState = ModBlocks.END_FARMLAND.getDefaultState();
				if (newState.isValidPosition(world, pos)) {
					world.setBlockState(pos, newState);
					world.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
					player.swingArm(event.getHand());
				}
			}
		}

		public static Container getOpenContainer() {
			return Minecraft.getInstance() != null
					? Minecraft.getInstance().player != null ? Minecraft.getInstance().player.openContainer : null
					: null;
		}

		public static boolean isActiveBlockAnAzuliumAnvil() {
			Container cont = getOpenContainer();
			if (cont != null && cont instanceof RepairContainer) {
				RepairContainer r = (RepairContainer) cont;
				System.out.println(r.field_216980_g == IWorldPosCallable.DUMMY);
				if (r.field_216980_g != null) {
					r.field_216980_g.apply((world, pos) -> {
						if (world.getBlockState(pos) == null)
							System.out.println("no blockstate");
						else
							System.out.println(world.getBlockState(pos).getBlock().getRegistryName());
						return null;
					});
				}
				return false;
			}
			return false;
		}

		public static boolean isAzuliumAnvil(World world, BlockPos pos) {
			BlockState state = world.getBlockState(pos);
			return state.getBlock() == ModBlocks.AZULIUM_ANVIL;
		}

		@SubscribeEvent
		public static void anvilUpdate(AnvilUpdateEvent event) {
			ItemStack itemstack = event.getLeft();
			ItemStack itemstack2 = event.getRight();
			if (itemstack.getItem() != Items.ENCHANTED_BOOK || itemstack2.getItem() != Items.ENCHANTED_BOOK) {
				Map<Enchantment, Integer> map1 = EnchantmentHelper.getEnchantments(itemstack2);
				if (map1.keySet().stream().anyMatch(e -> e == ModEnchantments.FLY_ABILLITY)) {
					boolean flag = itemstack.getItem() instanceof ArmorItem
							&& ((ArmorItem) itemstack.getItem()).getArmorMaterial() == ModArmorMaterials.RUBY;
					if (!flag) {
						event.setCanceled(true);
						event.setOutput(ItemStack.EMPTY);
						event.setCost(0);
						return;
					}
				}
			}
			if (itemstack.getItem() == Items.BOOK && itemstack.getCount() == 1
					&& itemstack2.getItem() instanceof EnchantedEssenceItem) {
				EnchantedEssenceItem essence = (EnchantedEssenceItem) itemstack2.getItem();
				Enchantment e = essence.getEnchantment();
				int level = e.getMaxLevel();
				int newLevel = level >= itemstack2.getCount() ? itemstack2.getCount() : level;
				ItemStack book = EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(e, newLevel));
				event.setOutput(book);
				event.setMaterialCost(newLevel);
				event.setCost(2 * (e.isTreasureEnchantment() ? 2 : 1) * newLevel);
			}
			RayTraceResult result = Minecraft.getInstance().objectMouseOver;
			if (result.getType() == Type.BLOCK) {
				World world = Minecraft.getInstance().world;
				BlockPos pos = ((BlockRayTraceResult) result).getPos();
				BlockState state = world.getBlockState(pos);
				if (state.getBlock() == ModBlocks.AZULIUM_ANVIL) {
					onAzuliumAnvilUpdate(event, world, pos, state);
				}
			}
		}

		public static void onAzuliumAnvilUpdate(AnvilUpdateEvent event, World world, BlockPos pos, BlockState state) {
			ItemStack itemstack1 = event.getLeft();
			ItemStack itemstack2 = event.getRight();
			if (itemstack1.getItem() == itemstack2.getItem()) {
				if (itemstack1.isEnchanted() && itemstack2.isEnchantable() && !itemstack2.isEnchanted()) {
					Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(itemstack1);
					ItemStack output = itemstack2.copy();
					int cost = 0;
					for (Enchantment e : enchantments.keySet()) {
						int level = enchantments.get(e);
						cost += level * (e.isTreasureEnchantment() ? 2 : 1);
						output.addEnchantment(e, level);
					}
					event.setOutput(output);
					event.setCost(cost);
					event.setMaterialCost(1);
				}
			}
		}

		@SubscribeEvent
		public static void farmlandTrample(FarmlandTrampleEvent event) {
			if (event.getWorld().getBlockState(event.getPos()).getBlock() == ModBlocks.END_FARMLAND)
				event.setCanceled(true);
		}

		@SubscribeEvent
		public static void onEntityAdd(EntityJoinWorldEvent event) {
			if (event.getEntity() instanceof BoatEntity) {
				BoatEntity boat = (BoatEntity) event.getEntity();
				if (boat.getBoatType() == ModBoatTypes.ENDWOOD) {
					boat.getDataManager().set(Entity.NO_GRAVITY, true);
				}
			}
		}

		private static Vec3d newPos;
		private static boolean delayed;

		@SubscribeEvent
		public static void mountEvent(EntityMountEvent event) {
			Entity entity = event.getEntityBeingMounted();
			Entity mountEntity = event.getEntityMounting();
			if (entity instanceof BoatEntity && ((BoatEntity) entity).getBoatType() == ModBoatTypes.ENDWOOD) {
				Vec3d center = entity.getBoundingBox().getCenter();
				Vec3d mountCenter = mountEntity.getBoundingBox().getCenter();
				double x = entity.getPosX() + center.x - mountCenter.x, y = center.y + 1,
						z = entity.getPosZ() + center.z - mountCenter.z;
				mountEntity.prevPosX = x;
				mountEntity.prevPosY = y;
				mountEntity.prevPosZ = z;
				mountEntity.setPosition(x, y, z);
				newPos = new Vec3d(x, y, z);
				// update again in GameEvents.playerTick
			}
		}
	}

	@Mod.EventBusSubscriber(bus = Bus.MOD)
	public static class RegistryEvents {
		@SubscribeEvent
		public static void registerParticles(ParticleFactoryRegisterEvent event) {
			Minecraft.getInstance().particles.registerFactory(ModParticleTypes.PURPLE_FLAME,
					FlameParticle.Factory::new);
		}

		@SubscribeEvent
		public static void registerBlocks(final RegistryEvent.Register<Block> event) {
			ModBlocks.registerBlocks(event.getRegistry());
		}

		@SubscribeEvent
		public static void registerItems(final RegistryEvent.Register<Item> event) {
			ModItems.registerItems(event.getRegistry());
		}

		@SubscribeEvent
		public static void registerBiomes(RegistryEvent.Register<Biome> event) {
			ModBiomes.registerBiomes(event.getRegistry());
		}

		@SubscribeEvent
		public static void registerRecipeTypes(RegistryEvent.Register<? extends IRecipeType<?>> event) {
			ModRecipeTypes.registerRecipeTypes(event.getRegistry());
		}

		@SubscribeEvent
		public static void registerTileEntityTypes(RegistryEvent.Register<TileEntityType<?>> event) {
			ModTileEntityTypes.registerTileEntityTypes(event.getRegistry());
		}

		@SubscribeEvent
		public static void registerRecipeSerializer(RegistryEvent.Register<IRecipeSerializer<?>> event) {
			ModRecipeSerializer.registerSerializer(event.getRegistry());
		}

		@SubscribeEvent
		public static void registerContainerTypes(RegistryEvent.Register<ContainerType<?>> event) {
			ModContainerTypes.registerContainerTypes(event.getRegistry());
		}

		@SubscribeEvent
		public static void registerParticleTypes(RegistryEvent.Register<ParticleType<?>> event) {
			ModParticleTypes.registerParticleTypes(event.getRegistry());
		}

		@SubscribeEvent
		public static void registerEnchantments(RegistryEvent.Register<Enchantment> event) {
			ModEnchantments.registerEnchantments(event.getRegistry());
		}

		@SubscribeEvent
		public static void registerPotions(RegistryEvent.Register<Potion> event) {
			ModPotions.registerPotions(event.getRegistry());
		}
	}

	public static boolean isPurpleFireBlock(Block block) {
		return PURPLE_FIRE_BLOCKS.stream().anyMatch(e -> e.get() == block);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Throwable> void tryCatch(Runnable test, Consumer<T> error) {
		try {
			test.run();
		} catch (Exception t) {
			tryCatch(() -> error.accept((T) t));
		}
	}

	public static void tryCatch(Runnable test) {
		tryCatch(test, DEFAULT_ERROR);
	}
}
