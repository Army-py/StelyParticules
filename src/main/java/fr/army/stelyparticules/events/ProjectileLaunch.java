package fr.army.stelyparticules.events;

import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import fr.army.stelyparticules.StelyParticulesPlugin;
import fr.army.stelyparticules.utils.ParticlesGenerator;

public class ProjectileLaunch implements Listener{
    @EventHandler
	public void projectileLaunch(ProjectileLaunchEvent e) {
		if(e.getEntity().getShooter() instanceof Player) {
			Player player = (Player) e.getEntity().getShooter();
			if (!player.hasPermission(StelyParticulesPlugin.permission)) return;
			
			if (StelyParticulesPlugin.sqlManager.isRegistered(player.getName())) {
				if (StelyParticulesPlugin.config.getStringList("soundsProjectiles").contains(e.getEntityType().toString())) {
					if (!StelyParticulesPlugin.sqlManager.isDisableSounds(player.getName())) {
						String sound = StelyParticulesPlugin.config.getString("sounds."+StelyParticulesPlugin.sqlManager.getSound(player.getName())+".sound");
						player.getWorld().playSound(player.getLocation(), sound, 1.0F, 1.0F);
					}
				}

				if (StelyParticulesPlugin.config.getStringList("particlesProjectiles").contains(e.getEntityType().toString())) {
					if (!StelyParticulesPlugin.sqlManager.isDisableParticles(player.getName())) {
						Particle particle = Particle.valueOf(StelyParticulesPlugin.config.getString("particles."+StelyParticulesPlugin.sqlManager.getParticle(player.getName())+".particle"));
						ParticlesGenerator arrow = new ParticlesGenerator(player, e.getEntity(), particle);
						arrow.runTaskTimer(StelyParticulesPlugin.instance, 0 ,1);
					}
				}
			}
		}
	}
}
