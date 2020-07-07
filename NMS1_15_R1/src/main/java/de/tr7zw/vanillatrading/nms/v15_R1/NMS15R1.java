package de.tr7zw.vanillatrading.nms.v15_R1;

import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftMerchantCustom;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Merchant;

import de.tr7zw.vanillatrading.nms.NMSImplementation;
import de.tr7zw.vanillatrading.shop.ShopHolder;
import net.minecraft.server.v1_15_R1.ChatComponentText;

public class NMS15R1 implements NMSImplementation {

	@Override
	public Merchant createMerchant(ShopHolder shop) {
		return new CustomShopMerchant("Shop", shop);
	}
	
	@Override
	public void openMerchant(Player player, Merchant merchant) {
		CustomShopMerchant customMerchant = (CustomShopMerchant) merchant;
		customMerchant.getMerchant().setTradingPlayer(((CraftPlayer)player).getHandle());
		customMerchant.getMerchant().openTrade(((CraftPlayer)player).getHandle(), new ChatComponentText(customMerchant.getTitle()), 0);
	}
	

	@Override
	public void resetTrader(Merchant merchant) {
		CustomShopMerchant customMerchant = (CustomShopMerchant) merchant;
		customMerchant.getMerchant().setTradingPlayer(null);
	}
	
	private static class CustomShopMerchant extends CraftMerchantCustom implements de.tr7zw.vanillatrading.nms.ShopMerchant {

		private final String title;
		private final ShopHolder shop;
		
		public CustomShopMerchant(String title, ShopHolder shop) {
			super(title);
			this.title = title;
			this.shop = shop;
		}
		
		public ShopHolder getShop() {
			return shop;
		}
		
		public String getTitle() {
			return title;
		}
		
	}

}
