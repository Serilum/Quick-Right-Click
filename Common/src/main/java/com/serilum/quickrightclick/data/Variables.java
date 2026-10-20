package com.serilum.quickrightclick.data;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Variables {
	// Quick Bed
	public static CopyOnWriteArrayList<String> bedIsSleeping = new CopyOnWriteArrayList<String>();
	public static HashMap<String, InteractionHand> bedUsedHand = new HashMap<String, InteractionHand>();
	public static HashMap<String, BlockPos> bedFootPos = new HashMap<>();
}
