package fr.army.events;

import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import fr.army.App;
import fr.army.utils.BoucleFleche;

public class ProjectileLaunch implements Listener{
    @EventHandler
	public void launch(ProjectileLaunchEvent e) {

		if(e.getEntity().getShooter() instanceof Player) {

			Player player = (Player) e.getEntity().getShooter();
			if(player.hasPermission(App.permission) && App.soundsData.isSet(player.getName())) {
				if(App.soundsData.getBoolean(player.getName()+".Disable") == false){
					if((e.getEntityType().equals(EntityType.ARROW) || e.getEntityType().equals(EntityType.TRIDENT))) {
						for(String str : App.config.getConfigurationSection("sounds").getKeys(false)){
							if(App.soundsData.getBoolean(player.getName()+"."+str) == true) {
								String sound = App.config.getString("sounds."+str+".sound");
								player.getWorld().playSound(player.getLocation(), sound, 1.0F, 1.0F);
							}
						}
					}
				}
			}

			//if(player.hasPermission("stelybow.particule.*") || player.hasPermission("stelybow.particule.fleche")) {
			if(player.hasPermission(App.permission) && App.particlesData.isSet(player.getName())) {
				if(App.particlesData.getBoolean(player.getName()+".Disable") == false){
					if((e.getEntityType().equals(EntityType.ARROW) || e.getEntityType().equals(EntityType.TRIDENT))) {
						for(String str : App.config.getConfigurationSection("particles").getKeys(false)){
							if(App.particlesData.getBoolean(player.getName()+"."+str) == true) {
								Particle particle = Particle.valueOf(App.config.getString("particles."+str+".particle"));
								BoucleFleche fleche = new BoucleFleche(player, e.getEntity(), particle);
								fleche.runTaskTimer(App.instance, 0 ,1);
							}
						}
					}
				}
			}
		}
	}
}
