package de.tr7zw.vanillatrading.shop.gui;

import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GuiUtil {

	public static void selectItemGui(Player player, Consumer<ItemStack> consumer) {
		Inventory inventory = Bukkit.createInventory(new ItemSelectorHolder(consumer), 9, "Select an Item");
		inventory.setItem(4, new ItemStack(Material.PAPER));
		player.openInventory(inventory);
	}
	
	public static void openStorage(Player player, ItemStack[] items, ShopConfigGui gui, Consumer<Inventory> consumer) {
		Inventory inventory = Bukkit.createInventory(new ItemStorageHolder(consumer), 9*6, "Item Storage");
		inventory.setContents(items);
		player.openInventory(inventory);
	}
	
}
