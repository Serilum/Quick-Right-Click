package com.serilum.quickrightclick.forge.events;

import com.serilum.quickrightclick.events.QuickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeQuickEvent {
	@SubscribeEvent
	public static void onItemClick(PlayerInteractEvent.RightClickItem e) {
		QuickEvent.onItemClick(e.getEntity(), e.getLevel(), e.getHand());
	}
}
