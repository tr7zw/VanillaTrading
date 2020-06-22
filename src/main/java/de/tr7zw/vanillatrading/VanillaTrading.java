package de.tr7zw.vanillatrading;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import de.tr7zw.vanillatrading.shop.gui.MerchantGuiListener;
import de.tr7zw.vanillatrading.shop.gui.TRGuiListener;
import de.tr7zw.vanillatrading.shop.gui.ItemSelectorHolder;
import de.tr7zw.vanillatrading.shop.gui.ItemStorageHolder;

public class VanillaTrading extends JavaPlugin {

	public static VanillaTrading INSTANCE;
	
	@Override
	public void onEnable() {
		INSTANCE = this;
		Bukkit.getPluginManager().registerEvents(new InteractListener(), this);
		Bukkit.getPluginManager().registerEvents(new MerchantGuiListener(), this);
		Bukkit.getPluginManager().registerEvents(new ItemSelectorHolder(null), this);
		Bukkit.getPluginManager().registerEvents(new ItemStorageHolder(null), this);
		Bukkit.getPluginManager().registerEvents(new TRGuiListener(), this);
	}
	
}
