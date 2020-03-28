package me.johnstar128.frostyssentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpeedCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command.");
            return true;
        }
        if(!(sender.hasPermission("frostyssentials.command.speed"))) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            return true;
        }
        if(args.length == 0) {
            sender.sendMessage(ChatColor.RED + "/speed <value>");
            return true;
        }
        Player p = (Player) sender;
        int speed = Integer.parseInt(args[0]);
        try {
            Integer.parseInt(args[0]);
            if (!p.isFlying()) {
                p.setWalkSpeed(speed / (float) 10.0);
            } else {
                p.setFlySpeed(speed / (float) 10.0);
            }
            p.sendMessage(ChatColor.GRAY + "Speed updated to: " + ChatColor.DARK_AQUA + speed);
        }catch (IllegalArgumentException e) {
            sender.sendMessage(ChatColor.RED + "Invalid speed value! Must be between -10 and 10");
            return true;
        }
        return true;
    }
}
