package com.natamus.quickrightclick.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.GrindstoneMenu;
import org.jetbrains.annotations.NotNull;

public class QuickGrindstoneMenu extends GrindstoneMenu {
	public QuickGrindstoneMenu(int id, Inventory inventory, ContainerLevelAccess containerLevelAccess) {
		super(id, inventory, containerLevelAccess);
	}

	@Override
	public boolean stillValid(@NotNull Player player) {
		return true;
	}
}