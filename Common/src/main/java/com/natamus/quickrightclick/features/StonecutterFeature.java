package com.natamus.quickrightclick.features;

import com.natamus.quickrightclick.config.ConfigHandler;
import com.natamus.quickrightclick.data.Constants;
import com.natamus.quickrightclick.menu.QuickStonecutterMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;

public class StonecutterFeature {
    public static boolean init(Level level, Player player, BlockPos playerPos) {
        if (!ConfigHandler.enableQuickStonecutters) {
            return false;
        }

        player.openMenu(new SimpleMenuProvider((id, inventory, p) -> new QuickStonecutterMenu(id, inventory, ContainerLevelAccess.create(level, playerPos)), Constants.STONECUTTER_TITLE));
        return true;
    }
}
