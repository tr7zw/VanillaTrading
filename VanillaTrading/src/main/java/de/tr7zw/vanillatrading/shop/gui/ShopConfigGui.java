package de.tr7zw.vanillatrading.shop.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.vanillatrading.ShopUtil;
import de.tr7zw.vanillatrading.shop.ShopHolder;

public class ShopConfigGui extends TRGui {
	
	private final static String changeLore = "§rLeft click to change the Item";
	private final static String storageLore = "§rRightclick to access the storage";
	private final static ItemStack defaultItemInput = ShopUtil.getNamedCopy(new ItemStack(Material.BARRIER), "§r§6No input Item", changeLore, storageLore);
	private final static ItemStack defaultItemOutput = ShopUtil.getNamedCopy(new ItemStack(Material.BARRIER), "§r§6No output Item", changeLore, storageLore);
	private ShopHolder shopHolder;
	private boolean justClose = true;

	public ShopConfigGui(ShopHolder shopHolder) {
		super("Shop config", Math.min(shopHolder.getTradingSlots() / 2 + 1, 6));
		this.shopHolder = shopHolder;
	}

	@Override
	public void setupGui() {
		for (int i = 0; i < shopHolder.getTradingSlots(); i++) {
			final int fi = i;
			ItemStack output = shopHolder.getOutput(i);
			ItemStack inputOne = shopHolder.getInputOne(i);
			ItemStack inputTwo = shopHolder.getInputTwo(i);
			addButton(0 + (i % 2 == 0 ? 0 : 6), i / 2, inputOne == null ? defaultItemInput : ShopUtil.getNamedCopy(inputOne, "§r§61st Input", changeLore, storageLore),
					player -> {
						justClose = false;
						GuiUtil.selectItemGui(player, item -> {
							shopHolder.setInputOne(fi, item);
							player.closeInventory();
							cleanup();
						});
					},
					player -> {
						justClose = false;
						GuiUtil.openStorage(player, shopHolder.getInputOneStorage(fi), shopHolder, this, inv -> {
							shopHolder.setInputOneStorage(fi, inv.getContents());
							player.closeInventory();
							cleanup();
						});
					});
			addButton(1 + (i % 2 == 0 ? 0 : 6), i / 2, inputTwo == null ? defaultItemInput : ShopUtil.getNamedCopy(inputTwo, "§r§62nd Input", changeLore, storageLore),
					player -> {
						justClose = false;
						GuiUtil.selectItemGui(player, item -> {
							shopHolder.setInputTwo(fi, item);
							player.closeInventory();
							cleanup();
						});
					},
					player -> {
						justClose = false;
						GuiUtil.openStorage(player, shopHolder.getInputTwoStorage(fi), shopHolder, this, inv -> {
							shopHolder.setInputTwoStorage(fi, inv.getContents());
							player.closeInventory();
							cleanup();
						});
					});
			addButton(2 + (i % 2 == 0 ? 0 : 6), i / 2, output == null ? defaultItemOutput : ShopUtil.getNamedCopy(output, "§r§6Output", changeLore, storageLore),
					player -> {
						justClose = false;
						GuiUtil.selectItemGui(player, item -> {
							shopHolder.setOutput(fi, item);
							player.closeInventory();
							cleanup();
						});
					},
					player -> {
						justClose = false;
						GuiUtil.openStorage(player, shopHolder.getOutputStorage(fi), shopHolder, this, inv -> {
							shopHolder.setOutputStorage(fi, inv.getContents());
							player.closeInventory();
							cleanup();
						});
					});
		}
	}

	@Override
	public void onClose(Player player) {
		if(justClose) {
			cleanup();
		}
	}
	
	private void cleanup() {
		shopHolder.setOpenBy(null);
		shopHolder.rePopulateTrades(shopHolder.getMerchant());
	}

}
