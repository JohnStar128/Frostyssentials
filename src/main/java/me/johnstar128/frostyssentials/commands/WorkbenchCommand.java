package me.johnstar128.frostyssentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorkbenchCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(p.hasPermission("frostyssentials.command.workbench")) {
                p.openWorkbench(null, true);
                p.sendMessage(ChatColor.GRAY + "Opening workbench");
            }
        }
        return true;
    }
}
