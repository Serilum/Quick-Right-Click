package com.natamus.quickrightclick.config;

import com.natamus.collective.config.DuskConfig;
import com.natamus.quickrightclick.util.Reference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConfigHandler extends DuskConfig {
	public static HashMap<String, List<String>> configMetaData = new HashMap<String, List<String>>();

	@Entry public static boolean enableQuickBeds = true;
	@Entry public static boolean enableQuickCartographyTables = true;
	@Entry public static boolean enableQuickCraftingTables = true;
	@Entry public static boolean enableQuickEnderChests = true;
	@Entry public static boolean enableQuickGrindstones = true;
	@Entry public static boolean enableQuickShulkerBoxes = true;
	@Entry public static boolean enableQuickSmithingTables = true;
	@Entry public static boolean enableQuickStonecutters = true;

	public static void initConfig() {
		configMetaData.put("enableQuickBeds", Arrays.asList(
			"If right-clicking beds in the air should enable the quick feature."
		));
		configMetaData.put("enableQuickCartographyTables", Arrays.asList(
			"If right-clicking cartography tables in the air should enable the quick feature."
		));
		configMetaData.put("enableQuickCraftingTables", Arrays.asList(
			"If right-clicking crafting tables in the air should enable the quick feature."
		));
		configMetaData.put("enableQuickEnderChests", Arrays.asList(
			"If right-clicking ender chests in the air should enable the quick feature."
		));
		configMetaData.put("enableQuickGrindstones", Arrays.asList(
			"If right-clicking grindstones in the air should enable the quick feature."
		));
		configMetaData.put("enableQuickShulkerBoxes", Arrays.asList(
			"If right-clicking shulker boxes in the air should enable the quick feature."
		));
		configMetaData.put("enableQuickSmithingTables", Arrays.asList(
			"If right-clicking smithing tables in the air should enable the quick feature."
		));
		configMetaData.put("enableQuickStonecutters", Arrays.asList(
			"If right-clicking stonecutters in the air should enable the quick feature."
		));

		DuskConfig.init(Reference.NAME, Reference.MOD_ID, ConfigHandler.class);
	}
}