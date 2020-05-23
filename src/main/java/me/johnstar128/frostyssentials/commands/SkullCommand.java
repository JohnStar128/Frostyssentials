package me.johnstar128.frostyssentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import static me.johnstar128.frostyssentials.Frostyssentials.printUsage;

public class SkullCommand implements CommandExecutor {

    private ItemStack setSkull(String owner) {

        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte)3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwner(owner);
        skull.setItemMeta(skullMeta);
        return skull;

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command.");
            return true;
        }

        if (!(sender.hasPermission("frostyssentials.command.skull"))) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        if (args.length != 1) {
            printUsage(sender, label, null, "<username>");
            return true;
        }

        Player p = (Player) sender;
        String skullOwner = args[0];
        try {
            if (skullOwner.length() <= 16) {
                p.getInventory().setItem(p.getInventory().firstEmpty(), setSkull(skullOwner));
                sender.sendMessage(ChatColor.GREEN + "Gave the skull of " + ChatColor.GOLD + skullOwner);
                return true;
            } else {
                sender.sendMessage(ChatColor.RED + "Name is too long!");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            p.sendMessage(ChatColor.RED + "Couldn't give " + ChatColor.GOLD + skullOwner + ChatColor.RED + "'s skull because your inventory is full!");
        }

        return true;
    }

}
