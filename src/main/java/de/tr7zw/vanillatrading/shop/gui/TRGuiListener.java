package de.tr7zw.vanillatrading.shop.gui;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;

import de.tr7zw.vanillatrading.shop.gui.TRGui.TRGuiHolder;

public class TRGuiListener implements Listener {

	private static final Set<InventoryAction> allowedActions = new HashSet<InventoryAction>(
			Arrays.asList(InventoryAction.PICKUP_ALL));
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if (event.getInventory().getHolder() != null && event.getInventory().getHolder() instanceof TRGuiHolder) {
			TRGuiHolder holder = (TRGuiHolder) event.getInventory().getHolder();
			if (!allowedActions.contains(event.getAction())
					|| event.getClickedInventory().getType() == InventoryType.PLAYER) {
				event.setCancelled(true);
				return;
			}
			if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
				event.setCancelled(true);
				holder.getGui().onClick(event.getSlot(), (Player)event.getWhoClicked());
				return;
			}
		}
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent event) {
		if (event.getInventory().getHolder() != null && event.getInventory().getHolder() instanceof TRGuiHolder) {
			TRGuiHolder holder = (TRGuiHolder) event.getInventory().getHolder();
			holder.getGui().onClose((Player)event.getPlayer());
		}
	}
	
}
