package me.mickymoley.randomitemgiver;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class TaskHandler {

    TaskGiveItems task;
    RandomItemGiver plugin;
    Random random = new Random();
    List<String> validItems;

    public TaskHandler(RandomItemGiver plugin){
        this.plugin = plugin;
    }

    public void begin(){
        begin(this.plugin.getConfigHandler().getItemsPerMinute());
    }
    public void begin(double itemsPerMinute){
        begin((int) (1200 / itemsPerMinute)); // 1200 ticks per minute
    }
    public void begin(int ticksPerItem){
        if (task !=null && !task.isCancelled()){
            task.cancel();
        }
        task = new TaskGiveItems(this, ticksPerItem);
        task.runTaskLater(plugin, ticksPerItem);
    }

    public void giveRandomItem(){
        List<String> worlds = plugin.getConfigHandler().getActiveWorlds();
        Collection<? extends Player> players = plugin.getServer().getOnlinePlayers();
        for (Player player : players){
            for (String world : worlds){
                if (world.toLowerCase().equals(player.getWorld().getName().toLowerCase())){
                    giveRandomItem(player);
                    break;
                }
            }
        }
    }
    public void giveRandomItem(Player player){
        updateItems();
        if (validItems.size() > 0){
            int index = random.nextInt(validItems.size());
            //player.sendMessage(validItems.get(index)); // <-- Debug message
            player.getInventory().addItem(
                    new ItemStack(Material.getMaterial(validItems.get(index)), 1));
        }
    }
    private void updateItems(){
        List<String> enabledItems = plugin.getConfigItemHandler().getEnabledItems();
        if (!enabledItems.equals(validItems)){
            List<String> ret = new ArrayList<String>();
            for (String itemName : enabledItems){
                if (validItem(itemName.toUpperCase())){
                    ret.add(itemName.toUpperCase());
                }
            }
            validItems = ret;
        }
    }
    private boolean validItem(String itemName){
        return Material.getMaterial(itemName) != null;
    }
}
