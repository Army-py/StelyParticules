package fr.army.stelyparticules.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.army.stelyparticules.StelyParticulesPlugin;

public class InventoryGenerator {	
	public static Inventory createMainInventory() {
		Integer slots = StelyParticulesPlugin.config.getInt("inventories.main.slots");
		String inventoryName = StelyParticulesPlugin.config.getString("inventories.main.name");
		Inventory inventory = Bukkit.createInventory(null, slots, inventoryName);
		emptyCases(inventory, slots);
		
		for(String str : StelyParticulesPlugin.config.getConfigurationSection("main").getKeys(false)){
			Integer slot = StelyParticulesPlugin.config.getInt("main."+str+".slot");
			Material material = Material.getMaterial(StelyParticulesPlugin.config.getString("main."+str+".itemType"));
			String name = StelyParticulesPlugin.config.getString("main."+str+".itemName");

			inventory.setItem(slot, ItemBuilder.getItem(material, name, false));
		}
		
		return inventory;
	}


	public static Inventory createParticleInventory(String playername) {
		Integer slots = StelyParticulesPlugin.config.getInt("inventories.particles.slots");
		String inventoryName = StelyParticulesPlugin.config.getString("inventories.particles.name");
		Inventory inventory = Bukkit.createInventory(null, slots, inventoryName);
		emptyCases(inventory, slots);

		for(String str : StelyParticulesPlugin.config.getConfigurationSection("particles").getKeys(false)){
			Integer slot = StelyParticulesPlugin.config.getInt("particles."+str+".slot");
			Material material = Material.getMaterial(StelyParticulesPlugin.config.getString("particles."+str+".itemType"));
			String name = StelyParticulesPlugin.config.getString("particles."+str+".itemName");

			if (StelyParticulesPlugin.sqlManager.isRegistered(playername)){
				if (Material.getMaterial(StelyParticulesPlugin.config.getString("particles."+StelyParticulesPlugin.sqlManager.getParticle(playername)+".itemType")).equals(material)){
					inventory.setItem(slot, ItemBuilder.getItem(material, name, true));
				}else{
					inventory.setItem(slot, ItemBuilder.getItem(material, name, false));
				}
			}else{
				inventory.setItem(slot, ItemBuilder.getItem(material, name, false));
			}
		}
		return inventory;
	}


	public static Inventory createSoundInventory(String playername) {
		Integer slots = StelyParticulesPlugin.config.getInt("inventories.sounds.slots");
		String inventoryName = StelyParticulesPlugin.config.getString("inventories.sounds.name");
		Inventory inventory = Bukkit.createInventory(null, slots, inventoryName);
		emptyCases(inventory, slots);
		
		for(String str : StelyParticulesPlugin.config.getConfigurationSection("sounds").getKeys(false)){
			Integer slot = StelyParticulesPlugin.config.getInt("sounds."+str+".slot");
			Material material = Material.getMaterial(StelyParticulesPlugin.config.getString("sounds."+str+".itemType"));
			String name = StelyParticulesPlugin.config.getString("sounds."+str+".itemName");
			
			if (StelyParticulesPlugin.sqlManager.isRegistered(playername)){
				if (Material.getMaterial(StelyParticulesPlugin.config.getString("sounds."+StelyParticulesPlugin.sqlManager.getSound(playername)+".itemType")).equals(material)){
					inventory.setItem(slot, ItemBuilder.getItem(material, name, true));
				}else{
					inventory.setItem(slot, ItemBuilder.getItem(material, name, false));
				}
			}else{
				inventory.setItem(slot, ItemBuilder.getItem(material, name, false));
			}
		}
		return inventory;
	}


	private static void emptyCases(Inventory inventory, Integer slots) {
		ItemStack item = new ItemStack(Material.getMaterial(StelyParticulesPlugin.config.getString("emptyCase")), 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(" ");
		item.setItemMeta(meta);

        for(int i = 0; i < slots; i++) {
			inventory.setItem(i, item);
		}
	}
}
