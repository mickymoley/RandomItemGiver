package me.mickymoley.randomitemgiver.commands;

import me.mickymoley.randomitemgiver.RandomItemGiver;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.Range;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandRate extends RIGCommand {
    public CommandRate(RandomItemGiver plugin, String[] aliases) {
        super(plugin, aliases);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String[] args) {
        if (args.length > 3){ // /rig rate <> ...
            commandSender.sendMessage(ChatColor.RED + "Too many arguments");
            return false;
        }
        if (args.length == 1){
            commandSender.sendMessage(ChatColor.RED + "Too few arguments");
            return false;
        }

        switch (args[1].toLowerCase()){
            case "get" :
                if (args.length > 2){
                    commandSender.sendMessage(ChatColor.RED + "Too many arguments");
                    return false;
                }
                else{
                    commandSender.sendMessage(
                            "There are " + plugin.getConfigHandler().getItemsPerMinute() + " items given per minute.");
                }
                break;
            case "set" :
                if (args.length == 2){
                    commandSender.sendMessage(ChatColor.RED + "Value not given");
                    return false;
                }
                else{
                    Double value;
                    try {
                        value = Double.valueOf(args[2]);
                    } catch (NumberFormatException e) {
                        commandSender.sendMessage(ChatColor.RED + args[2] + " is not a valid number");
                        return false;
                    }
                    if (value <= 0 ){
                        commandSender.sendMessage(ChatColor.RED + "The value has to be greater than 0");
                        return false;
                    }
                    if (value > plugin.getConfigHandler().getMaxItemsPerMinute()){
                        commandSender.sendMessage(ChatColor.RED
                                + "The value has to be less than " + plugin.getConfigHandler().getMaxItemsPerMinute());
                        return false;
                    }
                    plugin.getConfigHandler().setItemsPerMinute(Double.valueOf(args[2]));
                    plugin.getTaskHandler().begin(Double.valueOf(args[2]));
                    commandSender.sendMessage("Items per minute has been set to " + args[2]);
                    return true;
                }
            default :
                commandSender.sendMessage(ChatColor.RED + "Unrecognised sub-command: " + args[1]);
                return false;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String[] args) {
        List<String> ret = new ArrayList<String>();
        switch (args.length){
            case 2 :
                if ("get".startsWith(args[1].toLowerCase())){
                    ret.add("get");
                }
                if ("set".startsWith(args[1].toLowerCase())){
                    ret.add("set");
                }
                return ret;
            default :
                return null;
        }
    }
}
