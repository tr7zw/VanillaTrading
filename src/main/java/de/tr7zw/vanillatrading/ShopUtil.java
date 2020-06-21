package de.tr7zw.vanillatrading;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

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
			System.out.println("Loaded new shop");
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

}
