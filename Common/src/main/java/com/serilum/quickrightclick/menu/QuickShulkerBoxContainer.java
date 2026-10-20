package com.serilum.quickrightclick.menu;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
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

		CompoundTag blockEntityTag = BlockItem.getBlockEntityData(shulkerStack);
		if (blockEntityTag != null && blockEntityTag.contains("Items", Tag.TAG_LIST)) {
			NonNullList<ItemStack> storedItems = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
			ContainerHelper.loadAllItems(blockEntityTag, storedItems);

			for (int slot = 0; slot < storedItems.size(); slot++) {
				if (!storedItems.get(slot).isEmpty()) {
					this.setItem(slot, storedItems.get(slot));
				}
			}
		}
	}

	@Override
	public void setChanged() {
		super.setChanged();

		if (isShulkerStillHeld()) {
			CompoundTag blockEntityTag = getBlockEntityTagWithoutItems();
			ContainerHelper.saveAllItems(blockEntityTag, getItemList(), false);
			BlockItem.setBlockEntityData(shulkerStack, BlockEntityType.SHULKER_BOX, blockEntityTag);
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

	private NonNullList<ItemStack> getItemList() {
		NonNullList<ItemStack> itemList = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		for (int slot = 0; slot < itemList.size(); slot++) {
			itemList.set(slot, this.getItem(slot));
		}
		return itemList;
	}

	private CompoundTag getBlockEntityTagWithoutItems() {
		CompoundTag existingTag = BlockItem.getBlockEntityData(shulkerStack);
		if (existingTag == null) {
			return new CompoundTag();
		}

		CompoundTag blockEntityTag = existingTag.copy();
		blockEntityTag.remove("Items");
		blockEntityTag.remove("id");
		return blockEntityTag;
	}

	private void playShulkerSound(SoundEvent soundEvent) {
		Level level = player.level();
		level.playSound(null, player.getX(), player.getY(), player.getZ(), soundEvent, SoundSource.BLOCKS, 0.5F, level.getRandom().nextFloat() * 0.1F + 0.9F);
	}
}
