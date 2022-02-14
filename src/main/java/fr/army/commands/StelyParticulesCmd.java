package fr.army.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.army.App;

public class StelyParticulesCmd implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 0) {
			Player player = (Player) sender;
			if(player.hasPermission(App.permission)) {
				App.inventory.setname(App.config.getString("inventories.main.name"));
				App.inventory.setplayer(player);
				App.inventory.createMainInventory();
			}
		}
		return true;
	}

}
