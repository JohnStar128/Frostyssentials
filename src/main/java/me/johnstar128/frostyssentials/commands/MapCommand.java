package me.johnstar128.frostyssentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;

import static me.johnstar128.frostyssentials.Frostyssentials.printUsage;

public class MapCommand implements CommandExecutor {

    private ItemStack makeMap(short mapId) {
        ItemStack map = new ItemStack(Material.MAP, 1, mapId);
        MapMeta mapMeta = (MapMeta) map.getItemMeta();
        mapMeta.setScaling(false);
        map.setItemMeta(mapMeta);
        return map;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command.");
            return true;
        }

        if (!(sender.hasPermission("frostyssentials.command.map"))) {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command.");
            return true;
        }

        if (args.length != 1) {
            printUsage(sender, label, null, "<map ID>");
            return true;
        }

        try {
            short mapId = Short.parseShort(args[0]);
            ((Player) sender).getInventory().addItem(makeMap(mapId));
            sender.sendMessage(ChatColor.GREEN + "Gave map " + ChatColor.GOLD + mapId);
            return true;
        } catch (NumberFormatException e) {
            printUsage(sender, label, ChatColor.RED + "Map IDs must be a positive integer!", "<map ID>");
        }

        return true;

    }
}
