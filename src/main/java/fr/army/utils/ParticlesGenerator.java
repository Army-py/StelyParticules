package fr.army.utils;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import fr.army.App;

public class ParticlesGenerator extends BukkitRunnable {
	Projectile projectile;
	Particle particle;
	Player player;

	double stop = 0;


	public ParticlesGenerator(Player player, Projectile projectile, Particle particle) {
		this.projectile = projectile;
		this.particle = particle;
		this.player = player;
	}


	@Override
	public void run(){
		stop++;
		Location location = projectile.getLocation();
		Vector direction = location.getDirection().normalize();
		double x = direction.getX() * 0;
		double y = direction.getY() * 0;
		double z = direction.getZ() * 0;
		location.add(x, y, z);
		player.getLocation().getWorld().spawnParticle(particle, location, 1);
		location.subtract(x, y, z);

		if (stop == App.config.getInt("maximumParticlesNumber")){
			this.cancel();
		}
	}
}
