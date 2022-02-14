package fr.army.utils;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.army.App;

public class InventoryGenerator {
    Player player;
	String nom;
	Inventory inventaire;

	HashMap<Integer, ItemStack> items;

	public String getname() {
		return player.openInventory(inventaire).getTitle();
	}

	public void setname(String nom) {
		this.nom = nom;
	}

	public void setplayer(Player player) {
		this.player = player;
	}

	public Player getplayer() {
		return player;
	}

	public HashMap<Integer, ItemStack> getitems() {
		return items;
	}

	public int getitemssize() {
		return items.size();
	}

	public Inventory getinventory() {
		return inventaire;
	}
	
	public void createMainInventory() {
		Integer slots = App.config.getInt("inventories.main.slots");
		Inventory inventaire = Bukkit.createInventory(null, slots, nom);
		this.inventaire = inventaire;
		for(int i = 0; i < slots; i++) {
			if(i != 11 || i != 15) {
				inventaire.setItem(i, vitredeco());
			}
		}
		
		for(String str : App.config.getConfigurationSection("main").getKeys(false)){
			Integer slot = App.config.getInt("main."+str+".slot");
			Material material = Material.getMaterial(App.config.getString("main."+str+".itemType"));
			String name = App.config.getString("main."+str+".itemName");

			inventaire.setItem(slot, ItemBuilder.getItem(material, name));
		}
		
		player.openInventory(inventaire);
	}


	public void createParticleInventory() {
		Integer slots = App.config.getInt("inventories.particles.slots");
		Inventory inventaire = Bukkit.createInventory(null, slots, nom);
		this.inventaire = inventaire;
		for(int i = 0; i < slots; i++) {
			inventaire.setItem(i, vitredeco());
		}

		for(String str : App.config.getConfigurationSection("particles").getKeys(false)){
			Integer slot = App.config.getInt("particles."+str+".slot");
			Material material = Material.getMaterial(App.config.getString("particles."+str+".itemType"));
			String name = App.config.getString("particles."+str+".itemName");

			inventaire.setItem(slot, ItemBuilder.getItem(material, name));
		}
		player.openInventory(inventaire);
	}


	public void createSoundInventory() {
		Integer slots = App.config.getInt("inventories.sounds.slots");
		Inventory inventaire = Bukkit.createInventory(null, slots, nom);
		this.inventaire = inventaire;
		for(int i = 0; i < slots; i++) {
			inventaire.setItem(i, vitredeco());
		}
		
		for(String str : App.config.getConfigurationSection("sounds").getKeys(false)){
			Integer slot = App.config.getInt("sounds."+str+".slot");
			Material material = Material.getMaterial(App.config.getString("sounds."+str+".itemType"));
			String name = App.config.getString("sounds."+str+".itemName");

			inventaire.setItem(slot, ItemBuilder.getItem(material, name));
		}
		player.openInventory(inventaire);
	}

	
	public ItemStack vitredeco() {
		ItemStack it = new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1);
		ItemMeta meta = it.getItemMeta();
		meta.setDisplayName(" ");
		it.setItemMeta(meta);
		return it;
	}
}
