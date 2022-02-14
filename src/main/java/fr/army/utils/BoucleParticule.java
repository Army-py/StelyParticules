package fr.army.utils;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class BoucleParticule extends BukkitRunnable {
	
	Player player;
	Particle particule;
	
	public BoucleParticule(Player player, Particle particule) {
		this.player = player;
		this.particule = particule;
	}

	double t = 0;

	@Override
	public void run(){
		t = t + 0.5;
		Location loc = player.getLocation();
		Vector direction = loc.getDirection().normalize();
		double x = direction.getX() * t;
		double y = direction.getY() * t + 1.5;
		double z = direction.getZ() * t;
		loc.add(x,y,z);
		player.getLocation().getWorld().spawnParticle(particule, loc, 1);
		loc.subtract(x,y,z);
		
		if (t == 30){
			this.cancel();
		}
	}
}
