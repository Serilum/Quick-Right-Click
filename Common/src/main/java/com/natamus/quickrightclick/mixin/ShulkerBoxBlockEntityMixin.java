package com.natamus.quickrightclick.mixin;

import com.natamus.collective.functions.ItemFunctions;
import com.natamus.quickrightclick.data.Constants;
import com.natamus.quickrightclick.data.Variables;
import com.natamus.quickrightclick.features.ShulkerBoxFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ContainerUser;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ShulkerBoxBlockEntity.class, priority = 1001)
public class ShulkerBoxBlockEntityMixin {
    @Shadow private NonNullList<ItemStack> itemStacks;

    @Inject(method = "stopOpen(Lnet/minecraft/world/entity/ContainerUser;)V", at = @At(value = "HEAD"))
    public void stopOpen(ContainerUser containerUser, CallbackInfo ci) {
        LivingEntity livingEntity = containerUser.getLivingEntity();
        if (!(livingEntity instanceof Player player)) {
            return;
        }

        String playerName = player.getName().getString();
        if (Variables.shulkerUsed.contains(playerName) && Variables.shulkerUsedHand.containsKey(playerName)) {
            ShulkerBoxBlockEntity shulkerBoxBlockEntity = (ShulkerBoxBlockEntity)(Object)this;
            Component shulkerEntityCustomName = shulkerBoxBlockEntity.components().get(DataComponents.CUSTOM_NAME);
            if (shulkerEntityCustomName == null) {
                return;
            }

            String shulkerEntityCustomNameString = shulkerEntityCustomName.getString();
            if (!shulkerEntityCustomNameString.startsWith(Constants.INVISIBLE_CHAR)) {
                return;
            }

            Level level = shulkerBoxBlockEntity.getLevel();
            BlockPos shulkerPos = shulkerBoxBlockEntity.getBlockPos();
            BlockState blockState = shulkerBoxBlockEntity.getBlockState();
            Block block = blockState.getBlock();

            if (!(block instanceof ShulkerBoxBlock shulkerBoxBlock)) {
                return;
            }

            ItemStack shulkerStack = ShulkerBoxFeature.getCloneItemStack(level, shulkerPos, blockState, shulkerBoxBlock);

            Style nameStyle = shulkerEntityCustomName.getStyle();
            shulkerStack.set(DataComponents.CUSTOM_NAME, Component.literal(shulkerEntityCustomNameString.replace(Constants.INVISIBLE_CHAR, "")).withStyle(nameStyle));

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