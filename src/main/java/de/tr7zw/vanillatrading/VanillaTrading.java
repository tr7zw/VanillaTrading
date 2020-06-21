package de.tr7zw.vanillatrading;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.tr7zw.vanillatrading.shop.gui.GuiListener;
import de.tr7zw.vanillatrading.shop.gui.ItemSelectorHolder;

public class VanillaTrading extends JavaPlugin {

	public static VanillaTrading INSTANCE;
	
	@Override
	public void onEnable() {
		INSTANCE = this;
		Bukkit.getPluginManager().registerEvents(new InteractListener(), this);
		Bukkit.getPluginManager().registerEvents(new GuiListener(), this);
		Bukkit.getPluginManager().registerEvents(new ItemSelectorHolder(null), this);
	}
	
}
