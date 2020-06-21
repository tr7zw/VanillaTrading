package de.tr7zw.vanillatrading.nms;

import de.tr7zw.nbtapi.utils.MinecraftVersion;
import de.tr7zw.vanillatrading.nms.v15_R1.NMS15R1;

public class NMSHandler {

	private static NMSImplementation nmsImplementation;
	
	static {
		MinecraftVersion version = MinecraftVersion.getVersion();
		if(version == MinecraftVersion.MC1_15_R1) {
			nmsImplementation = new NMS15R1();
		}
	}
	
	public static NMSImplementation getNMS() {
		return nmsImplementation;
	}
	
}
