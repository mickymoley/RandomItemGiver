package me.mickymoley.randomitemgiver.config;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
        return addItem(Collections.singletonList(name));
    }
    public boolean addItem(List<String> names){
        List<String> enabledItems = getEnabledItems();
        List<String> newEnabledItems = new ArrayList<>();
        for (String name : names){
            if (name.startsWith("LEGACY_")){
                name = name.substring(7);
            }
            if (!enabledItems.contains(name)){
                newEnabledItems.add(name);
            }
        }
        if (newEnabledItems.size() != 0) {
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
        return removeItem(Collections.singletonList(name));
    }
    public boolean removeItem(List<String> names){
        List<String> enabledItems = getEnabledItems();
        if (enabledItems.removeAll(names)){
            configItem.getItemConfig().set(ENABLED_ITEMS, enabledItems);
            configItem.saveItemConfig();
            return true;
        }
        return false;
    }

    public boolean addAllItems() {
        Material[] materials = Material.values();
        List<String> names = new ArrayList<>(materials.length);
        for (Material material : materials){
            names.add(material.name());
        }
        return addItem(names);
    }
    public boolean removeAllItems() {
        return removeItem(getEnabledItems());
    }
}
