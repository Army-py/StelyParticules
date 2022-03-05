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
			if(!player.hasPermission(App.permission)) return;
			
			if(App.soundsData.isSet(player.getName())) {
				if(App.soundsData.getBoolean(player.getName()+".Disable") == false){
					if (App.config.getStringList("soundsProjectiles").contains(e.getEntityType().toString())) {
						for(String str : App.config.getConfigurationSection("sounds").getKeys(false)){
							if(App.soundsData.getBoolean(player.getName()+"."+str) == true) {
								String sound = App.config.getString("sounds."+str+".sound");
								player.getWorld().playSound(player.getLocation(), sound, 1.0F, 1.0F);
								break;
							}
						}
					}
				}
			}

			if(App.particlesData.isSet(player.getName())) {
				if(App.particlesData.getBoolean(player.getName()+".Disable") == false){
					if (App.config.getStringList("particlesProjectiles").contains(e.getEntityType().toString())) {
						for(String str : App.config.getConfigurationSection("particles").getKeys(false)){
							if(App.particlesData.getBoolean(player.getName()+"."+str) == true) {
								Particle particle = Particle.valueOf(App.config.getString("particles."+str+".particle"));
								ParticlesGenerator arrow = new ParticlesGenerator(player, e.getEntity(), particle);
								arrow.runTaskTimer(App.instance, 0 ,1);
								break;
							}
						}
					}
				}
			}
		}
	}
}
