package de.tr7zw.vanillatrading;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.tr7zw.vanillatrading.shop.gui.ItemSelectorHolder;
import de.tr7zw.vanillatrading.shop.gui.ItemStorageHolder;
import de.tr7zw.vanillatrading.shop.gui.TRGuiListener;

public class VanillaTrading extends JavaPlugin {

	public static VanillaTrading INSTANCE;
	private Settings settings = new Settings();
	
	@Override
	public void onEnable() {
		INSTANCE = this;
		settings.init(this.getConfig());
		saveConfig();
		Bukkit.getPluginManager().registerEvents(new InteractListener(), this);
		Bukkit.getPluginManager().registerEvents(new ItemSelectorHolder(null), this);
		Bukkit.getPluginManager().registerEvents(new ItemStorageHolder(null), this);
		Bukkit.getPluginManager().registerEvents(new TRGuiListener(), this);
	}
	
	public Settings getSettings() {
		return settings;
	}
	
}
