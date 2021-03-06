package me.johnstar128.frostyssentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.johnstar128.frostyssentials.Frostyssentials.printUsage;

public class EnderChestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command");
            return true;
        }

        if (!(sender.hasPermission("frostyssentials.command.enderchest"))) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            return true;
        }

        if (args.length > 0) {
            printUsage(sender, label, null, null);
            return true;
        }

        Player p = (Player) sender;
        p.openInventory(p.getEnderChest());
        p.sendMessage(ChatColor.GRAY + "Opening your ender chest");
        return true;

    }

}
