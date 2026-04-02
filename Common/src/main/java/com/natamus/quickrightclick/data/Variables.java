package com.natamus.quickrightclick.data;

import net.minecraft.world.InteractionHand;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Variables {
    // Quick Bed
    public static CopyOnWriteArrayList<String> bedIsSleeping = new CopyOnWriteArrayList<String>();
    public static HashMap<String, InteractionHand> bedUsedHand = new HashMap<String, InteractionHand>();

    // Quick Shulker
    public static CopyOnWriteArrayList<String> shulkerUsed = new CopyOnWriteArrayList<String>();
    public static HashMap<String, InteractionHand> shulkerUsedHand = new HashMap<String, InteractionHand>();
}
