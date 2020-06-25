package de.tr7zw.vanillatrading.shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.vanillatrading.ShopUtil;
import de.tr7zw.vanillatrading.nms.NMSHandler;
import de.tr7zw.vanillatrading.shop.gui.ShopConfigGui;

public interface ShopHolder {

	public NBTCompound getPersistentDataContainer();

	public default World getWorld() {
		return getLocation().getWorld();
	}

	public Location getLocation();

	public Map<Integer, Integer> getStoredIds();

	public void setStoredIds(Map<Integer, Integer> mapping);
	
	public void setOpenBy(Player player);
	
	public boolean canBeUsedBy(Player player);

	public default NBTCompound getShopStorage() {
		if (!getPersistentDataContainer().hasKey("vanillatrading")) {
			NBTContainer container = new NBTContainer();
			container.addCompound("vanillatrading").setBoolean("shop", true);
			getPersistentDataContainer().mergeCompound(container);
		}
		return getPersistentDataContainer().getCompound("vanillatrading");
	}

	public default void setOwner(UUID uuid) {
		getShopStorage().setString("ownerUUID", uuid.toString());
	}

	public default UUID getOwner() {
		return UUID.fromString(getShopStorage().getString("ownerUUID"));
	}

	public default ItemStack getOutput(int id) {
		if (getShopStorage().hasKey("output_" + id)) {
			return getShopStorage().getItemStack("output_" + id);
		}
		return null;
	}

	public default ItemStack getInputOne(int id) {
		return getInput(1, id);
	}

	public default ItemStack getInputTwo(int id) {
		return getInput(2, id);
	}

	default ItemStack getInput(int input, int id) {
		if (getShopStorage().hasKey("input_" + input + "_" + id)) {
			return getShopStorage().getItemStack("input_" + input + "_" + id);
		}
		return null;
	}

	public default ItemStack[] getOutputStorage(int id) {
		return getStorage("output", id);
	}

	public default ItemStack[] getInputOneStorage(int id) {
		return getStorage("input_1", id);
	}

	public default ItemStack[] getInputTwoStorage(int id) {
		return getStorage("input_2", id);
	}

	public default ItemStack[] getStorage(String type, int id) {
		if (getShopStorage().hasKey(type + "_" + id + "_storage")) {
			List<String> itemStacks = getShopStorage().getStringList(type + "_" + id + "_storage");
			ItemStack[] items = new ItemStack[itemStacks.size()];
			int i = 0;
			for (String item : itemStacks) {
				items[i] = NBTItem.convertNBTtoItem(new NBTContainer(item));
				i++;
			}
			return items;
		}
		return new ItemStack[6*9];
	}

	public default void setOutputStorage(int id, ItemStack[] items) {
		setStorage("output", id, items);
	}

	public default void setInputOneStorage(int id, ItemStack[] items) {
		setStorage("input_1", id, items);
	}

	public default void setInputTwoStorage(int id, ItemStack[] items) {
		setStorage("input_2", id, items);
	}

	public default void setStorage(String type, int id, ItemStack[] items) {
		getShopStorage().removeKey(type + "_" + id + "_storage");
		List<String> itemStacks = getShopStorage().getStringList(type + "_" + id + "_storage");
		for (ItemStack item : items) {
			itemStacks.add(NBTItem.convertItemtoNBT(item).toString());
		}
	}

	public default void setOutput(int id, ItemStack item) {
		if (item == null) {
			getShopStorage().removeKey("output_" + id);
		} else {
			getShopStorage().setItemStack("output_" + id, item);
		}
	}

	public default void setInputOne(int id, ItemStack item) {
		if (item == null) {
			getShopStorage().removeKey("input_1_" + id);
		} else {
			getShopStorage().setItemStack("input_1_" + id, item);
		}
	}

	public default void setInputTwo(int id, ItemStack item) {
		if (item == null) {
			getShopStorage().removeKey("input_2_" + id);
		} else {
			getShopStorage().setItemStack("input_2_" + id, item);
		}
	}

	public Merchant getMerchant();

	public default void onInteract(Player player) {
		if(!canBeUsedBy(player)) {
			player.sendMessage("§cThis shop is in use!"); //TODO
			return;
		}
		setOpenBy(player);
		if (player.getUniqueId().equals(getOwner())) {
			new ShopConfigGui(this).openGui(player);
		} else {
			NMSHandler.getNMS().openMerchant(player, getMerchant());
		}

	}

	public default void onBreak() {
		ShopUtil.removeShop(getLocation());
		for (int i = 0; i < 12; i++) {
			ItemStack[] items = getInputOneStorage(i);
			for(ItemStack item : items) {
				if(item != null && item.getType() != Material.AIR) {
					getWorld().dropItem(getLocation(), item);
				}
			}
			items = getInputTwoStorage(i);
			for(ItemStack item : items) {
				if(item != null && item.getType() != Material.AIR) {
					getWorld().dropItem(getLocation(), item);
				}
			}
			items = getOutputStorage(i);
			for(ItemStack item : items) {
				if(item != null && item.getType() != Material.AIR) {
					getWorld().dropItem(getLocation(), item);
				}
			}
		}
	}

	public default void onShopClose(HumanEntity player) {
		Map<Integer, Integer> mapping = getStoredIds();
		int tradeid = 0;
		for (MerchantRecipe trade : getMerchant().getRecipes()) {
			if (trade.getUses() > 0) {
				Integer i = mapping.getOrDefault(tradeid, null);
				if (i == null) {
					System.out.println("Somehow an unmapped Recipe got used. Report this to tr7zw!");
					continue;
				}
				System.out.println(player.getName() + " bought " + trade.getUses() + "*" + trade.getResult().getType()
						+ " from " + getOwner());
				ItemStack output = getOutput(i);
				ItemStack inputOne = getInputOne(i);
				ItemStack inputTwo = getInputTwo(i);
				if (inputOne != null) {
					ItemStack[] stored = getInputOneStorage(i);
					stored = addItems(stored, inputOne, trade.getUses());
					setInputOneStorage(i, stored);
				}
				if (inputTwo != null) {
					ItemStack[] stored = getInputTwoStorage(i);
					stored = addItems(stored, inputTwo, trade.getUses());
					setInputTwoStorage(i, stored);
				}
				if (output != null) {
					ItemStack[] stored = getOutputStorage(i);
					stored = removeItems(stored, output, trade.getUses());
					setOutputStorage(i, stored);
				}
			}
			tradeid++;
		}
		rePopulateTrades(getMerchant());
		NMSHandler.getNMS().resetTrader(getMerchant());
		setOpenBy(null);
	}

	public default ItemStack[] addItems(ItemStack[] inventory, ItemStack item, int times) {
		// TODO can be done better
		Inventory inv = Bukkit.createInventory(null, 6*9);
		inv.setContents(inventory);
		for (int i = 0; i < times; i++)
			inv.addItem(item);
		return inv.getContents();
	}
	
	public default ItemStack[] removeItems(ItemStack[] inventory, ItemStack item, int times) {
		int amount = item.getAmount()*times;
		for(int i = 0; i < inventory.length; i++) {
			if(item.isSimilar(inventory[i])) {
				if(amount >= inventory[i].getAmount()) {
					amount -= inventory[i].getAmount();
					inventory[i] = new ItemStack(Material.AIR);
				}else {
					inventory[i].setAmount(inventory[i].getAmount() - amount);
				}
			}
			if(amount <= 0)break;
		}
		if(amount != 0) {
			System.out.println("Unable to remove the correct amount of items! " + amount);
		}
		return inventory;
	}

	public default void rePopulateTrades(Merchant merchant) {
		List<MerchantRecipe> trades = new ArrayList<MerchantRecipe>();
		Map<Integer, Integer> mapping = new HashMap<Integer, Integer>();
		for (int i = 0; i < 12; i++) {
			ItemStack output = getOutput(i);
			ItemStack inputOne = getInputOne(i);
			ItemStack inputTwo = getInputTwo(i);
			if (output == null || (inputOne == null && inputTwo == null))
				continue;
			int maxUses = Integer.MAX_VALUE;
			if (inputOne != null) {
				int freeSlots = getFreeSlots("input_1", i);
				int storage = freeSlots * inputOne.getMaxStackSize();
				int uses = storage / inputOne.getAmount();
				maxUses = Math.min(maxUses, uses);
			}
			if (inputTwo != null) {
				int freeSlots = getFreeSlots("input_2", i);
				int storage = freeSlots * inputTwo.getMaxStackSize();
				int uses = storage / inputTwo.getAmount();
				maxUses = Math.min(maxUses, uses);
			}
			if (output != null) { // more or less just for encapsulation
				int stored = getStoredAmount("output", i, output);
				int uses = stored / output.getAmount();
				maxUses = Math.min(maxUses, uses);
			}
			MerchantRecipe rec = new MerchantRecipe(output, 0, maxUses, false);
			if (inputOne != null)
				rec.addIngredient(inputOne);
			if (inputTwo != null)
				rec.addIngredient(inputTwo);
			mapping.put(trades.size(), i);
			trades.add(rec);
		}
		setStoredIds(mapping);
		merchant.setRecipes(trades);
	}

	public default int getStoredAmount(String type, int id, ItemStack targetItem) {
		ItemStack[] storage = getStorage(type, id);
		int amount = 0;
		for (ItemStack item : storage) {
			if (item != null && item.isSimilar(targetItem)) {
				amount += item.getAmount();
			}
		}
		return amount;
	}

	public default int getFreeSlots(String type, int id) {
		ItemStack[] storage = getStorage(type, id);
		int amount = 0;
		for (ItemStack item : storage) {
			if (item == null || item.getType() == Material.AIR) {
				amount++;
			}
		}
		return amount;
	}

}
