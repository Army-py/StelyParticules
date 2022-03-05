package fr.army.events.InventoryClick;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.army.App;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;

public class MainInventory implements Listener{
	@EventHandler
	public void invev(InventoryClickEvent e) {
		if(e.getClickedInventory() != null && e.getCurrentItem() != null) {
			if(e.getInventory().getViewers().size() != 0 && e.getInventory().getViewers().get(0).getOpenInventory().getTitle().equals("§5§lStelyParticules")) {
				Player player = (Player) e.getWhoClicked();
				
				if(e.getCurrentItem().getType().equals(Material.getMaterial(App.config.getString("main.Particles.itemType")))) {
					player.openInventory(App.inventory.createParticleInventory());
				}else if(e.getCurrentItem().getType().equals(Material.getMaterial(App.config.getString("main.Sounds.itemType")))) {
					player.openInventory(App.inventory.createSoundInventory());
				}else if(e.getCurrentItem().getType().equals(Material.getMaterial(App.config.getString("main.SoundPack.itemType")))) {
					soundPackMessage(player);
					player.closeInventory();
					player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 1.0F);
				}
				e.setCancelled(true);
			}
		}
	}

	public final static void soundPackMessage(Player player) {
		final TextComponent component = new TextComponent(TextComponent.fromLegacyText(App.config.getString("prefix")+" "));
		final ClickEvent click = new ClickEvent(ClickEvent.Action.OPEN_URL, App.config.getString("packUrl"));

		String message = App.config.getString("packMessage");
		component.addExtra(new ComponentBuilder(new TextComponent(TextComponent.fromLegacyText(message))).event(click).create()[0]);

		player.spigot().sendMessage(component); 
	}
}
