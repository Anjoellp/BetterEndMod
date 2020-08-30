package org.ajdp.betterend.structures;

import org.ajdp.betterend.BetterEndMod;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;

public class ModStructurePieceTypes {
	public static final IStructurePieceType DRUIDE_HUT = Registry.register(Registry.STRUCTURE_PIECE,
			BetterEndMod.location("druide_hut"), DruideHutPiece::new);
}