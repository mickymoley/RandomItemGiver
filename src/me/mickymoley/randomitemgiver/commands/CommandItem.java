package me.mickymoley.randomitemgiver.commands;

import me.mickymoley.randomitemgiver.RandomItemGiver;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CommandItem extends RIGCommand {
    private Material idkWhyThisFixesIt = Material.ACACIA_SAPLING; // <-- this line officer
    public CommandItem(RandomItemGiver plugin, String[] aliases) {
        super(plugin, aliases);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String[] args) {
        if (args.length > 3){ // /rig item <> ...
            commandSender.sendMessage(ChatColor.RED + "Too many arguments");
            return false;
        }
        if (args.length == 1){
            commandSender.sendMessage(ChatColor.RED + "Too few arguments");
            return false;
        }

        switch (args[1].toLowerCase()){
            case "list" : // /rig item list
                if (args.length > 2){
                    commandSender.sendMessage(ChatColor.RED + "Too many arguments");
                    return false;
                }
                else{
                    List<String> itemNames = plugin.getConfigItemHandler().getEnabledItems();
                    if (itemNames.isEmpty()){
                        commandSender.sendMessage("There are currently no enabled items.");
                    }
                    else{
                        commandSender.sendMessage("Enabled items: ");
                        for (String itemName : itemNames){
                            commandSender.sendMessage(" - " + itemName);
                        }
                    }
                    return true;
                }
            case "give" :
                if (args.length == 2){ // /rig item give
                    if (commandSender instanceof Player){
                        Player player = (Player) commandSender;
                        if (player.getInventory().getItemInMainHand() != null){
                            plugin.getTaskHandler().giveRandomItem(player);
                            commandSender.sendMessage("You were given a random item.");
                            return true;
                        }
                    }
                    commandSender.sendMessage(ChatColor.RED + "Player not given");
                    return false;
                }
                else{ // /rig item give <player>
                    Player player = Bukkit.getPlayer(args[2]);
                    if (player == null){
                        commandSender.sendMessage(ChatColor.RED + "Invalid player");
                        return false;
                    }
                    else{
                        plugin.getTaskHandler().giveRandomItem(player);
                        commandSender.sendMessage(args[2] + " was given a random item.");
                        return true;
                    }
                }
            case "add" :
                if (args.length == 2){ // /rig item add
                    if (commandSender instanceof Player){
                        Player player = (Player) commandSender;
                        if (!player.getInventory().getItemInMainHand().getType().isAir()){
                            ItemStack itemStack = player.getInventory().getItemInMainHand();
                            String name = itemStack.getType().name();
                            plugin.getConfigItemHandler().addItem(name);
                            commandSender.sendMessage(
                                    "Item " + name + " was added to the possible items.");
                            return true;
                        }
                    }
                    commandSender.sendMessage(ChatColor.RED + "Value not given");
                    return false;
                }
                else{
                    if (args[2].equalsIgnoreCase("all")){
                        commandSender.sendMessage("Adding all items...");
                        plugin.getConfigItemHandler().addAllItems();
                        commandSender.sendMessage("All items added.");
                        return true;
                    }
                    plugin.getConfigItemHandler().addItem(args[2]);
                    commandSender.sendMessage("Item " + args[2] + " was added to the possible items.");
                    return true;
                }
            case "remove" :
                if (args.length == 2){ // /rig item remove
                    if (commandSender instanceof Player){
                        Player player = (Player) commandSender;
                        if (!player.getInventory().getItemInMainHand().getType().isAir()){
                            plugin.getConfigItemHandler().removeItem(
                                    player.getInventory().getItemInMainHand());
                            commandSender.sendMessage(
                                    "Item " + player.getInventory().getItemInMainHand().getType().name()
                                            + " was removed from the possible items.");
                            return true;
                        }
                    }
                    commandSender.sendMessage(ChatColor.RED + "Value not given");
                    return false;
                }
                else{
                    if (args[2].equalsIgnoreCase("all")){
                        commandSender.sendMessage("Removing all items...");
                        plugin.getConfigItemHandler().removeAllItems();
                        commandSender.sendMessage("All items removed.");
                        return true;
                    }
                    plugin.getConfigItemHandler().removeItem(args[2]);
                    commandSender.sendMessage("Item " + args[2] + " was removed from the possible items.");
                    return true;
                }
            default :
                commandSender.sendMessage(ChatColor.RED + "Unrecognised sub-command: " + args[1]);
                return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String[] args) {
        List<String> ret = new ArrayList<String>();
        switch (args.length) {
            case 2 :
                if ("add".startsWith(args[1].toLowerCase())){
                    ret.add("add");
                }
                if ("remove".startsWith(args[1].toLowerCase())){
                    ret.add("remove");
                }
                if ("list".startsWith(args[1].toLowerCase())){
                    ret.add("list");
                }
                if ("give".startsWith(args[1].toLowerCase())){
                    ret.add("give");
                }
                return ret;
            case 3 :
                switch (args[1].toLowerCase()){
                    case "add" :
                        if ("all".startsWith(args[2].toLowerCase())){
                            ret.add("all");
                        }
                        for (Material material : Material.values()){
                            if (material.name().toUpperCase().startsWith(args[2].toUpperCase())){
                                ret.add(material.name().toUpperCase());
                            }
                        }
                        return ret;
                    case "remove" :
                        if ("all".startsWith(args[2].toLowerCase())){
                            ret.add("all");
                        }
                        for (String itemName : plugin.getConfigItemHandler().getEnabledItems()){
                            if (itemName.toLowerCase().startsWith(args[2].toLowerCase())){
                                ret.add(itemName);
                            }
                        }
                        return ret;
                    default :
                        return null;
                }
            default :
                return null;
        }
    }
}
