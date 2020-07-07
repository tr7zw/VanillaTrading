package de.tr7zw.vanillatrading;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.nbtapi.NBTTileEntity;
import de.tr7zw.vanillatrading.shop.BlockHolder;
import de.tr7zw.vanillatrading.shop.ShopHolder;

public class InteractListener implements Listener {

	private Set<Material> validShopBlocks = new HashSet<Material>(
			Arrays.asList(Material.CHEST, Material.ENDER_CHEST, Material.FURNACE));
	private Set<EntityType> validShopEntities = new HashSet<EntityType>(Arrays.asList(EntityType.ARMOR_STAND));

	@EventHandler
	public void onPlace(BlockPlaceEvent event) {
		if (event.getItemInHand() != null && validShopBlocks.contains(event.getItemInHand().getType())) {
			ItemMeta meta = event.getItemInHand().getItemMeta();
			if (meta.hasDisplayName() && meta.getDisplayName().equalsIgnoreCase("shop")) {
				ShopHolder shop = new BlockHolder(event.getBlockPlaced(),
						new NBTTileEntity(event.getBlockPlaced().getState()));
				shop.setOwner(event.getPlayer().getUniqueId());
			}
		}
	}

	@EventHandler
	public void onExplode(BlockExplodeEvent event) {
		for (Block block : event.blockList()) {
			if (validShopBlocks.contains(block.getType())) {
				ShopHolder shop = ShopUtil.getShop(block);
				if (shop != null) {
					event.setCancelled(true);
					return;
				}
			}
		}
	}
	
	@EventHandler
	public void onExplode(EntityExplodeEvent event) {
		for (Block block : event.blockList()) {
			if (validShopBlocks.contains(block.getType())) {
				ShopHolder shop = ShopUtil.getShop(block);
				if (shop != null) {
					event.setCancelled(true);
					return;
				}
			}
		}
	}

	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		if (event.getBlock() != null && validShopBlocks.contains(event.getBlock().getType())) {
			ShopHolder shop = ShopUtil.getShop(event.getBlock());
			if (shop != null) {
				if (event.getPlayer() == null) {
					event.setCancelled(true);
					return;
				}
				if (event.getPlayer().getUniqueId().equals(shop.getOwner())
						|| event.getPlayer().hasPermission("vanillatrading.admin")) {
					shop.onBreak();
				} else {
					event.setCancelled(true);
					event.getPlayer().sendMessage("§cThis is not your shop.");
				}
			}
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock() != null
				&& validShopBlocks.contains(event.getClickedBlock().getType())) {
			ShopHolder shop = ShopUtil.getShop(event.getClickedBlock());
			if (shop != null) {
				event.setCancelled(true);
				shop.onInteract(event.getPlayer());
			}
		}
	}

	@EventHandler
	public void onEntityInteract(PlayerInteractEntityEvent event) {
		if (event.getRightClicked() != null && validShopEntities.contains(event.getRightClicked().getType())) {
			ShopHolder shop = ShopUtil.getShop(event.getRightClicked());
			if (shop != null) {
				event.setCancelled(true);
				shop.onInteract(event.getPlayer());
			}
		}
	}

}
