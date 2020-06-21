package de.tr7zw.vanillatrading.shop.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.vanillatrading.shop.ShopHolder;

public class ShopConfigGui extends TRGui {

	private ShopHolder shopHolder;
	
	public ShopConfigGui(ShopHolder shopHolder) {
		super("Shop config", 6);
		this.shopHolder = shopHolder;
	}

	@Override
	public void setupGui() {
		addButton(0, 0, new ItemStack(Material.PAPER), player -> {
			player.sendMessage("A");
		});
		addButton(1, 1, new ItemStack(Material.STICK), player -> {
			player.sendMessage("B");
		});
	}

	@Override
	public void onClose(Player player) {
		
	}

}
