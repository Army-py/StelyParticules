package fr.army;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import fr.army.commands.StelyParticulesCmd;
import fr.army.events.ProjectileLaunch;
import fr.army.events.InventoryClick.MainInventory;
import fr.army.events.InventoryClick.ParticlesInventory;
import fr.army.events.InventoryClick.SoundsInventory;
import fr.army.utils.InventoryGenerator;

public class App extends JavaPlugin implements Listener{

	public static InventoryGenerator inventory = new InventoryGenerator();

	public static String permission;

	public static Plugin instance;

	public static YamlConfiguration config;
	public static YamlConfiguration particlesData;
	public static YamlConfiguration soundsData;

	public static File particlesDataFile;
	public static File soundsDataFile;

	public void onEnable(){
		instance = this;
		this.saveDefaultConfig();
		
		particlesData = initFile(this.getDataFolder(), "particlesData.yml");
		particlesDataFile = new File(this.getDataFolder(), "particlesData.yml");

		soundsData = initFile(this.getDataFolder(), "soundsData.yml");
		soundsDataFile = new File(this.getDataFolder(), "soundsData.yml");

		config = initFile(this.getDataFolder(), "config.yml");
		permission = config.getString("permission");

		getCommand("stelyparticules").setExecutor(new StelyParticulesCmd());
		Bukkit.getPluginManager().registerEvents(new ProjectileLaunch(), this);
		Bukkit.getPluginManager().registerEvents(new MainInventory(), this);
		Bukkit.getPluginManager().registerEvents(new ParticlesInventory(), this);
		Bukkit.getPluginManager().registerEvents(new SoundsInventory(), this);

	}
	
	public void onDisable(){

	}

	/*@EventHandler
	public void move(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		Location loc = new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
		loc.add(0, 0.1, 0);
		Config config = new Config("databasejoueur.yml", StelyParticule.instance);

		if(!config.isNull(player.getName()) && player.hasPermission(permission)) {

			if(config.getBoolean(player.getName()+".Heart") == true) {
				player.getWorld().spawnParticle(Particle.HEART, loc, 1);
			}else if(config.getBoolean(player.getName()+".Lava") == true) {
				player.getWorld().spawnParticle(Particle.DRIP_LAVA, loc, 1);
			}else if(config.getBoolean(player.getName()+".Water") == true) {
				player.getWorld().spawnParticle(Particle.DRIP_WATER, loc, 1);
			}else if(config.getBoolean(player.getName()+".Dolphin") == true) {
				player.getWorld().spawnParticle(Particle.DOLPHIN, loc, 1);
			}else if(config.getBoolean(player.getName()+".Spell") == true) {
				player.getWorld().spawnParticle(Particle.SPELL, loc, 1);
			}else if(config.getBoolean(player.getName()+".SpellMob") == true) {
				player.getWorld().spawnParticle(Particle.SPELL_MOB_AMBIENT, loc, 1);
			}else if(config.getBoolean(player.getName()+".Nautilus") == true) {
				player.getWorld().spawnParticle(Particle.NAUTILUS, loc, 1);
			}else if(config.getBoolean(player.getName()+".Note") == true) {
				player.getWorld().spawnParticle(Particle.NOTE, loc, 1);
			}else if(config.getBoolean(player.getName()+".SpellWitch") == true) {
				player.getWorld().spawnParticle(Particle.SPELL_WITCH, loc, 1);
			}else if(config.getBoolean(player.getName()+".Disable") == true) {

			}
		}
		//a garder

		//player.getWorld().spawnParticle(Particle.HEART, loc, 1);
		//player.getWorld().spawnParticle(Particle.DRIP_LAVA, loc, 1);
		//player.getWorld().spawnParticle(Particle.DRIP_WATER, loc, 1);
		//player.getWorld().spawnParticle(Particle.DOLPHIN, loc, 1);
		//player.getWorld().spawnParticle(Particle.SPELL, loc, 1);
		//player.getWorld().spawnParticle(Particle.SPELL_MOB_AMBIENT, loc, 1);
		//player.getWorld().spawnParticle(Particle.NAUTILUS, loc, 1);
		//player.getWorld().spawnParticle(Particle.NOTE, loc, 1);
		//player.getWorld().spawnParticle(Particle.SPELL_WITCH, loc, 1);

	}*/


	private YamlConfiguration initFile(File dataFolder, String fileName) {
        final File file = new File(dataFolder, fileName);
        if (!file.exists()) {
            try {
                Files.copy(Objects.requireNonNull(getResource(fileName)), file.toPath());
            } catch (IOException ignored) {
            }
        }
        return YamlConfiguration.loadConfiguration(file);
    }

	public static void saveDataFiles(){
		try {
			App.particlesData.save(particlesDataFile);
			App.soundsData.save(soundsDataFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
