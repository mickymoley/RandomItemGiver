package me.mickymoley.randomitemgiver.commands;

import me.mickymoley.randomitemgiver.RandomItemGiver;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class RIGTabCompleteExecutor implements TabCompleter {

    private RIGCommandExecutor commandExecutor;

    public RIGTabCompleteExecutor(RIGCommandExecutor commandExecutor){
        this.commandExecutor = commandExecutor;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] strings) {

        if (!commandSender.hasPermission(commandExecutor.PERMISSION)){
            return null;
        }
        List<String> ret = new ArrayList<String>();
        switch (strings.length){
            case 1 :
                for (RIGCommand rigCommand : commandExecutor.getCommands()){
                    if (rigCommand.getAliases()[0].startsWith(strings[0].toLowerCase())){
                        ret.add(rigCommand.getAliases()[0]);
                    }
                }
                break;
            default :
                for (RIGCommand rigCommand : commandExecutor.getCommands()){
                    if (rigCommand.getAliases()[0].equals(strings[0].toLowerCase())){
                        ret = rigCommand.onTabComplete(commandSender, command, strings);
                        break;
                    }
                }
                break;
        }
        if (ret == null || ret.isEmpty()){
            return null;
        }
        else{
            return ret;
        }
    }
}
