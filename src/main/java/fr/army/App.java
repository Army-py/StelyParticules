package fr.army;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
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
import fr.army.utils.SQLManager;

public class App extends JavaPlugin implements Listener{

	public static InventoryGenerator inventory = new InventoryGenerator();

	public static String permission;

	public static Plugin instance;

	public static SQLManager sqlManager;

	public static YamlConfiguration config;
	public static YamlConfiguration particlesData;
	public static YamlConfiguration soundsData;

	public static File particlesDataFile;
	public static File soundsDataFile;

	public void onEnable(){
		getLogger().info("StelyParticule ON");

		instance = this;
		this.saveDefaultConfig();
		
		particlesData = initFile(this.getDataFolder(), "particlesData.yml");
		particlesDataFile = new File(this.getDataFolder(), "particlesData.yml");

		soundsData = initFile(this.getDataFolder(), "soundsData.yml");
		soundsDataFile = new File(this.getDataFolder(), "soundsData.yml");

		config = initFile(this.getDataFolder(), "config.yml");
		permission = config.getString("permission");
		
		App.sqlManager = new SQLManager();
        try {
            sqlManager.connect();
            this.getLogger().info("SQL connectée au plugin !");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
        }

		getCommand("stelyparticules").setExecutor(new StelyParticulesCmd());
		Bukkit.getPluginManager().registerEvents(new ProjectileLaunch(), this);
		Bukkit.getPluginManager().registerEvents(new MainInventory(), this);
		Bukkit.getPluginManager().registerEvents(new ParticlesInventory(), this);
		Bukkit.getPluginManager().registerEvents(new SoundsInventory(), this);
	}


	public void onDisable(){
		getLogger().info("StelyParticule OFF");
	}


	private YamlConfiguration initFile(File dataFolder, String fileName) {
        final File file = new File(dataFolder, fileName);
        if (!file.exists()) {
            try {
                Files.copy(Objects.requireNonNull(getResource(fileName)), file.toPath());
            } catch (IOException ignored) {}
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
