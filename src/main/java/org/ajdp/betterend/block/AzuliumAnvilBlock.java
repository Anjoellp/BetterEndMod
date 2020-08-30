package org.ajdp.betterend.block;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import org.ajdp.betterend.container.AzuliumRepairContainer;

import com.google.common.collect.Lists;

import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class AzuliumAnvilBlock extends AnvilBlock {

	public AzuliumAnvilBlock(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player,
			Hand handIn, BlockRayTraceResult hit) {
		if (worldIn.isRemote) {
			return ActionResultType.SUCCESS;
		} else {
			player.openContainer(getContainer(state, worldIn, pos));
			player.addStat(ModStats.INTERACT_WITH_AZULIUM_ANVIL);
			return ActionResultType.SUCCESS;
		}
	}

	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
		if (worldIn.isAirBlock(pos.down()) || canFallThrough(worldIn.getBlockState(pos.down())) && pos.getY() >= 0) {
			FallingBlockEntity fallingblockentity = new FallingBlockEntity(worldIn, (double) pos.getX() + 0.5D,
					(double) pos.getY(), (double) pos.getZ() + 0.5D, worldIn.getBlockState(pos)) {
				@Override
				public boolean onLivingFall(float distance, float damageMultiplier) {
					int i = MathHelper.ceil(distance - 1.0F);
					if (i > 0) {
						List<Entity> list = Lists.newArrayList(
								this.world.getEntitiesWithinAABBExcludingEntity(this, this.getBoundingBox()));
						DamageSource damagesource = DamageSource.ANVIL;
						for (Entity entity : list) {
							entity.attackEntityFrom(damagesource, (float) Math
									.min(MathHelper.floor((float) i * (Float) fallHurtAmount), fallHurtMax));
						}
					}
					return false;
				}
			};
			this.onStartFalling(fallingblockentity);
			worldIn.addEntity(fallingblockentity);
		}
	}

	@Override
	@Nullable
	public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
		return new SimpleNamedContainerProvider((id, inventory, player) -> {
			return new AzuliumRepairContainer(id, inventory, IWorldPosCallable.of(worldIn, pos));
		}, new TranslationTextComponent("container.azulium_repair"));
	}
}
