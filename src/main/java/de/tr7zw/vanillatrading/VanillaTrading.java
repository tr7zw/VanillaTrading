package de.tr7zw.vanillatrading;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.tr7zw.vanillatrading.shop.gui.GuiListener;

public class VanillaTrading extends JavaPlugin {

	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(new InteractListener(), this);
		Bukkit.getPluginManager().registerEvents(new GuiListener(), this);
		//this.getCommand("vantrade").setExecutor(new InfoCommand());
	}
	
}
