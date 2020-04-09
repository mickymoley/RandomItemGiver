package me.mickymoley.randomitemgiver.commands;

import me.mickymoley.randomitemgiver.RandomItemGiver;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class RIGCommandExecutor implements CommandExecutor {

    public static String LABEL = "randomitemgiver";
    public static String PERMISSION = "randomitemgiver.configure";
    private RIGCommand[] commands;

    public RIGCommandExecutor(RandomItemGiver plugin){
        CommandWorld commandWorld = new CommandWorld(plugin, new String[] {"world"});
        CommandRate commandRate = new CommandRate(plugin, new String[] {"rate"});
        CommandItem commandItem = new CommandItem(plugin, new String[] {"item"});
        commands = new RIGCommand[]{commandWorld, commandRate, commandItem};
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if (!commandSender.hasPermission(PERMISSION)){
            commandSender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return false;
        }
        if (args.length == 0){
            commandSender.sendMessage(ChatColor.RED + "Invalid usage");
            return false;
        }

        for (RIGCommand rigCommand : commands){
            for (String alias : rigCommand.getAliases()){
                if (args[0].toLowerCase().equals(alias)){
                    return rigCommand.onCommand(commandSender, command, args);
                }
            }
        }

        commandSender.sendMessage(ChatColor.RED + "Unrecognised sub-command: " + args[0]);
        return false;
    }

    public RIGCommand[] getCommands() {
        return commands;
    }
}
