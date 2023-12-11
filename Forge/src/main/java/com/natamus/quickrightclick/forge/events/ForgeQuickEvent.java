package com.natamus.quickrightclick.forge.events;

import com.natamus.quickrightclick.events.QuickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class ForgeQuickEvent {
	@SubscribeEvent
	public void onItemClick(PlayerInteractEvent.RightClickItem e) {
		QuickEvent.onItemClick(e.getPlayer(), e.getWorld(), e.getHand());
	}
}
