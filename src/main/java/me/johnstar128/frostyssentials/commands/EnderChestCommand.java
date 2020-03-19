package me.johnstar128.frostyssentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnderChestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if(sender instanceof Player) {
                Player p = (Player) sender;
                if(p.hasPermission("frostyssentials.command.enderchest")) {
                    p.openInventory(p.getEnderChest());
                    p.sendMessage(ChatColor.GRAY + "Opening your ender chest");
                }
            }
        return true;
    }
}
