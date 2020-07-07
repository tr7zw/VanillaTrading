package de.tr7zw.vanillatrading.nms;

import org.bukkit.Bukkit;

import de.tr7zw.nbtapi.utils.MinecraftVersion;
import de.tr7zw.vanillatrading.VanillaTrading;
import de.tr7zw.vanillatrading.nms.v15_R1.MerchantGuiListener14R1;
import de.tr7zw.vanillatrading.nms.v15_R1.MerchantGuiListener15R1;
import de.tr7zw.vanillatrading.nms.v15_R1.NMS14R1;
import de.tr7zw.vanillatrading.nms.v15_R1.NMS15R1;
import de.tr7zw.vanillatrading.nms.v16_R1.MerchantGuiListener16R1;
import de.tr7zw.vanillatrading.nms.v16_R1.NMS16R1;

public class NMSHandler {

	private static NMSImplementation nmsImplementation;
	
	static {
		MinecraftVersion version = MinecraftVersion.getVersion();
		if(version == MinecraftVersion.MC1_14_R1) {
			nmsImplementation = new NMS14R1();
			Bukkit.getPluginManager().registerEvents(new MerchantGuiListener14R1(), VanillaTrading.INSTANCE);
		}
		if(version == MinecraftVersion.MC1_15_R1) {
			nmsImplementation = new NMS15R1();
			Bukkit.getPluginManager().registerEvents(new MerchantGuiListener15R1(), VanillaTrading.INSTANCE);
		}
		if(version == MinecraftVersion.MC1_16_R1) {
			nmsImplementation = new NMS16R1();
			Bukkit.getPluginManager().registerEvents(new MerchantGuiListener16R1(), VanillaTrading.INSTANCE);
		}
	}
	
	public static NMSImplementation getNMS() {
		return nmsImplementation;
	}
	
}
