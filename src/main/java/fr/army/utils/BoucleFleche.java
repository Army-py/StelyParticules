package fr.army.utils;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class BoucleFleche extends BukkitRunnable {
	
	Projectile projectile;
	Particle particule;
	Player player;
	
	public BoucleFleche(Player player, Projectile projectile, Particle particule) {
		this.projectile = projectile;
		this.particule = particule;
		this.player = player;
	}

	double t = 0;
	double stop = 0;

	@Override
	public void run(){
		stop++;
		Location loc = projectile.getLocation();
		Vector direction = loc.getDirection().normalize();
		double x = direction.getX() * t;
		double y = direction.getY() * t;
		double z = direction.getZ() * t;
		loc.add(x,y,z);
		player.getLocation().getWorld().spawnParticle(particule, loc, 1);
		loc.subtract(x,y,z);

		if (stop == 50){
			this.cancel();
		}
	}
}
