package me.mickymoley.randomitemgiver.config;

import me.mickymoley.randomitemgiver.RandomItemGiver;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ConfigHandler {
    
    private RandomItemGiver plugin;
    public String ITEMSPERMINUTE = "ItemsPerMinute";
    public String MAXITEMSPERMINUTE = "MaxItemsPerMinute";
    public String ENABLEDWORLDS = "EnabledWorlds";
    
    public ConfigHandler(RandomItemGiver plugin){
        this.plugin = plugin;
    }
    
    public void onEnable(){
        plugin.saveDefaultConfig();
    }

    public void setItemsPerMinute(Double value){
        plugin.getConfig().set(ITEMSPERMINUTE, value);
        plugin.saveConfig();
    }
    public double getItemsPerMinute(){
        plugin.reloadConfig();
        return plugin.getConfig().getDouble(ITEMSPERMINUTE);
    }

    public boolean addWorld(Player player){
        return addWorld(player.getWorld().getName());
    }
    public boolean addWorld(String world){
        return addWorld(Collections.singletonList(world));
    }
    public boolean addWorld(List<String> worlds){
        List<String> enabledWorlds = getEnabledWorlds();
        List<String> newEnabledWorlds = new ArrayList<>();
        for (String world : worlds){
            if (!enabledWorlds.contains(world)){
                newEnabledWorlds.add(world);
            }
        }
        if (newEnabledWorlds.size() != 0){
            enabledWorlds.addAll(newEnabledWorlds);
            plugin.getConfig().set(ENABLEDWORLDS, enabledWorlds);
            plugin.saveConfig();
            return true;
        }
        return false;
    }

    public boolean removeWorld(Player player){
        return this.removeWorld(player.getWorld().getName());
    }
    public boolean removeWorld(String world){
        return removeWorld(Collections.singletonList(world));
    }
    public boolean removeWorld(List<String> worlds){
        List<String> enabledWorlds = getEnabledWorlds();
        if (enabledWorlds.removeAll(worlds)) {
            plugin.getConfig().set(ENABLEDWORLDS, enabledWorlds);
            plugin.saveConfig();
            return true;
        }
        return false;
    }

    public boolean removeAllWorlds(){
        plugin.getConfig().set(ENABLEDWORLDS, null);
        plugin.saveConfig();
        return true;
    }
    public boolean addAllWorlds(){
        return addWorld(this.getBukkitWorldNames());
    }

    public List<String> getEnabledWorlds() {
        plugin.reloadConfig();
        return plugin.getConfig().getStringList(ENABLEDWORLDS);
    }
    public List<String> getDisabledWorlds() {
        List<String> worlds = this.getBukkitWorldNames();
        worlds.removeAll(getEnabledWorlds());
        return worlds;
    }

    private List<String> getBukkitWorldNames() {
        List<World> worlds = Bukkit.getWorlds();
        List<String> ret = new ArrayList<>(worlds.size());
        for (World world : worlds){
            ret.add(world.getName());
        }
        return ret;
    }
    public Double getMaxItemsPerMinute() {
        plugin.reloadConfig();
        return plugin.getConfig().getDouble(MAXITEMSPERMINUTE);
    }
}
