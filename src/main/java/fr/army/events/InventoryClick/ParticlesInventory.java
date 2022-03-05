package fr.army.events.InventoryClick;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.army.App;

public class ParticlesInventory implements Listener{
	@EventHandler
	public void invev(InventoryClickEvent e) {
		if(e.getClickedInventory() != null && e.getCurrentItem() != null) {
			if(e.getInventory().getViewers().size() != 0 && e.getInventory().getViewers().get(0).getOpenInventory().getTitle().equals(App.config.getString("inventories.particles.name"))) {
				Player player = (Player) e.getWhoClicked();
				
				if(!App.sqlManager.isRegistered(player.getName())) {
					App.sqlManager.insertPlayer(player.getName());
				}
				
				for(String str : App.config.getConfigurationSection("particles").getKeys(false)){
					Material material = Material.getMaterial(App.config.getString("particles."+str+".itemType"));
					if(e.getCurrentItem().getType().equals(Material.getMaterial(App.config.getString("particles.Back.itemType")))) {
						player.openInventory(App.inventory.createMainInventory());
					}else if(e.getCurrentItem().getType().equals(material)) {
						App.sqlManager.updateParticle(player.getName(), str);
						e.getWhoClicked().closeInventory();
						player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 1.0F);
					}
				}
				e.setCancelled(true);
			}
		}
	}
}
