package me.johnstar128.frostyssentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Set;
import java.util.StringJoiner;

import static me.johnstar128.frostyssentials.Frostyssentials.printUsage;

public class SignEditCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command.");
            return true;
        }

        if (!(sender.hasPermission("frostyssentials.command.signedit"))) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        if (args.length < 2) {
            printUsage(sender, label, ChatColor.RED + "Invalid arguments!", "<line> <message>");
            return true;
        }

        Player p = (Player) sender;
        int signLine = 0;
        Block lookAtSign = p.getTargetBlock((Set<Material>) null, 5);
        if (!(lookAtSign.getType() == Material.SIGN_POST || lookAtSign.getType() == Material.WALL_SIGN)) {
            printUsage(sender, label, ChatColor.RED + "You must look at a sign!", "<line> <message>");
        } else {
            Sign sign = (Sign) lookAtSign.getState();
            try {
                signLine = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                printUsage(sender, label, ChatColor.GOLD + args[0] + ChatColor.RED + " isn't a valid sign line!", "<line> <message>");
            }

            if (signLine < 1 || signLine > 4) {
                printUsage(sender, label, ChatColor.RED + "Sign line must be between 1 and 4!", "<line> <message>");
                return true;
            } else {
                // Borrowed in the learning process <3
                StringJoiner joiner = new StringJoiner(" ");
                Arrays.stream(args).skip(1).forEach(joiner::add);
                String joined = joiner.toString();
                int quotes = joined.indexOf("\"", 1);
                if (!joined.startsWith("\"") || quotes == -1) {
                    printUsage(sender, label, ChatColor.RED + "Text must be in quotations", "<line> <message>");
                    return true;
                }
                String output = joined.substring(1, quotes);
                sign.setLine(signLine - 1, output);
                sign.update();
                p.sendMessage(ChatColor.GREEN + "Line " + ChatColor.GOLD + signLine + ChatColor.GREEN + " updated to " + ChatColor.GOLD + output);
            }
        }
        return true;
    }
}
