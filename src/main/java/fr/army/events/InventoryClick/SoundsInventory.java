package fr.army.events.InventoryClick;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import fr.army.App;
import fr.army.utils.InventoryGenerator;

public class SoundsInventory implements Listener{
	@EventHandler
	public void inventoryClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || !App.config.getConfigurationSection("inventories").getValues(true).containsValue(event.getView().getTitle())){
            return;
        }

		Player player = (Player) event.getWhoClicked();
				
		if(!App.sqlManager.isRegistered(player.getName())) {
			App.sqlManager.insertPlayer(player.getName());
		}
		
		for(String str : App.config.getConfigurationSection("sounds").getKeys(false)){
			String itemName = App.config.getString("sounds."+str+".itemName");
			
			if(event.getCurrentItem().getItemMeta().getDisplayName().equals(App.config.getString("sounds.Back.itemName"))) {
				Inventory inventory = InventoryGenerator.createMainInventory();
				player.openInventory(inventory);
			}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(itemName)) {
				App.sqlManager.updateSound(player.getName(), str);
				event.getWhoClicked().closeInventory();
				player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 1.0F);
			}
		}
		event.setCancelled(true);
	}
}


