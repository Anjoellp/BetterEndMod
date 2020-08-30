package org.ajdp.betterend.block;

import org.ajdp.betterend.tileentity.PurpleCampfireTileEntity;

import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class PurpleCampfireBlock extends CampfireBlock {

	public PurpleCampfireBlock(Properties propertiesIn) {
		super(true, 1, propertiesIn);
	}

	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		return new PurpleCampfireTileEntity();
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new PurpleCampfireTileEntity();
	}

}
