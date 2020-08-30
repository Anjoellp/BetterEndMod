package org.ajdp.betterend.block;

import org.ajdp.betterend.item.ModItems;

import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ComposterBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class EndwoodComposterBlock extends ComposterBlock {
	public static final Object2FloatMap<IItemProvider> ENDWOOD_CHANCES = new Object2FloatOpenHashMap<>();
	public static final Property<Boolean> ELYTRIUM = BooleanProperty.create("elytrium");

	public static void init() {
		registerEndwoodCompostable(ModItems.ENDERMITE_SKIN, 0.4f, 1f);
	}

	private static void registerEndwoodCompostable(IItemProvider item, float elytriumChance, float chance) {
		ENDWOOD_CHANCES.put(item, elytriumChance);
		CHANCES.put(item, chance);
	}

	public EndwoodComposterBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(ELYTRIUM);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return super.getStateForPlacement(context).with(ELYTRIUM, false);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {
		int i = state.get(LEVEL);
		ItemStack itemstack = player.getHeldItem(handIn);
		boolean elytrium = ENDWOOD_CHANCES.containsKey(itemstack.getItem())
				&& worldIn.rand.nextFloat() <= ENDWOOD_CHANCES.getFloat(itemstack.getItem());
		if (elytrium && i < 8) {
			worldIn.setBlockState(pos, state = state.with(ELYTRIUM, true));
		} else if (i == 8) {
			if ((state.get(ELYTRIUM) || elytrium) && worldIn.rand.nextFloat() < 0.2f) {
				double d0 = (double) (worldIn.rand.nextFloat() * 0.7F) + (double) 0.15F;
				double d1 = (double) (worldIn.rand.nextFloat() * 0.7F) + (double) 0.060000002F + 0.6D;
				double d2 = (double) (worldIn.rand.nextFloat() * 0.7F) + (double) 0.15F;
				ItemEntity itementity = new ItemEntity(worldIn, (double) pos.getX() + d0, (double) pos.getY() + d1,
						(double) pos.getZ() + d2, new ItemStack(ModItems.ELYTRIUM_MUD));
				itementity.setDefaultPickupDelay();
				worldIn.addEntity(itementity);
			}
		}
		ActionResultType type = super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
		if (i == 8)
			worldIn.setBlockState(pos, worldIn.getBlockState(pos).with(ELYTRIUM, false));
		return type;
	}

}
