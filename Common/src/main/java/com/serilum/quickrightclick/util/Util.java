package com.serilum.quickrightclick.util;

import com.natamus.collective.functions.ItemFunctions;
import com.serilum.quickrightclick.data.Variables;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractBedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class Util {
	public static void stopSleeping(Player player, String playerName) {
		if (player.isSleeping()) {
			Vec3 vec = player.position();
			player.setPose(Pose.STANDING);
			player.setPos(vec.x, vec.y, vec.z);
			player.clearSleepingPos();
		}

		Level level = player.level();
		BlockPos playerPos = player.blockPosition();

		BlockPos footPos = Variables.bedFootPos.remove(playerName);
		if (footPos != null) {
			AbstractBedBlock bedBlock = getQuickBedBlock(level, footPos);
			if (bedBlock != null) {
				boolean bedIsUsedUp = bedBlock.getBedRule(level, footPos.north()).destroyOnLeave();
				if (!player.isCreative() && !bedIsUsedUp) {
					returnBedItem(player, playerName, new ItemStack(bedBlock.asItem(), 1));
				}

				removeQuickBed(level, footPos);
			}
		}

		BlockPos southPos = playerPos.south().immutable();
		player.teleportTo(southPos.getX()+0.5, southPos.getY()+0.5, southPos.getZ()+0.5);

		Variables.bedIsSleeping.remove(playerName);
	}

	public static void removeQuickBed(Level level, BlockPos footPos) {
		BlockPos headPos = footPos.north();
		if (level.getBlockState(headPos).getBlock() instanceof AbstractBedBlock) {
			level.setBlock(headPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL | Block.UPDATE_KNOWN_SHAPE);
		}
		if (level.getBlockState(footPos).getBlock() instanceof AbstractBedBlock) {
			level.setBlock(footPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL);
		}
	}

	private static AbstractBedBlock getQuickBedBlock(Level level, BlockPos footPos) {
		if (level.getBlockState(footPos.north()).getBlock() instanceof AbstractBedBlock headBlock) {
			return headBlock;
		}

		if (level.getBlockState(footPos).getBlock() instanceof AbstractBedBlock footBlock) {
			return footBlock;
		}
		return null;
	}

	private static void returnBedItem(Player player, String playerName, ItemStack bedItemStack) {
		InteractionHand hand = InteractionHand.MAIN_HAND;
		if (Variables.bedUsedHand.containsKey(playerName)) {
			hand = Variables.bedUsedHand.get(playerName);
		}

		if (player.getItemInHand(hand).isEmpty()) {
			player.setItemInHand(hand, bedItemStack);
		}
		else {
			ItemFunctions.giveOrDropItemStack(player, bedItemStack);
		}
	}
}
