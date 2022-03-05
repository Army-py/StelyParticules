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

	public void onEnable(){
		getLogger().info("StelyParticule ON");

		instance = this;
		this.saveDefaultConfig();

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
		App.sqlManager.createTables();

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
}
