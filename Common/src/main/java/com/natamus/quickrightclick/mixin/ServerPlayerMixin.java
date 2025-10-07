package com.natamus.quickrightclick.mixin;

import com.natamus.quickrightclick.data.Variables;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ServerPlayer.class, priority = 1001)
public class ServerPlayerMixin {
    @Inject(method = "setRespawnPosition", at = @At(value = "HEAD"), cancellable = true)
       public void setRespawnPosition(ServerPlayer.RespawnConfig respawnConfig, boolean b, CallbackInfo ci) {
        Player player = (Player)(Object)this;
        String playerName = player.getName().getString();
        if (Variables.bedIsSleeping.contains(playerName)) {
            ci.cancel();
        }
    }
}