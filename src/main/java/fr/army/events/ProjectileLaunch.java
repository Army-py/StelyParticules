package fr.army.events;

import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import fr.army.App;
import fr.army.utils.ParticlesGenerator;

public class ProjectileLaunch implements Listener{
    @EventHandler
	public void launch(ProjectileLaunchEvent e) {
		if(e.getEntity().getShooter() instanceof Player) {
			Player player = (Player) e.getEntity().getShooter();
			if (!player.hasPermission(App.permission)) return;
			
			if (App.sqlManager.isRegistered(player.getName())) {
				if (App.config.getStringList("soundsProjectiles").contains(e.getEntityType().toString())) {
					if (!App.sqlManager.isDisableSounds(player.getName())) {
						String sound = App.config.getString("sounds."+App.sqlManager.getSound(player.getName())+".sound");
						player.getWorld().playSound(player.getLocation(), sound, 1.0F, 1.0F);
					}
				}
			}

			if(App.sqlManager.isRegistered(player.getName())) {
				if (App.config.getStringList("particlesProjectiles").contains(e.getEntityType().toString())) {
					if (!App.sqlManager.isDisableParticles(player.getName())) {
						Particle particle = Particle.valueOf(App.config.getString("particles."+App.sqlManager.getParticle(player.getName())+".particle"));
						ParticlesGenerator arrow = new ParticlesGenerator(player, e.getEntity(), particle);
						arrow.runTaskTimer(App.instance, 0 ,1);
					}
				}
			}
		}
	}
}
