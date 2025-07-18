package com.natamus.quickrightclick.features;

import com.natamus.quickrightclick.config.ConfigHandler;
import com.natamus.quickrightclick.data.Constants;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.PlayerEnderChestContainer;

public class EnderChestFeature {
    public static boolean init(Player player) {
        if (!ConfigHandler.enableQuickEnderChests) {
            return false;
        }

        PlayerEnderChestContainer enderChestContainer = player.getEnderChestInventory();
        if (enderChestContainer == null) {
            return false;
        }

        player.openMenu(new SimpleMenuProvider((id, inventory, p) -> ChestMenu.threeRows(id, inventory, enderChestContainer), Constants.ENDER_CHEST_TITLE));
        return true;
    }
}
