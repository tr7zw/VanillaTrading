package de.tr7zw.vanillatrading.shop;

import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftMerchantCustom;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantInventory;
import org.bukkit.inventory.MerchantRecipe;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.vanillatrading.ShopUtil;
import de.tr7zw.vanillatrading.nms.NMSHandler;
import net.minecraft.server.v1_15_R1.ChatComponentText;

public interface ShopHolder {

	public NBTCompound getPersistentDataContainer();
	
	public default World getWorld() {
		return getLocation().getWorld();
	}
	
	public Location getLocation();
	
	public default NBTCompound getShopStorage() {
		if(!getPersistentDataContainer().hasKey("vanillatrading")) {
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
	
	public Merchant getMerchant();
	
	public default void onInteract(Player player) {
		NMSHandler.getNMS().openMerchant(player, getMerchant());

	}
	
	public default void onBreak() {
		ShopUtil.removeShop(getLocation());
	}
	
	public default void onShopClose(HumanEntity player) {
		for (MerchantRecipe trade : getMerchant().getRecipes()) {
			if (trade.getUses() > 0) {
				System.out.println(player.getName() + " bought " + trade.getUses() + "*"
						+ trade.getResult().getType() + " from " + getOwner());
			}
		}
		rePopulateTrades(getMerchant());
	}
	
	public default void rePopulateTrades(Merchant merchant) {
		MerchantRecipe rec2 = new MerchantRecipe(new ItemStack(Material.BONE), 0, 64, false);
		rec2.addIngredient(new ItemStack(Material.EMERALD));
		merchant.setRecipes(Arrays.asList(rec2));
	}
	
}