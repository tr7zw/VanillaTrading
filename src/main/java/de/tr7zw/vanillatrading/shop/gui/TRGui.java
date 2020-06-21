package de.tr7zw.vanillatrading.shop.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public abstract class TRGui {

	private Inventory inventory;
	private Map<Integer, Consumer<Player>> functions = new HashMap<Integer, Consumer<Player>>();

	public TRGui(String name, int rows) {
		this.inventory = Bukkit.createInventory(new TRGuiHolder(), rows * 9, name);
	}

	public void clearGui() {
		functions.clear();
		this.inventory.clear();
	}

	public abstract void setupGui();
	
	public abstract void onClose(Player player);

	public void openGui(Player player) {
		clearGui();
		setupGui();
		player.openInventory(inventory);
	}

	public void addButton(int x, int y, ItemStack item, Consumer<Player> function) {
		addButton(x + y * 9, item, function);
	}

	public void addButton(int slot, ItemStack item, Consumer<Player> function) {
		functions.put(slot, function);
		inventory.setItem(slot, item);
	}
	
	public void onClick(int slot, Player player) {
		if(functions.containsKey(slot)) {
			functions.get(slot).accept(player);
		}
	}

	public class TRGuiHolder implements InventoryHolder {

		public TRGui getGui() {
			return TRGui.this;
		}

		@Override
		public Inventory getInventory() {
			return inventory;
		}

	}

}
