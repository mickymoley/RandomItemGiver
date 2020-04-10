package me.mickymoley.randomitemgiver.config;

import me.mickymoley.randomitemgiver.RandomItemGiver;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ConfigItemHandler {
    private ConfigItem configItem;
    private boolean skipConfigUpdate = false;
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

    public boolean addAllItems() {
        skipConfigUpdate = true;
        for (Material material : Material.values()){
            addItem(material);
        }
        skipConfigUpdate = false;
        return true;
    }
    public boolean addItem(ItemStack itemStack){
        return addItem(itemStack.getType());
    }
    public boolean addItem(Material material) {
        return addItem(material.name());
    }
    public boolean addItem(String name){
        if (!skipConfigUpdate){
            configItem.reloadItemConfig();
        }
        
        if (name.startsWith("LEGACY_")){
            name = name.substring(7);
        }
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
        skipConfigUpdate = true;
        for (String itemName : getEnabledItems()){
            removeItem(itemName);
        }
        skipConfigUpdate = false;
        return true;
    }
    public boolean removeItem(ItemStack itemStack){
        return removeItem(itemStack.getType());
    }
    public boolean removeItem(Material material) {
        return removeItem(material.name());
    }
    public boolean removeItem(String name){
        if (!skipConfigUpdate){
            configItem.reloadItemConfig();
        }
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
