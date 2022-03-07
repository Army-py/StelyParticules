package fr.army.events.InventoryClick;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import fr.army.App;
import fr.army.utils.InventoryGenerator;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;

public class MainInventory implements Listener{
	@EventHandler
	public void inventoryClick(InventoryClickEvent event) {
		if(event.getCurrentItem() == null || !App.config.getConfigurationSection("inventories").getValues(true).containsValue(event.getView().getTitle())){
            return;
        }

		Player player = (Player) event.getWhoClicked();
		
		if(event.getCurrentItem().getItemMeta().getDisplayName().equals(App.config.getString("main.Particles.itemName"))) {
			Inventory inventory = InventoryGenerator.createMainInventory();
			player.openInventory(inventory);
		}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(App.config.getString("main.Sounds.itemName"))) {
			Inventory inventory = InventoryGenerator.createSoundInventory(player.getName());
			player.openInventory(inventory);
		}else if(event.getCurrentItem().getItemMeta().getDisplayName().equals(App.config.getString("main.SoundPack.itemName"))) {
			soundPackMessage(player);
			player.closeInventory();
			player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 1.0F);
		}
		event.setCancelled(true);
	}

	
	public final static void soundPackMessage(Player player) {
		final TextComponent component = new TextComponent(TextComponent.fromLegacyText(App.config.getString("prefix")+" "));
		final ClickEvent click = new ClickEvent(ClickEvent.Action.OPEN_URL, App.config.getString("packUrl"));

		String message = App.config.getString("packMessage");
		component.addExtra(new ComponentBuilder(new TextComponent(TextComponent.fromLegacyText(message))).event(click).create()[0]);

		player.spigot().sendMessage(component); 
	}
}
