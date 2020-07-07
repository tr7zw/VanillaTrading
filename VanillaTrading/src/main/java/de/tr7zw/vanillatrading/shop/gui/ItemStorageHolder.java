package de.tr7zw.vanillatrading.shop.gui;

import java.util.function.Consumer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class ItemStorageHolder implements InventoryHolder, Listener {

	private Consumer<Inventory> callable;
	private boolean completed = false;

	public ItemStorageHolder(Consumer<Inventory> callable) {
		this.callable = callable;
	}

	@Override
	public Inventory getInventory() {
		return null;// inventory;
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent event) {
		if (event.getInventory().getHolder() != null && event.getInventory().getHolder() instanceof ItemStorageHolder) {
			ItemStorageHolder holder = (ItemStorageHolder) event.getInventory().getHolder();
			if(!holder.completed) {
				holder.completed = true;
				holder.callable.accept(event.getInventory());
			}
		}
	}

}
