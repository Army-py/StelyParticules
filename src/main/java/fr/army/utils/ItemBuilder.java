package fr.army.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {
	public static ItemStack getItem(Material material, String name, boolean isSelect){
		ItemStack item = new ItemStack(material, 1);
		ItemMeta meta = item.getItemMeta();
		if (isSelect){
			meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		}
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
		meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}
}
