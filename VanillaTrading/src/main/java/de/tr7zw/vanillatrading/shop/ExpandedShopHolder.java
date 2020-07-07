package de.tr7zw.vanillatrading.shop;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import de.tr7zw.vanillatrading.ShopUtil;
import de.tr7zw.vanillatrading.nms.NMSHandler;
import de.tr7zw.vanillatrading.shop.gui.ShopConfigGui;

public interface ExpandedShopHolder extends ShopHolder {

	public default void onInteract(Player player) {
		if(!canBeUsedBy(player)) {
			player.sendMessage("§cThis shop is in use!"); //TODO
			return;
		}
		setOpenBy(player);
		boolean isOwner = player.getUniqueId().equals(getOwner());
		if ((isOwner && !player.isSneaking()) || (!isOwner && player.hasPermission("vanillatrading.admin") && player.isSneaking())) {
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
	
}
