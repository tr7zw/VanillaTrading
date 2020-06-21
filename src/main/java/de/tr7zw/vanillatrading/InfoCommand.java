package de.tr7zw.vanillatrading;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTItem;

public class InfoCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("displaytracker.info")) {
			sender.sendMessage("§4You don't have permissions!");
			return true;
		}
		if (!(sender instanceof Player)) {
			sender.sendMessage("§4This command has to be run by a Player!");
			return true;
		}
		Player player = (Player) sender;
		ItemStack hand = player.getItemInHand();
		if (hand == null || hand.getType() == Material.AIR) {
			sender.sendMessage("§4No item in the hand.");
			return true;
		}
		NBTItem nbti = new NBTItem(hand);
		if (nbti.hasKey("displayNameCreatorName") && nbti.hasKey("displayNameCreatorUUID")) {
			String playerName = nbti.getString("displayNameCreatorName");
			String playerUUID = nbti.getString("displayNameCreatorUUID");
			String itemName = "None?";
			if (hand.getItemMeta().hasDisplayName())
				itemName = hand.getItemMeta().getDisplayName();
			player.sendMessage("§6The last person that modified this item with the name '§r" + itemName + "§r§6' was '§r" + playerName + "§6' (§r" + playerUUID + "§6)");
		} else {
			sender.sendMessage("§4No item name data avaliable.");
			return true;
		}
		return true;
	}

}