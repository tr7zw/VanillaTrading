package de.tr7zw.vanillatrading.shop.gui;

import java.util.function.Consumer;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.vanillatrading.ShopUtil;

public class GuiUtil {

	public static void selectItemGui(Player player, Consumer<ItemStack> consumer) {
		Inventory inventory = Bukkit.createInventory(new ItemSelectorHolder(consumer), 9, "Select an Item");
		inventory.setItem(4, ShopUtil.getNamedCopy(new ItemStack(Material.PAPER), "§r§6Select an Item", "§rUse ESC to reset to no Item"));
		player.openInventory(inventory);
	}
	
	public static void openStorage(Player player, ItemStack[] items, ShopConfigGui gui, Consumer<Inventory> consumer) {
		Inventory inventory = Bukkit.createInventory(new ItemStorageHolder(consumer), 9*6, "Item Storage");
		inventory.setContents(items);
		player.openInventory(inventory);
	}
	
}
