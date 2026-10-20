package com.serilum.quickrightclick.neoforge.events;

import com.serilum.quickrightclick.events.QuickEvent;
import net.minecraft.world.InteractionResult;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.bus.api.SubscribeEvent;

public class NeoForgeQuickEvent {
	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void onItemClick(PlayerInteractEvent.RightClickItem e) {
		if (QuickEvent.onItemClick(e.getEntity(), e.getLevel(), e.getHand()).equals(InteractionResult.SUCCESS)) {
			e.setCancellationResult(InteractionResult.SUCCESS);
			e.setCanceled(true);
		}
	}
}
