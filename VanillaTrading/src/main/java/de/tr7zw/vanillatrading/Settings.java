package de.tr7zw.vanillatrading;

import org.bukkit.configuration.file.FileConfiguration;

public class Settings {

	private int shopSlotAmount = 12;
	private int shopStorageRows = 6;
	
	protected void init(FileConfiguration config) {
		config.options().copyDefaults(true);
		config.addDefault("shopSlotAmount", 12);
		config.addDefault("shopStorageRows", 6);
		shopSlotAmount = Math.min(12, Math.max(1, config.getInt("shopSlotAmount")));
		shopStorageRows = Math.min(6, Math.max(1, config.getInt("shopStorageRows")));
	}

	public int getShopSlotAmount() {
		return shopSlotAmount;
	}

	public int getShopStorageRows() {
		return shopStorageRows;
	}

}
