package com.natamus.quickrightclick.neoforge.events;

import com.natamus.quickrightclick.events.QuickEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber
public class NeoForgeQuickEvent {
	@SubscribeEvent
	public static void onItemClick(PlayerInteractEvent.RightClickItem e) {
		QuickEvent.onItemClick(e.getEntity(), e.getLevel(), e.getHand());
	}
}
