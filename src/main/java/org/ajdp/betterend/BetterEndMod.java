package org.ajdp.betterend;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.ajdp.betterend.biomes.ModFeatures;
import org.ajdp.betterend.block.EndGrassBlock;
import org.ajdp.betterend.block.ModBlocks;
import org.ajdp.betterend.block.PurpleFireBlock;
import org.ajdp.betterend.capabillities.TeleportDelayCapabillity;
import org.ajdp.betterend.container.DisenchantmentScreen;
import org.ajdp.betterend.container.EndstoneFurnaceScreen;
import org.ajdp.betterend.container.EssenceEnchanterScreen;
import org.ajdp.betterend.container.ModContainerTypes;
import org.ajdp.betterend.effect.ModEffects;
import org.ajdp.betterend.entity.DruideEntity;
import org.ajdp.betterend.entity.DruideEntityRenderer;
import org.ajdp.betterend.entity.EnderSquidEntity;
import org.ajdp.betterend.entity.EnderSquidRenderer;
import org.ajdp.betterend.entity.ModBoatTypes;
import org.ajdp.betterend.entity.ModEntityTypes;
import org.ajdp.betterend.entity.UpdatedEndermanEntity;
import org.ajdp.betterend.item.EnchantedEssenceItem;
import org.ajdp.betterend.item.ModArmorMaterials;
import org.ajdp.betterend.item.ModItems;
import org.ajdp.betterend.item.enchantment.INonVillagerEnchantment;
import org.ajdp.betterend.item.enchantment.ModEnchantments;
import org.ajdp.betterend.loot.ModLootFunctions;
import org.ajdp.betterend.particles.ModParticleTypes;
import org.ajdp.betterend.structures.ModStructures;
import org.ajdp.betterend.util.EnumHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.TriConsumer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.SoulFireBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.merchant.villager.VillagerTrades.ITrade;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.RepairContainer;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.loot.LootParameter;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootParameters;
import net.minecraft.network.play.client.CPlayerPacket.PositionPacket;
import net.minecraft.state.Property;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.enchanting.EnchantmentLevelSetEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.world.BlockEvent.FarmlandTrampleEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BetterEndMod.MODID)
public class BetterEndMod {
	// Directly reference a log4j logger.
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MODID = "betterend";

	public BetterEndMod() {
		LOGGER.info("hello setup");
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		LOGGER.info("client hello");
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
		LOGGER.info("client finished");
		MinecraftForge.EVENT_BUS.register(this);
		LOGGER.info("register finished");
	}

	@SuppressWarnings("unchecked")
	private void setup(final FMLCommonSetupEvent event) {
		// used to initialize the ModBoatTypes class
		new ModBoatTypes();
		// used to initialize the ModLootFunctionType class
		new ModLootFunctions();
		TeleportDelayCapabillity.register();
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
		ModFeatures.setupEnderFeatures();

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
		TileEntityType.CAMPFIRE.validBlocks = new ImmutableSet.Builder<Block>()
				.addAll(TileEntityType.CAMPFIRE.validBlocks).add(ModBlocks.PURPLE_CAMPFIRE).build();
		// entity attributes
		GlobalEntityTypeAttributes.put(ModEntityTypes.DRUIDE, DruideEntity.getAttributeModifierMap().func_233813_a_());
		GlobalEntityTypeAttributes.put(ModEntityTypes.ENDER_SQUID, EnderSquidEntity.getAttributeModifierMap());
		// update enderman
		try {
			Field factory = null;
			for (Field field : EntityType.class.getDeclaredFields()) {
				if (field.getType().equals(EntityType.IFactory.class)) {
					factory = field;
					break;
				}
			}
			factory.setAccessible(true);
			factory.set(EntityType.ENDERMAN, (EntityType.IFactory<EndermanEntity>) UpdatedEndermanEntity::new);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		try {
			{
				Field field = LootParameterSet.class.getDeclaredField("all");
				EnumHelper.makeAccessible(field);
				Set<LootParameter<?>> all = (Set<LootParameter<?>>) field.get(LootParameterSets.ENTITY);
				all = ImmutableSet.<LootParameter<?>>builder().addAll(all).add(LootParameters.TOOL).build();
				field.set(LootParameterSets.ENTITY, all);
			}
			// set end stone harvest level
			{
				Field field = ForgeHooks.class.getDeclaredField("blockToolSetter");
				EnumHelper.makeAccessible(field);
				TriConsumer<Block, ToolType, Integer> con = (TriConsumer<Block, ToolType, Integer>) field.get(null);
				con.accept(Blocks.END_STONE, ToolType.PICKAXE, 1);
			}
			// replace end structure settings
			ModStructures.replaceEndStructureSettings();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean isNonTraidingEnchantment(Enchantment e) {
		return e instanceof INonVillagerEnchantment;
	}

	private void doClientStuff(final FMLClientSetupEvent event) {
		ScreenManager.registerFactory(ModContainerTypes.ENDSTONE_FURNACE, EndstoneFurnaceScreen::new);
		ScreenManager.registerFactory(ModContainerTypes.DISENCHANTMENT, DisenchantmentScreen::new);
		ScreenManager.registerFactory(ModContainerTypes.ESSENCE_ENCHANTER, EssenceEnchanterScreen::new);
		RenderTypeLookup.setRenderLayer(ModBlocks.ENDWOOD_DOOR, RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(ModBlocks.ENDWOOD_TRAPDOOR, RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(ModBlocks.PURPLE_FIRE, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.ENDWOOD_SAPLING, RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(ModBlocks.ENDER_PARSNIPS, RenderType.getTranslucent());
		RenderTypeLookup.setRenderLayer(ModBlocks.PURPLE_CAMPFIRE, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.ENDWOOD_COMPOSTER, RenderType.getTranslucent());
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.DRUIDE, DruideEntityRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.ENDER_SQUID, EnderSquidRenderer::new);
		RenderTypeLookup.setRenderLayer(ModBlocks.ESSENCE_CRAFTER, RenderType.getCutoutMipped());
		BoatRenderer.BOAT_TEXTURES = ImmutableList.<ResourceLocation>builder()
				.addAll(Arrays.asList(BoatRenderer.BOAT_TEXTURES)).add(location("textures/entity/boat/endwood.png"))
				.add(location("textures/entity/boat/flying_endwood.png")).build().toArray(new ResourceLocation[0]);
		RenderTypeLookup.setRenderLayer(ModBlocks.ENDER_CACTUS, RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(ModBlocks.ENDER_CACTUS_FRUIT, RenderType.getCutout());
	}

	public static ResourceLocation location(String name) {
		return new ResourceLocation(MODID, name);
	}

	// will be initialized by coremod
	public static boolean isEndermanInvisible(Item item) {
		return item == Items.CARVED_PUMPKIN || item == ModItems.RUBY_HELMET;
	}

	@Mod.EventBusSubscriber
	public static class GameEvents {
		// public static final DataParameter<Integer> PLAYER_NEXT_TELEPORT_TICKS =
		// EntityDataManager
		// .createKey(PlayerEntity.class, DataSerializers.VARINT);
		private static final int PLAYER_TELEPORT_INTERRUPT_TICKS = 60;

		@SubscribeEvent
		public static void blockRightClick(PlayerInteractEvent.RightClickBlock event) {
			// teleport handling
			teleportEvent(event);

		}

		@SubscribeEvent
		public static void rightClick(PlayerInteractEvent.RightClickEmpty event) {
			teleportEvent(event);
		}

//
//		@SubscribeEvent
//		public static void playerLeave(PlayerLoggedOutEvent event) {
//		}

		public static void teleportEvent(PlayerInteractEvent event) {
			PlayerEntity player = event.getPlayer();
			if (player.isSprinting() || player.isSneaking())
				return;
			ItemStack mainHand = player.getHeldItem(Hand.MAIN_HAND);
			ItemStack offHand = player.getHeldItem(Hand.OFF_HAND);
			if (player.isPotionActive(ModEffects.TELEPORT) && mainHand.isEmpty()
					&& (offHand.isEmpty() || offHand.isFood() || offHand.getItem() == Items.SHIELD)) {
				player.getCapability(TeleportDelayCapabillity.TELEPORT_DELAY).ifPresent(delay -> {
					int ticks = delay.getDelay();
					if (ticks > 0)
						return;
					Vector3d dir = player.getLookVec();
					dir = dir.mul(1, 0, 1);
					if (event instanceof RightClickBlock) {
						RightClickBlock block = (RightClickBlock) event;
						if (block.getFace() == Direction.DOWN)
							return;
						BlockPos p = block.getPos().offset(block.getFace());
						tryTeleport(event, new Vector3d(p.getX() + 0.5, p.getY(), p.getZ() + 0.5), player);
					} else
						for (int i = 0; i < 4; i++) {
							for (int j = 2; j >= -2; j--) {
								Vector3d move = dir.mul(5 - i, 5 - i, 5 - i).add(0, j, 0);
								Vector3d tel = player.getPositionVec().add(move);
								if (tryTeleport(event, tel, player))
									return;
							}
						}
				});
			}
		}

		private static boolean tryTeleport(PlayerInteractEvent event, Vector3d tel, PlayerEntity player) {
			BlockPos pos = new BlockPos(tel);
			BlockState s1 = player.world.getBlockState(pos);
			BlockState s2 = player.world.getBlockState(pos.up());
			BlockState s3 = player.world.getBlockState(pos.down());
			if (!s1.isSolid() && !s2.isSolid()) {
				if (!s3.isSolid())
					if (tel.getY() <= 0)
						return false;
					else
						return tryTeleport(event, tel.add(0, -1, 0), player);
				player.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1, 1);
				// player.getDataManager().set(PLAYER_NEXT_TELEPORT_TICKS,
				// PLAYER_TELEPORT_INTERRUPT_TICKS);
				double distance = player.getPositionVec().distanceTo(tel);
				int ticks = (int) (PLAYER_TELEPORT_INTERRUPT_TICKS * (distance / 5));
				player.getCapability(TeleportDelayCapabillity.TELEPORT_DELAY).ifPresent(e -> e.setDelay(ticks));
				if (player instanceof ClientPlayerEntity) {
					player.setPosition(tel.getX(), tel.getY(), tel.getZ());
					ClientPlayerEntity clientPlayer = (ClientPlayerEntity) player;
					clientPlayer.connection.sendPacket(new PositionPacket(tel.x, tel.y, tel.z, false));
				} else
					player.setPositionAndUpdate(tel.getX(), tel.getY(), tel.getZ());
				return true;
			}
			return false;
		}

		@SubscribeEvent
		public static void playerTick(TickEvent.PlayerTickEvent event) {
			PlayerEntity player = event.player;
			if (!(player.isCreative() || player.isSpectator())) {
				boolean flag = true;
				for (ItemStack slot : player.inventory.armorInventory) {
					if (!slot.isEnchanted() || slot == ItemStack.EMPTY
							|| slot.getDamage() / slot.getMaxDamage() >= 0.9) {
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

			// update teleport interupt timer
			player.getCapability(TeleportDelayCapabillity.TELEPORT_DELAY).ifPresent(e -> e.tick());
		}

		@SubscribeEvent
		public static void attachCapabillities(AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof PlayerEntity)
				event.addCapability(location("teleport_delay"), new TeleportDelayCapabillity());
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public static BlockState fireState(BlockState state) {
			BlockState current = ModBlocks.PURPLE_FIRE.getDefaultState();
			for (Property<?> prop : state.getBlock().getStateContainer().getProperties()) {
				current = current.with((Property) prop, (Comparable) state.get(prop));
			}
			return current;
		}

		@SubscribeEvent
		public static void anvilRepair(AnvilRepairEvent event) {
			RepairContainer cont = (RepairContainer) event.getPlayer().openContainer;
			cont.field_234644_e_.consume((world, pos) -> {
				BlockState state = world.getBlockState(pos);
				if (state.getBlock() == ModBlocks.AZULIUM_ANVIL) {
					onAzuliumAnvilRepair(event, pos, state, world);
				}
			});
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
					item.damageItem(1, player, e -> e.sendBreakAnimation(event.getHand()));
					if (event.getSide() == LogicalSide.CLIENT)
						player.swingArm(event.getHand());
				}
			}
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
			if (itemstack.getItem() == Items.BOOK && itemstack2.getItem() instanceof EnchantedEssenceItem) {
				Enchantment e = EnchantedEssenceItem.getEnchantment(itemstack2);
				int level = e.getMaxLevel();
				int newLevel = level >= itemstack2.getCount() ? itemstack2.getCount() : level;
				ItemStack book = EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(e, newLevel));
				event.setOutput(book);
				event.setMaterialCost(newLevel);
				event.setCost(2 * (e.isTreasureEnchantment() ? 2 : 1) * newLevel);
			}
			if (itemstack.getItem() == ModItems.MAGIC_ARTEFACT && itemstack2.getItem() == ModItems.MAGIC_ARTEFACT) {
				ItemStack result = new ItemStack(ModItems.MAGIC_ARTEFACT);
				int charge1 = itemstack.getMaxDamage() - itemstack.getDamage();
				int charge2 = itemstack2.getMaxDamage() - itemstack2.getDamage();
				int newCharge = charge1 + charge2;
				result.setDamage(Math.abs(itemstack.getMaxDamage() - newCharge));
				event.setOutput(result);
				event.setCost(4);
			}
			if (itemstack.getItem().isDamageable() && itemstack2.getItem() == ModItems.UNBREAKABLE_ESSENCE) {
				ItemStack result = itemstack.copy();
				result.getOrCreateTag().putBoolean("Unbreakable", true);
				event.setOutput(result);
				event.setCost(10);
			}
		}

		@SubscribeEvent
		public static void farmlandTrample(FarmlandTrampleEvent event) {
			if (event.getWorld().getBlockState(event.getPos()).getBlock() == ModBlocks.END_FARMLAND)
				event.setCanceled(true);
		}

		@SubscribeEvent
		public static void mountEvent(EntityMountEvent event) {
			Entity entity = event.getEntityBeingMounted();
			Entity mountEntity = event.getEntityMounting();
			if (entity instanceof BoatEntity && ((BoatEntity) entity).getBoatType() == ModBoatTypes.ENDWOOD
					&& entity.hasNoGravity()) {
				Vector3d center = entity.getBoundingBox().getCenter();
				Vector3d mountCenter = mountEntity.getBoundingBox().getCenter();
				double x = entity.getPosX() + center.x - mountCenter.x, y = center.y + 1,
						z = entity.getPosZ() + center.z - mountCenter.z;
				mountEntity.prevPosX = x;
				mountEntity.prevPosY = y;
				mountEntity.prevPosZ = z;
				mountEntity.setPosition(x, y, z);
			}
		}

		@SubscribeEvent
		public static void enchantLevel(EnchantmentLevelSetEvent event) {
			if (event.getItem().getItem() == ModItems.ENCHANTMENT_ESSENCE
					|| event.getItem().getItem() == ModItems.CURSE_ESSENCE) {
				event.setLevel(1);

			}
		}
	}

	// will be fired by coremod
	public static BlockState getFireState(IBlockReader reader, BlockPos pos) {
		if (((World) reader).func_234922_V_() == DimensionType.field_236001_e_)
			return ((PurpleFireBlock) ModBlocks.PURPLE_FIRE).getStateForPlacement(reader, pos);
		BlockPos blockpos = pos.down();
		BlockState blockstate = reader.getBlockState(blockpos);
		return SoulFireBlock.func_235577_c_(blockstate.getBlock()) ? Blocks.field_235335_bO_.getDefaultState()
				: ((FireBlock) Blocks.FIRE).getStateForPlacement(reader, pos);
	}

	@Mod.EventBusSubscriber(bus = Bus.MOD)
	public static class RegistryEvents {
		@SubscribeEvent
		public static void registerParticles(ParticleFactoryRegisterEvent event) {
			Minecraft.getInstance().particles.registerFactory(ModParticleTypes.PURPLE_FLAME,
					FlameParticle.Factory::new);
		}
	}
}
