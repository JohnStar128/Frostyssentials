package me.johnstar128.frostyssentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorkbenchCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command");
            return true;
        }
        if(!(sender.hasPermission("frostyssentials.command.nightvis"))) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            return true;
        }
            Player p = (Player) sender;
                p.openWorkbench(null, true);
                p.sendMessage(ChatColor.GRAY + "Opening workbench");
        return true;
    }
}
