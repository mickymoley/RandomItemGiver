package me.mickymoley.randomitemgiver.commands;

import me.mickymoley.randomitemgiver.RandomItemGiver;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class RIGCommand {

    String[] aliases;
    RandomItemGiver plugin;

    public RIGCommand(RandomItemGiver plugin, String[] aliases){
        this.plugin = plugin;
        this.aliases = aliases;
    }

    public boolean onCommand(CommandSender commandSender, Command command, String[] args){
        return false;
    }

    public List<String> onTabComplete(CommandSender commandSender, Command command, String[] args){
        return null;
    }

    public String[] getAliases(){
        return aliases;
    }
}
