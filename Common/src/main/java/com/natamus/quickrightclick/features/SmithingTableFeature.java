package com.natamus.quickrightclick.features;

import com.natamus.quickrightclick.config.ConfigHandler;
import com.natamus.quickrightclick.data.Constants;
import com.natamus.quickrightclick.menu.QuickSmithingTableMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;

public class SmithingTableFeature {
    public static boolean init(Level level, Player player, BlockPos playerPos) {
        if (!ConfigHandler.enableQuickSmithingTables) {
            return false;
        }

        player.openMenu(new SimpleMenuProvider((id, inventory, p) -> new QuickSmithingTableMenu(id, inventory, ContainerLevelAccess.create(level, playerPos)), Constants.SMITHING_TABLE_TITLE));
        return true;
    }
}
