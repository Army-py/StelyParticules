package fr.army.events.InventoryClick;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.army.App;

public class ParticlesInventory implements Listener{
	private void isNull(InventoryClickEvent e, String name){
		if(!App.particlesData.isSet(e.getWhoClicked().getName()+name)){
			App.particlesData.set(e.getWhoClicked().getName()+"."+name, false);
		}
	}


	private void setSave(InventoryClickEvent e, String name){
		for(String str : App.config.getConfigurationSection("particles").getKeys(false)){
			if(name.equals("."+str)){
				App.particlesData.set(e.getWhoClicked().getName()+"."+name, true);
			}else{
				App.particlesData.set(e.getWhoClicked().getName()+"."+str, false);
			}
		}
	}


	@EventHandler
	public void invev(InventoryClickEvent e) {
		if(e.getClickedInventory() != null && e.getCurrentItem() != null) {
			if(e.getInventory().getViewers().size() != 0 && e.getInventory().getViewers().get(0).getOpenInventory().getTitle().equals(App.config.getString("inventories.particles.name"))) {
				Player player = (Player) e.getWhoClicked();
				
				if(!App.particlesData.isSet(e.getWhoClicked().getName())) {
					for(String str : App.config.getConfigurationSection("particles").getKeys(false)){
						this.isNull(e, str);
					}
				}
				
				for(String str : App.config.getConfigurationSection("particles").getKeys(false)){
					Material material = Material.getMaterial(App.config.getString("particles."+str+".itemType"));
					if(e.getCurrentItem().getType().equals(Material.getMaterial(App.config.getString("particles.Back.itemType")))) {
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
