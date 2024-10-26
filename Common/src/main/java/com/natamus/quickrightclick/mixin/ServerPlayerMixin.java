package com.natamus.quickrightclick.mixin;

import com.natamus.quickrightclick.data.Variables;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ServerPlayer.class, priority = 1001)
public class ServerPlayerMixin {
    @Inject(method = "setRespawnPosition(Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/core/BlockPos;FZZ)V", at = @At(value = "HEAD"), cancellable = true)
       public void setRespawnPosition(ResourceKey<Level> level, BlockPos blockPos, float respawnAngle, boolean forcedRespawn, boolean showMessage, CallbackInfo ci) {
        Player player = (Player)(Object)this;
        String playerName = player.getName().getString();
        if (Variables.bedIsSleeping.contains(playerName)) {
            ci.cancel();
        }
    }
}