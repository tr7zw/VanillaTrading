package de.tr7zw.vanillatrading.nms;

import org.bukkit.inventory.Merchant;

import de.tr7zw.vanillatrading.shop.ShopHolder;

public interface ShopMerchant extends Merchant{

	public ShopHolder getShop();
	
}
