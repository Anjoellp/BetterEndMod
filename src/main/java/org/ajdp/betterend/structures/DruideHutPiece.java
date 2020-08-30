package org.ajdp.betterend.structures;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.ajdp.betterend.BetterEndMod;
import org.ajdp.betterend.entity.DruideEntity;
import org.ajdp.betterend.entity.ModEntityTypes;
import org.ajdp.betterend.item.ModLootTables;

import net.minecraft.block.Blocks;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.BrewingStandTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraftforge.registries.ForgeRegistries;

public class DruideHutPiece extends TemplateStructurePiece {
	private Rotation rotation;

	public DruideHutPiece(TemplateManager man, CompoundNBT nbt) {
		super(ModStructurePieceTypes.DRUIDE_HUT, nbt);
		rotation = Rotation.valueOf(nbt.getString("Rotation").toUpperCase());
		loadTemplate(man, templatePosition, new Random());
	}

	public DruideHutPiece(TemplateManager man, int x, int y, int z, Random rand, Rotation rot) {
		super(ModStructurePieceTypes.DRUIDE_HUT, 0);
		rotation = rot;
		loadTemplate(man, new BlockPos(x, y, z), rand);
	}

	@Override
	protected void readAdditional(CompoundNBT tagCompound) {
		super.readAdditional(tagCompound);
		tagCompound.putString("Rotation", rotation.name().toUpperCase());
	}

	private void loadTemplate(TemplateManager man, BlockPos templatePosition, Random rand) {
		template = man.getTemplate(BetterEndMod.location("druide_hut"));
		if (template == null)
			throw new NullPointerException();
		if (this.rotation == null)
			this.rotation = Rotation.randomRotation(rand);
		setup(template, templatePosition, new PlacementSettings().setRotation(this.rotation).setIgnoreEntities(true)
				.addProcessor(BlockIgnoreStructureProcessor.STRUCTURE_BLOCK));
	}

	@Override
	protected void handleDataMarker(String function, BlockPos pos, IWorld worldIn, Random rand,
			MutableBoundingBox sbb) {
		worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 8);
		if (function.startsWith("Chest")) {
			LockableLootTileEntity.setLootTable(worldIn, rand, pos.down(), ModLootTables.CHEST_DRUIDE_HUT_TREASURE);
		} else if (function.startsWith("BrewingChest")) {
			LockableLootTileEntity.setLootTable(worldIn, rand, pos.down(), ModLootTables.CHEST_DRUIDE_HUT_BREWING);
		} else if (function.startsWith("BrewingStand")) {
			TileEntity e = worldIn.getTileEntity(pos.down());
			if (e instanceof BrewingStandTileEntity) {
				BrewingStandTileEntity entity = (BrewingStandTileEntity) e;
				entity.setInventorySlotContents(0, randomPotion(rand));
				if (rand.nextInt(5) < 4) {
					entity.setInventorySlotContents(1, randomPotion(rand));
					if (rand.nextInt(3) < 2) {
						entity.setInventorySlotContents(2, randomPotion(rand));
					}
				}
				int blazePowderCount = rand.nextInt(15) + 2;
				entity.setInventorySlotContents(4, new ItemStack(Items.BLAZE_POWDER, blazePowderCount));
			}
		} else if (function.startsWith("Druide")) {
			DruideEntity entity = ModEntityTypes.DRUIDE.create(worldIn.getWorld());
			entity.setPosition(pos.getX(), pos.getY(), pos.getZ());
			entity.onInitialSpawn(worldIn, worldIn.getDifficultyForLocation(pos), SpawnReason.STRUCTURE, null, null);
			worldIn.addEntity(entity);
		}
	}

	private ItemStack randomPotion(Random rand) {
		ItemStack stack = new ItemStack(Items.POTION);
		List<Potion> potions = ForgeRegistries.POTION_TYPES.getValues().stream().filter(
				e -> e.getEffects().stream().anyMatch(e1 -> e1.getPotion().getEffectType() != EffectType.NEUTRAL))
				.collect(Collectors.toList());
		int potIndex = rand.nextInt(potions.size());
		Potion pot = potions.get(potIndex);
		stack.getOrCreateTag().putString("Potion", pot.getRegistryName().toString());
		return stack;
	}

}
