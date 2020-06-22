package de.tr7zw.vanillatrading.shop.gui;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class ItemSelectorHolder implements InventoryHolder, Listener {

	private static final Set<InventoryAction> allowedActions = new HashSet<InventoryAction>(
			Arrays.asList(InventoryAction.PICKUP_ALL));
	private Consumer<ItemStack> consumer;
	private boolean completed = false;

	public ItemSelectorHolder(Consumer<ItemStack> consumer) {
		this.consumer = consumer;
	}

	@Override
	public Inventory getInventory() {
		return null;// inventory;
	}

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if (event.getInventory().getHolder() != null && event.getInventory().getHolder() instanceof ItemSelectorHolder) {
			ItemSelectorHolder holder = (ItemSelectorHolder) event.getInventory().getHolder();
			if (!allowedActions.contains(event.getAction())
					|| event.getClickedInventory().getType() != InventoryType.PLAYER) {
				event.setCancelled(true);
				return;
			}
			if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
				event.setCancelled(true);
				holder.completed = true;
				holder.consumer.accept(event.getCurrentItem());
				return;
			}
		}
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent event) {
		if (event.getInventory().getHolder() != null && event.getInventory().getHolder() instanceof ItemSelectorHolder) {
			ItemSelectorHolder holder = (ItemSelectorHolder) event.getInventory().getHolder();
			if(!holder.completed) {
				holder.completed = true;
				holder.consumer.accept(null);
			}
		}
	}

}
