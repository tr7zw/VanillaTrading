package de.tr7zw.vanillatrading.shop;

import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantRecipe;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTEntity;

public class EntityHolder implements ShopHolder {

	private final Entity entity;
	private final NBTEntity nbtEntity;
	private final NBTCompound persistendHolder;
	
	public EntityHolder(Entity entity, NBTEntity nbtEntity) {
		this.entity = entity;
		this.nbtEntity = nbtEntity;
		this.persistendHolder = nbtEntity.getPersistentDataContainer();
	}
	
	public Entity getEntity() {
		return entity;
	}

	@Override
	public NBTCompound getPersistentDataContainer() {
		return persistendHolder;
	}

	@Override
	public Location getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Merchant getMerchant() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, Integer> getStoredIds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStoredIds(Map<Integer, Integer> mapping) {
		// TODO Auto-generated method stub
		
	}
	
}
