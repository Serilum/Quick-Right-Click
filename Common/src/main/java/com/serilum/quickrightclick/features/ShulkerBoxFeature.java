package com.serilum.quickrightclick.features;

import com.serilum.quickrightclick.config.ConfigHandler;
import com.serilum.quickrightclick.data.Constants;
import com.serilum.quickrightclick.menu.QuickShulkerBoxContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ShulkerBoxMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;

public class ShulkerBoxFeature {
	public static boolean init(Level level, Player player, ItemStack handStack, InteractionHand hand, Block block) {
		if (!ConfigHandler.enableQuickShulkerBoxes) {
			return false;
		}

		if (!block.getClass().equals(ShulkerBoxBlock.class) || handStack.getCount() != 1) {
			return false;
		}

		if (level.isClientSide) {
			return true;
		}

		QuickShulkerBoxContainer shulkerBoxContainer = new QuickShulkerBoxContainer(player, hand, handStack);
		player.openMenu(new SimpleMenuProvider((id, inventory, p) -> new ShulkerBoxMenu(id, inventory, shulkerBoxContainer), getShulkerBoxTitle(handStack)));
		return true;
	}

	private static Component getShulkerBoxTitle(ItemStack shulkerStack) {
		if (shulkerStack.hasCustomHoverName()) {
			return shulkerStack.getHoverName();
		}
		return Constants.SHULKER_BOX_TITLE;
	}
}
