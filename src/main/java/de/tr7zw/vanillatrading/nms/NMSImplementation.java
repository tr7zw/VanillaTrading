package de.tr7zw.vanillatrading.nms;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Merchant;

import de.tr7zw.vanillatrading.shop.ShopHolder;

public interface NMSImplementation {

	public Merchant createMerchant(ShopHolder shop);
	
	public void openMerchant(Player player, Merchant merchant);
	
}
