package com.serilum.quickrightclick.forge.events;

import com.serilum.quickrightclick.events.QuickEvent;
import net.minecraft.world.InteractionResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeQuickEvent {
	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onItemClick(PlayerInteractEvent.RightClickItem e) {
		if (QuickEvent.onItemClick(e.getEntity(), e.getLevel(), e.getHand()).getResult().equals(InteractionResult.SUCCESS)) {
			e.setCancellationResult(InteractionResult.SUCCESS);
			e.setCanceled(true);
		}
	}
}
