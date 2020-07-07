package de.tr7zw.vanillatrading;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.nbtapi.NBTEntity;
import de.tr7zw.nbtapi.NBTTileEntity;
import de.tr7zw.vanillatrading.shop.BlockHolder;
import de.tr7zw.vanillatrading.shop.EntityHolder;
import de.tr7zw.vanillatrading.shop.ShopHolder;

public class ShopUtil {

	private static Map<Location, ShopHolder> loadedShops = new HashMap<Location, ShopHolder>();

	public static ShopHolder getShop(Block block) {
		if (loadedShops.containsKey(block.getLocation()))
			return loadedShops.get(block.getLocation());
		NBTTileEntity nbtTile = new NBTTileEntity(block.getState());
		if (nbtTile.getPersistentDataContainer().hasKey("vanillatrading")) {
			BlockHolder holder = new BlockHolder(block, nbtTile);
			loadedShops.put(block.getLocation(), holder);
			return holder;
		}
		return null;
	}

	public static ShopHolder getShop(Entity entity) {
		NBTEntity nbtEnt = new NBTEntity(entity);
		if (nbtEnt.getPersistentDataContainer().hasKey("vanillatrading")) {
			return new EntityHolder(entity, nbtEnt);
		}
		return null;
	}

	public static void removeShop(Location location) {
		loadedShops.remove(location);
	}

	public static ItemStack getNamedCopy(ItemStack item, String name) {
		return getNamedCopy(item, name);
	}

	public static ItemStack getNamedCopy(ItemStack item, String name, String... lore) {
		ItemStack nextItem = item.clone();
		ItemMeta meta = nextItem.getItemMeta();
		meta.setDisplayName(name);
		if (lore != null && lore.length > 0) {
			meta.setLore(Arrays.asList(lore));
		}
		nextItem.setItemMeta(meta);
		return nextItem;
	}

}
