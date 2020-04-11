package me.mickymoley.randomitemgiver.config;

import me.mickymoley.randomitemgiver.RandomItemGiver;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ConfigItemHandler {
    private ConfigItem configItem;
    public String ENABLED_ITEMS = "EnabledItems";
    private Material idkWhyThisFixesIt = Material.ACACIA_SAPLING; // <-- this line officer

    public ConfigItemHandler(ConfigItem configItem){
        this.configItem = configItem;
    }

    public void onEnable(){
        configItem.saveDefaultConfig();
    }

    public List<String> getEnabledItems(){
        configItem.reloadItemConfig();
        return configItem.getItemConfig().getStringList(ENABLED_ITEMS);
    }

    public boolean addItem(ItemStack itemStack){
        return addItem(itemStack.getType());
    }
    public boolean addItem(Material material) {
        return addItem(material.name());
    }
    public boolean addItem(String name){
        List<String> ret =  new ArrayList<>();
        ret.add(name);
        return addItem(ret);
    }
    public boolean addItem(List<String> names){
        configItem.reloadItemConfig();
        List<String> enabledItems = getEnabledItems();
        List<String> newEnabledItems = new ArrayList<>();
        boolean altered = false;
        for (String name : names){
            if (name.startsWith("LEGACY_")){
                name = name.substring(7);
            }
            if (!enabledItems.contains(name)){
                newEnabledItems.add(name);
                altered = true;
            }
        }
        if (altered) {
            enabledItems.addAll(newEnabledItems);
            configItem.getItemConfig().set(ENABLED_ITEMS, enabledItems);
            configItem.saveItemConfig();
            return true;
        }
        return false;
    }

    public boolean removeItem(ItemStack itemStack){
        return removeItem(itemStack.getType());
    }
    public boolean removeItem(Material material) {
        return removeItem(material.name());
    }
    public boolean removeItem(String name){
        List<String> ret =  new ArrayList<>();
        ret.add(name);
        return removeItem(ret);
    }
    public boolean removeItem(List<String> names){
        configItem.reloadItemConfig();
        List<String> enabledItems = getEnabledItems();
        boolean altered = false;
        for (String name : names) {
            if (enabledItems.contains(name)){
                enabledItems.remove(name);
                altered = true;
            }
        }
        if (altered){
            configItem.getItemConfig().set(ENABLED_ITEMS, enabledItems);
            configItem.saveItemConfig();
            return true;
        }
        return false;
    }

    public boolean addAllItems() {
        List<String> names = new ArrayList<>();
        for (Material material : Material.values()){
            names.add(material.name());
        }
        return addItem(names);
    }
    public boolean removeAllItems() {
        return removeItem(getEnabledItems());
    }
}
