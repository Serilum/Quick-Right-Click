package com.natamus.quickrightclick.mixin;

import com.natamus.quickrightclick.data.Variables;
import com.natamus.quickrightclick.util.Util;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LivingEntity.class, priority = 1001)
public class LivingEntityMixin {
    @Inject(method = "stopSleeping()V", at = @At(value = "HEAD"))
    public void stopSleeping(CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity)(Object)this;
        if (livingEntity instanceof Player) {
            Player player = (Player)livingEntity;
            String playerName = player.getName().getString();
            if (!Variables.bedIsSleeping.contains(playerName)) {
                return;
            }

            Util.stopSleeping(player, playerName);
        }
    }
}