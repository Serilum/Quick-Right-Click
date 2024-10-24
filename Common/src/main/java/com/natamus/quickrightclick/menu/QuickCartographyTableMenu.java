package com.natamus.quickrightclick.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CartographyTableMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import org.jetbrains.annotations.NotNull;

public class QuickCartographyTableMenu extends CartographyTableMenu {
	public QuickCartographyTableMenu(int id, Inventory inventory, ContainerLevelAccess containerLevelAccess) {
		super(id, inventory, containerLevelAccess);
	}

	@Override
	public boolean stillValid(@NotNull Player player) {
		return true;
	}
}