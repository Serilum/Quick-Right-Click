package com.serilum.quickrightclick.features;

import com.natamus.collective.functions.MessageFunctions;
import com.serilum.quickrightclick.config.ConfigHandler;
import com.serilum.quickrightclick.data.Variables;
import com.serilum.quickrightclick.util.Util;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BedPart;

import java.util.concurrent.atomic.AtomicBoolean;

public class BedBlockFeature {
	public static boolean init(Level level, Player player, BlockPos playerPos, ItemStack handStack, InteractionHand hand, Block block) {
		if (!ConfigHandler.enableQuickBeds) {
			return false;
		}

		if (!BedBlock.canSetSpawn(level)) {
			return false;
		}

		BlockPos footPos = getFreeBedFootPos(level, playerPos);
		if (footPos == null) {
			if (level.isClientSide) {
				MessageFunctions.sendTranslatableMessage(player, "collective.quickrightclick.message.unablesleeplocation", ChatFormatting.DARK_GRAY);
			}
			return false;
		}

		if (level.isClientSide) {
			return true;
		}

		BlockPos headPos = footPos.north();
		level.setBlock(headPos, block.defaultBlockState().setValue(BedBlock.PART, BedPart.HEAD), 3);
		level.setBlock(footPos, block.defaultBlockState().setValue(BedBlock.PART, BedPart.FOOT), 3);

		String playerName = player.getName().getString();
		Variables.bedIsSleeping.add(playerName);

		AtomicBoolean canSleep = new AtomicBoolean(true);
		player.startSleepInBed(headPos).ifLeft((bedSleepingProblem) -> {
			if (bedSleepingProblem.getMessage() != null) {
				player.displayClientMessage(bedSleepingProblem.getMessage(), true);
			}
			canSleep.set(false);
		});

		if (!canSleep.get()) {
			Util.removeQuickBed(level, footPos);
			Variables.bedIsSleeping.remove(playerName);
			return false;
		}

		if (!player.isCreative()) {
			handStack.shrink(1);
		}

		Variables.bedUsedHand.put(playerName, hand);
		Variables.bedFootPos.put(playerName, footPos);
		return true;
	}

	private static BlockPos getFreeBedFootPos(Level level, BlockPos playerPos) {
		if (isFreeBedSpot(level, playerPos)) {
			return playerPos.immutable();
		}

		BlockPos abovePos = playerPos.above().immutable();
		if (isFreeBedSpot(level, abovePos)) {
			return abovePos;
		}
		return null;
	}

	private static boolean isFreeBedSpot(Level level, BlockPos footPos) {
		return level.getBlockState(footPos).isAir() && level.getBlockState(footPos.north()).isAir();
	}
}
