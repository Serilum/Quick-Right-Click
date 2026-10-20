package com.serilum.quickrightclick.menu;

import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class QuickShulkerBoxContainer extends SimpleContainer {
	private final Player player;
	private final InteractionHand hand;
	private final ItemStack shulkerStack;

	public QuickShulkerBoxContainer(Player player, InteractionHand hand, ItemStack shulkerStack) {
		super(27);
		this.player = player;
		this.hand = hand;
		this.shulkerStack = shulkerStack;

		shulkerStack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY).copyInto(this.getItems());
	}

	@Override
	public void setChanged() {
		super.setChanged();

		if (isShulkerStillHeld()) {
			shulkerStack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(this.getItems()));
		}
	}

	@Override
	public boolean stillValid(@NotNull Player player) {
		return isShulkerStillHeld();
	}

	@Override
	public void startOpen(@NotNull Player player) {
		playShulkerSound(SoundEvents.SHULKER_BOX_OPEN);
	}

	@Override
	public void stopOpen(@NotNull Player player) {
		setChanged();
		playShulkerSound(SoundEvents.SHULKER_BOX_CLOSE);
	}

	private boolean isShulkerStillHeld() {
		return player.isAlive() && !shulkerStack.isEmpty() && player.getItemInHand(hand) == shulkerStack;
	}

	private void playShulkerSound(SoundEvent soundEvent) {
		Level level = player.level();
		level.playSound(null, player.getX(), player.getY(), player.getZ(), soundEvent, SoundSource.BLOCKS, 0.5F, level.getRandom().nextFloat() * 0.1F + 0.9F);
	}
}
