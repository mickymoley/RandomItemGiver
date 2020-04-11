package me.mickymoley.randomitemgiver.config;

import me.mickymoley.randomitemgiver.RandomItemGiver;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.ArrayList;
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
        return this.addWorld(player.getWorld().getName());
    }
    public boolean addWorld(String world){
        List<String> ret = new ArrayList<>();
        ret.add(world);
        return addWorld(ret);
    }
    public boolean addWorld(List<String> worlds){
        plugin.reloadConfig();
        List<String> enabledWorlds = plugin.getConfig().getStringList(ENABLEDWORLDS);
        List<String> newEnabledWorlds = new ArrayList<>();
        boolean altered = false;
        for (String world : worlds){
            if (!enabledWorlds.contains(world)){
                newEnabledWorlds.add(world);
                altered = true;
            }
        }
        if (altered){
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
        List<String> ret = new ArrayList<>();
        ret.add(world);
        return removeWorld(ret);
    }
    public boolean removeWorld(List<String> worlds){
        plugin.reloadConfig();
        List<String> enabledWorlds = plugin.getConfig().getStringList(ENABLEDWORLDS);
        boolean altered = false;
        for (String world : worlds){
            if (enabledWorlds.contains(world)){
                enabledWorlds.remove(world);
                altered = true;
            }
        }
        if (altered) {
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
        plugin.reloadConfig();
        List<String> activeWorlds = plugin.getConfig().getStringList(ENABLEDWORLDS);
        List<String> worldNames = this.getBukkitWorldNames();
        worldNames.removeAll(activeWorlds);
        return worldNames;
    }

    private List<String> getBukkitWorldNames(){
        List<String> ret = new ArrayList<>();
        for (World world : Bukkit.getWorlds()){
            ret.add(world.getName());
        }
        return ret;
    }
    public Double getMaxItemsPerMinute() {
        plugin.reloadConfig();
        return plugin.getConfig().getDouble(MAXITEMSPERMINUTE);
    }
}
