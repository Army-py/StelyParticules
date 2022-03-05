package fr.army.events.InventoryClick;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.army.App;

public class SoundsInventory implements Listener{
	private void isNull(InventoryClickEvent e, String name){
		if(!App.soundsData.isSet(e.getWhoClicked().getName()+name)){
			App.soundsData.set(e.getWhoClicked().getName()+"."+name, false);
		}
	}


	private void setSave(InventoryClickEvent e, String name){
		for(String str : App.config.getConfigurationSection("sounds").getKeys(false)){
			if(name.equals("."+str)){
				App.soundsData.set(e.getWhoClicked().getName()+"."+name, true);
			}else{
				App.soundsData.set(e.getWhoClicked().getName()+"."+str, false);
			}
		}
	}

	
	@EventHandler
	public void invev(InventoryClickEvent e) {
		if(e.getClickedInventory() != null && e.getCurrentItem() != null) {
			if(e.getInventory().getViewers().size() != 0 && e.getInventory().getViewers().get(0).getOpenInventory().getTitle().equals(App.config.getString("inventories.sounds.name"))) {
				Player player = (Player) e.getWhoClicked();
				
				if(!App.soundsData.isSet(e.getWhoClicked().getName())) {
					for(String str : App.config.getConfigurationSection("sounds").getKeys(false)){
						this.isNull(e, str);
					}
				}
				
				for(String str : App.config.getConfigurationSection("sounds").getKeys(false)){
					Material material = Material.getMaterial(App.config.getString("sounds."+str+".itemType"));
					
					if(e.getCurrentItem().getType().equals(Material.getMaterial(App.config.getString("sounds.Back.itemType")))) {
						player.openInventory(App.inventory.createMainInventory());
					}else if(e.getCurrentItem().getType().equals(material)) {
						this.setSave(e, "."+str);
						e.getWhoClicked().closeInventory();
						player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 1.0F);
					}
				}
				
				App.saveDataFiles();
				e.setCancelled(true);
			}
		}
	}
}


