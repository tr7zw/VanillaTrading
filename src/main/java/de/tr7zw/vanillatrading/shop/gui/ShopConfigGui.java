package de.tr7zw.vanillatrading.shop.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.vanillatrading.shop.ShopHolder;

public class ShopConfigGui extends TRGui {

	private ShopHolder shopHolder;

	public ShopConfigGui(ShopHolder shopHolder) {
		super("Shop config", 6);
		this.shopHolder = shopHolder;
	}

	@Override
	public void setupGui() {
		addButton(0, 0, new ItemStack(Material.PAPER), player -> {
			player.sendMessage("A");
		});
		addButton(1, 1, new ItemStack(Material.STICK), player -> {
			player.sendMessage("B");
		});
		for (int i = 0; i < 12; i++) {
			final int fi = i;
			ItemStack output = shopHolder.getOutput(i);
			ItemStack inputOne = shopHolder.getInputOne(i);
			ItemStack inputTwo = shopHolder.getInputTwo(i);
			addButton(0 + (i % 2 == 0 ? 0 : 6), i / 2, inputOne == null ? new ItemStack(Material.BARRIER) : inputOne,
					player -> {
						GuiUtil.selectItemGui(player, item -> {
							shopHolder.setInputOne(fi, item);
							this.openGui(player);
						});
					},
					player -> {
						GuiUtil.openStorage(player, shopHolder.getInputOneStorage(fi), this, inv -> {
							shopHolder.setInputOneStorage(fi, inv.getContents());
							player.closeInventory();
						});
					});
			addButton(1 + (i % 2 == 0 ? 0 : 6), i / 2, inputTwo == null ? new ItemStack(Material.BARRIER) : inputTwo,
					player -> {
						GuiUtil.selectItemGui(player, item -> {
							shopHolder.setInputTwo(fi, item);
							this.openGui(player);
						});
					},
					player -> {
						GuiUtil.openStorage(player, shopHolder.getInputTwoStorage(fi), this, inv -> {
							shopHolder.setInputTwoStorage(fi, inv.getContents());
							player.closeInventory();
						});
					});
			addButton(2 + (i % 2 == 0 ? 0 : 6), i / 2, output == null ? new ItemStack(Material.BARRIER) : output,
					player -> {
						GuiUtil.selectItemGui(player, item -> {
							shopHolder.setOutput(fi, item);
							this.openGui(player);
						});
					},
					player -> {
						GuiUtil.openStorage(player, shopHolder.getOutputStorage(fi), this, inv -> {
							shopHolder.setOutputStorage(fi, inv.getContents());
							player.closeInventory();
						});
					});
		}
	}

	@Override
	public void onClose(Player player) {
		shopHolder.rePopulateTrades(shopHolder.getMerchant());
	}

}
