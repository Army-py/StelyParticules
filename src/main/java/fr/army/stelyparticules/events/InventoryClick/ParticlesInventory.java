package fr.army.stelyparticules.events.InventoryClick;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import fr.army.stelyparticules.StelyParticulesPlugin;
import fr.army.stelyparticules.utils.InventoryGenerator;

public class ParticlesInventory implements Listener{
	@EventHandler
	public void inventoryClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || !StelyParticulesPlugin.config.getConfigurationSection("inventories").getValues(true).containsValue(event.getView().getTitle())){
            return;
        }
		
		Player player = (Player) event.getWhoClicked();
		
		if(!StelyParticulesPlugin.sqlManager.isRegistered(player.getName())) {
			StelyParticulesPlugin.sqlManager.insertPlayer(player.getName());
		}
		
		for(String str : StelyParticulesPlugin.config.getConfigurationSection("particles").getKeys(false)){
			String itemName = StelyParticulesPlugin.config.getString("particles."+str+".itemName");

			if(event.getCurrentItem().getItemMeta().getDisplayName().equals(StelyParticulesPlugin.config.getString("particles.Back.itemName"))) {
				Inventory inventory = InventoryGenerator.createMainInventory();
				player.openInventory(inventory);
				break;
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(itemName)) {
				StelyParticulesPlugin.sqlManager.updateParticle(player.getName(), str);
				event.getWhoClicked().closeInventory();
				player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 1.0F);
				break;
			}
		}
		event.setCancelled(true);
	}
}
