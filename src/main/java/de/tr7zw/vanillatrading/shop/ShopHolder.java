package de.tr7zw.vanillatrading.shop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.vanillatrading.ShopUtil;
import de.tr7zw.vanillatrading.nms.NMSHandler;
import de.tr7zw.vanillatrading.shop.gui.GuiUtil;
import de.tr7zw.vanillatrading.shop.gui.ShopConfigGui;

public interface ShopHolder {

	public NBTCompound getPersistentDataContainer();

	public default World getWorld() {
		return getLocation().getWorld();
	}

	public Location getLocation();

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
		if (getShopStorage().hasKey("input_1_" + id)) {
			return getShopStorage().getItemStack("input_1_" + id);
		}
		return null;
	}

	public default ItemStack getInputTwo(int id) {
		if (getShopStorage().hasKey("input_2_" + id)) {
			return getShopStorage().getItemStack("input_2_" + id);
		}
		return null;
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
		if (player.getUniqueId().equals(getOwner())) {
			new ShopConfigGui(this).openGui(player);
			/*
			 * GuiUtil.selectItemGui(player, item -> { player.sendMessage("Item: " + item);
			 * });
			 */
		} else {
			NMSHandler.getNMS().openMerchant(player, getMerchant());
		}

	}

	public default void onBreak() {
		ShopUtil.removeShop(getLocation());
	}

	public default void onShopClose(HumanEntity player) {
		for (MerchantRecipe trade : getMerchant().getRecipes()) {
			if (trade.getUses() > 0) {
				System.out.println(player.getName() + " bought " + trade.getUses() + "*" + trade.getResult().getType()
						+ " from " + getOwner());
			}
		}
		rePopulateTrades(getMerchant());
		NMSHandler.getNMS().resetTrader(getMerchant());
	}

	public default void rePopulateTrades(Merchant merchant) {
		List<MerchantRecipe> trades = new ArrayList<MerchantRecipe>();
		for (int i = 0; i < 12; i++) {
			final int fi = i;
			ItemStack output = getOutput(i);
			ItemStack inputOne = getInputOne(i);
			ItemStack inputTwo = getInputTwo(i);
			if (output == null || (inputOne == null && inputTwo == null))
				continue;
			MerchantRecipe rec = new MerchantRecipe(output, 0, 64, false);
			if (inputOne != null)
				rec.addIngredient(inputOne);
			if (inputTwo != null)
				rec.addIngredient(inputTwo);
			trades.add(rec);
		}
		merchant.setRecipes(trades);
	}

}
