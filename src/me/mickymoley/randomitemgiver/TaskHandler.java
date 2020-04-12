package me.mickymoley.randomitemgiver;

import com.sun.istack.internal.NotNull;
import org.apache.commons.lang.NullArgumentException;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.logging.Level;

public class TaskHandler {

    private TaskGiveItems task;
    private RandomItemGiver plugin;
    private Random random = new Random();
    private List<String> validItems;
    private Material idkWhyThisFixesIt = Material.ACACIA_SAPLING; // <-- this line officer

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
        List<String> worlds = plugin.getConfigHandler().getEnabledWorlds();
        List<Player> givePlayers = new ArrayList<>();
        for (Player player : plugin.getServer().getOnlinePlayers()){
            if (worlds.contains(player.getWorld().getName())) {
                givePlayers.add(player);
            }
        }
        giveRandomItem(givePlayers);
    }

    public void giveRandomItem(Player player){
        giveRandomItem(Collections.singletonList(player));
    }
    public void giveRandomItem(List<Player> players){
        updateItems();
        if (validItems.size() > 0){
            for (Player player : players){
                player.getInventory().addItem(
                        new ItemStack(Material.getMaterial(validItems.get(random.nextInt(validItems.size()))),
                                1));
            }
        }
    }
    private void updateItems(){
        List<String> enabledItems = plugin.getConfigItemHandler().getEnabledItems();
        if (!enabledItems.equals(validItems)){
            List<String> ret = new ArrayList<>();
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
