package me.mickymoley.randomitemgiver.config;

import me.mickymoley.randomitemgiver.RandomItemGiver;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ConfigItemHandler {
    private ConfigItem configItem;
    public String ENABLED_ITEMS = "EnabledItems";

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

    public boolean addAllItems() {
        for (Material material : Material.values()){
            addItem(material);
        }
        return true;
    }
    public boolean addItem(ItemStack itemStack){
        return addItem(itemStack.getType());
    }
    public boolean addItem(Material material) {
        return addItem(material.name());
    }
    public boolean addItem(String name){
        configItem.reloadItemConfig();
        List<String> enabledItems = getEnabledItems();
        if (enabledItems.contains(name)){
            return false;
        }
        else{
            enabledItems.add(name);
            configItem.getItemConfig().set(ENABLED_ITEMS, enabledItems);
            configItem.saveItemConfig();
            return true;
        }
    }

    public boolean removeAllItems() {
        for (String itemName : getEnabledItems()){
            removeItem(itemName);
        }
        return true;
    }
    public boolean removeItem(ItemStack itemStack){
        return removeItem(itemStack.getType());
    }
    public boolean removeItem(Material material) {
        return removeItem(material.name());
    }
    public boolean removeItem(String name){
        configItem.reloadItemConfig();
        List<String> enabledItems = getEnabledItems();
        if (!enabledItems.contains(name)){
            return false;
        }
        else{
            enabledItems.remove(name);
            configItem.getItemConfig().set(ENABLED_ITEMS, enabledItems);
            configItem.saveItemConfig();
            return true;
        }
    }

}
