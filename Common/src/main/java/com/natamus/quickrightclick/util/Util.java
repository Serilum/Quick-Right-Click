package com.natamus.quickrightclick.util;

import com.natamus.collective.functions.ItemFunctions;
import com.natamus.quickrightclick.data.Variables;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
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

        boolean bedGiven = false;
        BlockPos playerPos = player.blockPosition();
        for (BlockPos aroundPos : BlockPos.betweenClosed(playerPos.getX()-1 , playerPos.getY()-1, playerPos.getZ()-1, playerPos.getX()+1, playerPos.getY()+1, playerPos.getZ()+1)) {
            BlockState aroundState = level.getBlockState(aroundPos);
            Block aroundBlock = aroundState.getBlock();
            if (aroundBlock instanceof BedBlock) {
                if (!bedGiven && !player.isCreative()) {
                    Item bedItem = aroundBlock.asItem();
                    ItemStack bedItemStack = new ItemStack(bedItem, 1);

                    InteractionHand hand = InteractionHand.MAIN_HAND;
                    if (Variables.bedUsedHand.containsKey(playerName)) {
                        hand = Variables.bedUsedHand.get(playerName);
                    }

                    if (player.getItemInHand(hand).isEmpty()) {
                        player.setItemInHand(hand, bedItemStack);
                    } else {
                        ItemFunctions.giveOrDropItemStack(player, bedItemStack);
                    }

                    bedGiven = true;
                }

                level.setBlock(aroundPos, Blocks.AIR.defaultBlockState(), 3);
            }
        }

        BlockPos southPos = playerPos.south().immutable();
        player.teleportTo(southPos.getX()+0.5, southPos.getY()+0.5, southPos.getZ()+0.5);

        Variables.bedIsSleeping.remove(playerName);
    }
}
