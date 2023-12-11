package com.natamus.quickrightclick.forge.mixin;

import com.natamus.collective.functions.ItemFunctions;
import com.natamus.quickrightclick.data.Constants;
import com.natamus.quickrightclick.data.Variables;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ShulkerBoxBlockEntity.class, priority = 1001)
public class ShulkerBoxBlockEntityMixin {
    @Inject(method = "stopOpen(Lnet/minecraft/world/entity/player/Player;)V", at = @At(value = "HEAD"))
    public void stopOpen(Player player, CallbackInfo ci) {
        String playerName = player.getName().getString();
        if (Variables.shulkerUsed.contains(playerName) && Variables.shulkerUsedHand.containsKey(playerName)) {
            ShulkerBoxBlockEntity shulkerBoxBlockEntity = (ShulkerBoxBlockEntity)(Object)this;
            Component shulkerEntityCustomName = shulkerBoxBlockEntity.getCustomName();
            String shulkerEntityCustomNameString = shulkerEntityCustomName.getString();
            if (!shulkerEntityCustomNameString.startsWith(Constants.INVISIBLE_CHAR)) {
                return;
            }

            Level level = shulkerBoxBlockEntity.getLevel();
            BlockPos shulkerPos = shulkerBoxBlockEntity.getBlockPos();
            BlockState blockState = shulkerBoxBlockEntity.getBlockState();
            Block block = blockState.getBlock();

            if (!(block instanceof ShulkerBoxBlock)) {
                return;
            }

            ShulkerBoxBlock shulkerBoxBlock = (ShulkerBoxBlock)block;
            ItemStack shulkerStack = shulkerBoxBlock.getCloneItemStack(level, shulkerPos, blockState);

            Style nameStyle = shulkerEntityCustomName.getStyle();
            shulkerStack.setHoverName(Component.literal(shulkerEntityCustomNameString.replace(Constants.INVISIBLE_CHAR, "")).withStyle(nameStyle));

            InteractionHand hand = Variables.shulkerUsedHand.get(playerName);
            if (player.getItemInHand(hand).isEmpty()) {
                player.setItemInHand(hand, shulkerStack);
            }
            else {
                ItemFunctions.giveOrDropItemStack(player, shulkerStack);
            }

            level.removeBlockEntity(shulkerPos);
            level.setBlock(shulkerPos, Blocks.AIR.defaultBlockState(), 3);

            Variables.shulkerUsed.remove(playerName);
            Variables.shulkerUsedHand.remove(playerName);
        }
    }
}