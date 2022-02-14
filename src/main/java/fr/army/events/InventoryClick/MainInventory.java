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
					App.inventory.setname(App.config.getString("inventories.particles.name"));
					App.inventory.setplayer(player);
					App.inventory.createParticleInventory();
				}else if(e.getCurrentItem().getType().equals(Material.getMaterial(App.config.getString("main.Sounds.itemType")))) {
					App.inventory.setname(App.config.getString("inventories.sounds.name"));
					App.inventory.setplayer(player);
					App.inventory.createSoundInventory();
				}else if(e.getCurrentItem().getType().equals(Material.getMaterial(App.config.getString("main.SoundPack.itemType")))) {
					Annonce(player);
					player.closeInventory();
					player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 1.0F);
				}
				e.setCancelled(true);
			}
		}
	}

	public final static void Annonce(Player p) {
		final TextComponent output = new TextComponent(TextComponent.fromLegacyText(App.config.getString("prefix")+" "));
		final ClickEvent click1D = new ClickEvent(ClickEvent.Action.OPEN_URL, App.config.getString("packUrl"));

		String clicable = App.config.getString("packMessage");
		output.addExtra(new ComponentBuilder(new TextComponent(TextComponent.fromLegacyText(clicable))).event(click1D).create()[0]);

		p.spigot().sendMessage(output); 
	}
}
