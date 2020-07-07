package de.tr7zw.vanillatrading.nms.v15_R1;

import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftInventoryMerchant;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import de.tr7zw.vanillatrading.nms.ShopMerchant;

public class MerchantGuiListener15R1 implements Listener {

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
