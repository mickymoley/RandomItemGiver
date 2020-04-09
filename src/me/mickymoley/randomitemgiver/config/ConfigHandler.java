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
    public String ACTIVEWORLDS = "ActiveWorlds";
    
    public ConfigHandler(RandomItemGiver plugin){
        this.plugin = plugin;
    }
    
    public void onEnable(){
        plugin.saveDefaultConfig();
    }

    public double getItemsPerMinute(){
        plugin.reloadConfig();
        return plugin.getConfig().getDouble(ITEMSPERMINUTE);
    }
    public void setItemsPerMinute(Double value){
        plugin.reloadConfig();
        plugin.getConfig().set(ITEMSPERMINUTE, value);
        plugin.saveConfig();
    }

    public boolean addWorld(Player player){
        return this.addWorld(player.getWorld().getName());
    }
    public boolean addWorld(String world){
        plugin.reloadConfig();
        List<String> activeWorlds = plugin.getConfig().getStringList(ACTIVEWORLDS);
        if (activeWorlds.contains(world)){
            return false;
        }
        else{
            activeWorlds.add(world);
            plugin.getConfig().set(ACTIVEWORLDS, activeWorlds);
            plugin.saveConfig();
            return true;
        }
    }
    public boolean removeWorld(Player player){
        return this.removeWorld(player.getWorld().getName());
    }
    public boolean removeWorld(String world){
        plugin.reloadConfig();
        List<String> activeWorlds = plugin.getConfig().getStringList(ACTIVEWORLDS);
        if (!activeWorlds.contains(world)){
            return false;
        }
        else{
            activeWorlds.remove(world);
            plugin.getConfig().set(ACTIVEWORLDS, activeWorlds);
            plugin.saveConfig();
            return true;
        }
    }
    public boolean removeAllWorlds(){
        plugin.reloadConfig();
        plugin.getConfig().set(ACTIVEWORLDS, null);
        plugin.saveConfig();
        return true;
    }
    public boolean addAllWorlds(){
        plugin.reloadConfig();
        List<String> worldNames = this.getBukkitWorldNames();
        for (String worldName : worldNames){
            this.addWorld(worldName);
        }
        return true;
    }

    public List<String> getActiveWorlds() {
        plugin.reloadConfig();
        return plugin.getConfig().getStringList(ACTIVEWORLDS);
    }
    public List<String> getUnactiveWorlds() {
        plugin.reloadConfig();
        List<String> activeWorlds = plugin.getConfig().getStringList(ACTIVEWORLDS);
        List<String> worldNames = this.getBukkitWorldNames();
        worldNames.removeAll(activeWorlds);
        return worldNames;
    }

    private List<String> getBukkitWorldNames(){
        List<String> ret = new ArrayList<String>();
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
