package com.serilum.quickrightclick.forge.events;

import com.serilum.quickrightclick.events.QuickEvent;
import net.minecraft.world.InteractionResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.Priority;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;

import java.lang.invoke.MethodHandles;

public class ForgeQuickEvent {
	public static void registerEventsInBus() {
		// BusGroup.DEFAULT.register(MethodHandles.lookup(), ForgeQuickEvent.class);

		PlayerInteractEvent.RightClickItem.BUS.addListener(Priority.HIGH, ForgeQuickEvent::onItemClick);
	}

	@SubscribeEvent(priority = Priority.HIGH)
	public static boolean onItemClick(PlayerInteractEvent.RightClickItem e) {
		if (QuickEvent.onItemClick(e.getEntity(), e.getLevel(), e.getHand()).equals(InteractionResult.SUCCESS)) {
			e.setCancellationResult(InteractionResult.SUCCESS);
			return true;
		}
		return false;
	}
}
