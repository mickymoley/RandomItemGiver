package me.mickymoley.randomitemgiver.config;

import me.mickymoley.randomitemgiver.RandomItemGiver;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.logging.Level;

public class ConfigItem {

    private Plugin plugin;
    private FileConfiguration itemConfig = null;
    private File itemConfigFile = null;
    private static String FILE_NAME = "itemConfig.yml";

    public ConfigItem(RandomItemGiver plugin){
        this.plugin = plugin;
    }

    public void reloadItemConfig() {
        if (itemConfigFile == null) {
            itemConfigFile = new File(plugin.getDataFolder(), FILE_NAME);
        }
        itemConfig = YamlConfiguration.loadConfiguration(itemConfigFile);

        // Look for defaults in the jar
        Reader defConfigStream = null;
        try {
            defConfigStream = new InputStreamReader(plugin.getResource(FILE_NAME), "UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            itemConfig.setDefaults(defConfig);
        }
    }

    public FileConfiguration getItemConfig() {
        if (itemConfig == null) {
            reloadItemConfig();
        }
        return itemConfig;
    }

    public void saveItemConfig() {
        if (itemConfig == null || itemConfigFile == null) {
            return;
        }
        try {
            getItemConfig().save(itemConfigFile);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + itemConfigFile, ex);
        }
    }

    public void saveDefaultConfig() {
        if (itemConfigFile == null) {
            itemConfigFile = new File(plugin.getDataFolder(), FILE_NAME);
        }
        if (!itemConfigFile.exists()) {
            plugin.saveResource(FILE_NAME, false);
        }
    }
}
