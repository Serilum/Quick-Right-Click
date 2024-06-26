package com.natamus.quickrightclick.features;

import com.natamus.collective.functions.MessageFunctions;
import com.natamus.quickrightclick.config.ConfigHandler;
import com.natamus.quickrightclick.data.Constants;
import com.natamus.quickrightclick.data.Variables;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Iterator;

public class ShulkerBoxFeature {
    public static boolean init(Level level, Player player, BlockPos playerPos, ItemStack handStack, InteractionHand hand, Block block) {
        if (!ConfigHandler.enableQuickShulkerBoxes) {
            return false;
        }

        ShulkerBoxBlock shulkerBoxBlock = (ShulkerBoxBlock)block;

        Direction playerDirection = player.getDirection();
        BlockPos shulkerPos = playerPos.above(2).relative(playerDirection, 0).immutable();
        if (!level.getBlockState(shulkerPos).getBlock().equals(Blocks.AIR)) {
            if (level.isClientSide) {
                MessageFunctions.sendMessage(player, "Unable to open shulker box, location obstructed.", ChatFormatting.DARK_GRAY);
            }
            return false;
        }

        BlockState blockState = block.defaultBlockState().setValue(ShulkerBoxBlock.FACING, playerDirection);

        ShulkerBoxBlockEntity shulkerBoxBlockEntity = new ShulkerBoxBlockEntity(shulkerBoxBlock.getColor(), shulkerPos, blockState);
        shulkerBoxBlockEntity.setLevel(level);

        level.setBlock(shulkerPos, blockState, 3);
        level.setBlockEntity(shulkerBoxBlockEntity);

        ItemContainerContents itemContainerContents = handStack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);

        int i = 0;
        Iterator<ItemStack> itemContainerIterator = itemContainerContents.stream().iterator();
        while (itemContainerIterator.hasNext()) {
            ItemStack nextStack = itemContainerIterator.next();
            shulkerBoxBlockEntity.setItem(i, nextStack);
            i+=1;
        }

        DataComponentMap.Builder dataComponentMapBuilder = DataComponentMap.builder();
        dataComponentMapBuilder.set(DataComponents.CUSTOM_NAME, Component.literal(Constants.INVISIBLE_CHAR).append(handStack.getHoverName()));

        shulkerBoxBlockEntity.setComponents(dataComponentMapBuilder.build());

        handStack.shrink(1);

        String playerName = player.getName().getString();
        Variables.shulkerUsed.add(playerName);
        Variables.shulkerUsedHand.put(playerName, hand);

        player.openMenu(shulkerBoxBlockEntity);
        return true;
    }
}
