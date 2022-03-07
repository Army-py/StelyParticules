package fr.army.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import fr.army.App;
import fr.army.utils.InventoryGenerator;

public class StelyParticulesCmd implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 0) {
			Player player = (Player) sender;
			if(player.hasPermission(App.permission)) {
				Inventory inventory = InventoryGenerator.createMainInventory();
				player.openInventory(inventory);
			}
		}
		return true;
	}

}
