package org.ajdp.betterend.block;

import net.minecraft.stats.IStatFormatter;
import net.minecraft.stats.Stats;
import net.minecraft.util.ResourceLocation;

public class ModStats {
	public static final ResourceLocation INTERACT_WITH_ENDER_CRAFTING_TABLE = Stats
			.registerCustom("interact_with_ender_crafting_table", IStatFormatter.DEFAULT);
	public static final ResourceLocation INTERACT_WITH_AZULIUM_ANVIL = Stats
			.registerCustom("interact_with_azulium_anvil", IStatFormatter.DEFAULT);
}
