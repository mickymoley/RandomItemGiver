package me.mickymoley.randomitemgiver;

import me.mickymoley.randomitemgiver.commands.RIGCommandExecutor;
import me.mickymoley.randomitemgiver.commands.RIGTabCompleteExecutor;
import me.mickymoley.randomitemgiver.config.ConfigItem;
import me.mickymoley.randomitemgiver.config.ConfigHandler;
import me.mickymoley.randomitemgiver.config.ConfigItemHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class RandomItemGiver extends JavaPlugin {

    private ConfigHandler configHandler;
    private ConfigItemHandler configItemHandler;
    private TaskHandler taskHandler;

    @Override
    public void onDisable() {
        super.onDisable();
        this.getCommand("randomitemgiver").setExecutor(null);
        this.getCommand("randomitemgiver").setTabCompleter(null);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        configHandler = new ConfigHandler(this);
        configHandler.onEnable();
        configItemHandler = new ConfigItemHandler(new ConfigItem(this));
        configItemHandler.onEnable();

        taskHandler = new TaskHandler(this);

        RIGCommandExecutor commandExecutor = new RIGCommandExecutor(this);
        this.getCommand("randomitemgiver").setExecutor(commandExecutor);
        this.getCommand("randomitemgiver").setTabCompleter(new RIGTabCompleteExecutor(commandExecutor));

        taskHandler.begin();
    }

    public ConfigHandler getConfigHandler() {
        return configHandler;
    }

    public ConfigItemHandler getConfigItemHandler() {
        return configItemHandler;
    }

    public TaskHandler getTaskHandler() {
        return taskHandler;
    }
}
