package de.tr7zw.vanillatrading.shop;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.Merchant;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTTileEntity;
import de.tr7zw.vanillatrading.nms.NMSHandler;

public class BlockHolder implements ShopHolder {

	private final Block block;
	private final NBTTileEntity nbtTile;
	private final NBTCompound persistendHolder;
	private Merchant shopMerchant = null;

	public BlockHolder(Block block, NBTTileEntity nbtTile) {
		this.block = block;
		this.nbtTile = nbtTile;
		persistendHolder = nbtTile.getPersistentDataContainer();
		shopMerchant = NMSHandler.getNMS().createMerchant(this);
		rePopulateTrades(shopMerchant);
	}
	
	public Block getBlock() {
		return block;
	}

	@Override
	public NBTCompound getPersistentDataContainer() {
		return persistendHolder;
	}

	@Override
	public Location getLocation() {
		return block.getLocation();
	}

	@Override
	public Merchant getMerchant() {
		return shopMerchant;
	}
	
}
