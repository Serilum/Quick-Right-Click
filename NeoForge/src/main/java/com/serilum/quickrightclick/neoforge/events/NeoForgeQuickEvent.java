package com.serilum.quickrightclick.neoforge.events;

import com.serilum.quickrightclick.events.QuickEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.bus.api.SubscribeEvent;

public class NeoForgeQuickEvent {
	@SubscribeEvent
	public static void onItemClick(PlayerInteractEvent.RightClickItem e) {
		QuickEvent.onItemClick(e.getEntity(), e.getLevel(), e.getHand());
	}
}
