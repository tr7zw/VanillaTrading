package de.tr7zw.vanillatrading.shop.gui;

import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftInventoryMerchant;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import de.tr7zw.vanillatrading.nms.ShopMerchant;

public class GuiListener implements Listener {

	@EventHandler
	public void onClose(InventoryCloseEvent event) {
		if (event.getInventory() instanceof CraftInventoryMerchant) {
			CraftInventoryMerchant merchantInv = (CraftInventoryMerchant) event.getInventory();
			if (merchantInv.getMerchant() instanceof ShopMerchant) {
				ShopMerchant merchant = (ShopMerchant) merchantInv.getMerchant();
				merchant.getShop().onShopClose(event.getPlayer());
			}
		}
	}

}
