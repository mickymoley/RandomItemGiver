package me.mickymoley.randomitemgiver.commands;

import me.mickymoley.randomitemgiver.RandomItemGiver;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandWorld extends RIGCommand {

    public CommandWorld(RandomItemGiver plugin, String[] aliases) {
        super(plugin, aliases);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String[] args) {
        if (args.length > 3){
            commandSender.sendMessage(ChatColor.RED + "Too many arguments");
            return false;
        }
        if (args.length == 1){
            commandSender.sendMessage(ChatColor.RED + "Too few arguments");
            return false;
        }

        switch (args[1].toLowerCase()){
            case "list" :
                if (args.length > 2){
                    commandSender.sendMessage(ChatColor.RED + "Too many arguments");
                    return false;
                }
                else{
                    List<String> activeWorlds = plugin.getConfigHandler().getActiveWorlds();
                    if (activeWorlds.isEmpty()){
                        commandSender.sendMessage("There are currently no enabled worlds.");
                    }
                    else{
                        commandSender.sendMessage("Enabled worlds: ");
                        for (String activeWorld : activeWorlds){
                            commandSender.sendMessage(" - " + activeWorld);
                        }
                    }
                    return true;
                }
            case "add" : // /rig world add <>
                if (args.length == 3){
                    if (args[2].toLowerCase().equals("all")){
                        commandSender.sendMessage("Adding all worlds...");
                        plugin.getConfigHandler().addAllWorlds();
                        commandSender.sendMessage("All worlds added");
                        plugin.getTaskHandler().begin();
                        return true;
                    }
                    else{
                        plugin.getConfigHandler().addWorld(args[2]);
                        plugin.getTaskHandler().begin();
                        commandSender.sendMessage("World " + args[2] + " was added to the active worlds.");
                        return true;
                    }
                }
                else{
                    if (commandSender instanceof Player){
                        Player player = (Player) commandSender;
                        plugin.getConfigHandler().addWorld(player);
                        plugin.getTaskHandler().begin();
                        commandSender.sendMessage(
                                "World " + player.getWorld().getName() + " was added to the active worlds.");
                        return true;
                    }
                    else{
                        commandSender.sendMessage(ChatColor.RED + "Console needs to specify world");
                        return false;
                    }
                }
            case "remove" :
                if (args.length == 3){
                    if (args[2].toLowerCase().equals("all")){
                        commandSender.sendMessage("Removing all worlds...");
                        plugin.getConfigHandler().removeAllWorlds();
                        commandSender.sendMessage("All worlds removed");
                        return true;
                    }
                    else{
                        plugin.getConfigHandler().removeWorld(args[2]);
                        commandSender.sendMessage("World " + args[2] + " was removed from the active worlds.");
                        return true;
                    }
                }
                else{
                    if (commandSender instanceof Player) {
                        Player player = (Player) commandSender;
                        plugin.getConfigHandler().removeWorld(player);
                        commandSender.sendMessage(
                                "World " + player.getWorld().getName() + " was removed from the active worlds.");
                        return true;
                    }
                    else{
                        commandSender.sendMessage(ChatColor.RED + "Console needs to specify world");
                        return false;
                    }
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
                return ret;
            case 3 :
                switch (args[1].toLowerCase()){
                    case "add" :
                        if ("all".startsWith(args[2].toLowerCase())){
                            ret.add("all");
                        }
                        for (String worldName : plugin.getConfigHandler().getUnactiveWorlds()){
                            if (worldName.toLowerCase().startsWith(args[2].toLowerCase())){
                                ret.add(worldName);
                            }
                        }
                        return ret;
                    case "remove" :
                        if ("all".startsWith(args[2].toLowerCase())){
                            ret.add("all");
                        }
                        for (String worldName : plugin.getConfigHandler().getActiveWorlds()){
                            if (worldName.toLowerCase().startsWith(args[2].toLowerCase())){
                                ret.add(worldName);
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
