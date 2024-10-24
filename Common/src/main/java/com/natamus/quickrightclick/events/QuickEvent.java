package com.natamus.quickrightclick.events;

import com.natamus.quickrightclick.features.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;

public class QuickEvent {
	public static InteractionResult onItemClick(Player player, Level level, InteractionHand hand) {
		ItemStack handStack = player.getItemInHand(hand);
		Item item = handStack.getItem();

		if (!(item instanceof BlockItem)) {
			return InteractionResult.PASS;
		}

		BlockPos playerPos = player.blockPosition();

		BlockItem blockItem = (BlockItem)item;
		Block block = blockItem.getBlock();

		boolean result = false;
		if (block instanceof BedBlock) {
			result = BedBlockFeature.init(level, player, playerPos, handStack, hand, block);
		}
		else if (block instanceof CartographyTableBlock) {
			result = CartographyTableFeature.init(level, player, playerPos);
		}
		else if (block instanceof CraftingTableBlock) {
			if (block instanceof SmithingTableBlock) {
				result = SmithingTableFeature.init(level, player, playerPos);
			}
			else {
				result = CraftingTableFeature.init(level, player, playerPos);
			}
		}
		else if (block instanceof EnderChestBlock) {
			result = EnderChestFeature.init(player);
		}
		else if (block instanceof GrindstoneBlock) {
			result = GrindstoneFeature.init(level, player, playerPos);
		}
		else if (block instanceof ShulkerBoxBlock) {
			result = ShulkerBoxFeature.init(level, player, playerPos, handStack, hand, block);
		}
		else if (block instanceof StonecutterBlock) {
			result = StonecutterFeature.init(level, player, playerPos);
		}

		if (result) {
			player.swing(hand, true);
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}