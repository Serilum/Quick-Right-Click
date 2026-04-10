package com.natamus.quickrightclick.forge.events;

import com.natamus.quickrightclick.events.QuickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;

import java.lang.invoke.MethodHandles;

public class ForgeQuickEvent {
	public static void registerEventsInBus() {
		// BusGroup.DEFAULT.register(MethodHandles.lookup(), ForgeQuickEvent.class);

		PlayerInteractEvent.RightClickItem.BUS.addListener(ForgeQuickEvent::onItemClick);
	}

	@SubscribeEvent
	public static void onItemClick(PlayerInteractEvent.RightClickItem e) {
		QuickEvent.onItemClick(e.getEntity(), e.getLevel(), e.getHand());
	}
}
