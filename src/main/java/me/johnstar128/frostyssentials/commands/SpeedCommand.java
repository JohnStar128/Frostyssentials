package me.johnstar128.frostyssentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpeedCommand implements CommandExecutor {

    public static boolean isInt(String s, CommandSender sender) {
        try {
            Integer.parseInt(s);
        }catch (NumberFormatException nfe) {
            sender.sendMessage(ChatColor.RED + "Please provide a valid speed value <1-10>");
            return false;
        }
        return true;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(p.hasPermission("frostyssentials.command.speed")) {
                if(args.length == 0) {
                    if(p.isFlying()) {
                        p.sendMessage(ChatColor.GRAY + "Current speed is: " + ChatColor.DARK_AQUA + p.getFlySpeed());
                    }else if(!p.isFlying()) {
                        p.sendMessage(ChatColor.GRAY + "Current speed is: " + ChatColor.DARK_AQUA + p.getFlySpeed());
                    }
                }else if(isInt(args[0], sender)) {
                    int fl = Integer.parseInt(args[0]);
                    if(p.isFlying()) {
                        p.setFlySpeed(fl / (float) 10.0);
                        p.sendMessage(ChatColor.GRAY + "Speed set to: " + ChatColor.DARK_AQUA + args[0]);
                    }else if(!p.isFlying()) {
                        p.setWalkSpeed(fl / (float) 10.0);
                        p.sendMessage(ChatColor.GRAY + "Speed set to: " + ChatColor.DARK_AQUA + args[0]);
                    }
                }
            }
        }
        return true;
    }
}
