package com.natamus.quickrightclick.forge.events;

import com.natamus.quickrightclick.events.QuickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeQuickEvent {
	@SubscribeEvent
	public static void onItemClick(PlayerInteractEvent.RightClickItem e) {
		QuickEvent.onItemClick(e.getEntity(), e.getLevel(), e.getHand());
	}
}
