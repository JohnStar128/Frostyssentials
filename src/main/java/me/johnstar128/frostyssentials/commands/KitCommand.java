package me.johnstar128.frostyssentials.commands;


import me.johnstar128.frostyssentials.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static me.johnstar128.frostyssentials.Frostyssentials.printUsage;

public class KitCommand implements CommandExecutor {

    private ConfigManager cfg;

    public KitCommand(ConfigManager cfg) {
        this.cfg = cfg;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command");
            return true;
        }
        if (!(sender.hasPermission("frostyssentials.command.kits"))) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            return true;
        }
        if (args.length < 1 || args.length > 3) {
            printUsage(sender, label, null,"<save/load/list/delete> <value>");
            return true;
        }

        Player p = (Player) sender;
        String sl = args[0];
        String playerUUID = p.getUniqueId().toString();
        int indexItems = 0;
        int kitMax = cfg.getConfig().getInt("max-kits");
        switch (sl) {
            case "save":
                try {
                    if (cfg.getConfig().getConfigurationSection("player." + playerUUID) != null) {
                        for (ItemStack i : p.getInventory().getContents()) {
                            if (i != null) {
                                cfg.getConfig().set("players." + playerUUID + "." + args[1] + ".slots." + indexItems, i);
                            }
                            indexItems++;
                        }
                        p.sendMessage(ChatColor.GREEN + "Inventory saved to kit " + ChatColor.GOLD + args[1]);
                    } else {
                        try {
                            int kitCount = cfg.getConfig().getConfigurationSection("players." + playerUUID).getKeys(false).size();
                            if (kitCount + 1 <= kitMax) {
                                for (ItemStack i : p.getInventory().getContents()) {
                                    if (i != null) {
                                        cfg.getConfig().set("players." + playerUUID + "." + args[1] + ".slots." + indexItems, i);
                                    }
                                    indexItems++;
                                }
                                p.sendMessage(ChatColor.GREEN + "Inventory saved to kit " + ChatColor.GOLD + args[1]);
                            } else {
                                p.sendMessage(ChatColor.RED + "Can't save more than " + ChatColor.GOLD + kitMax + ChatColor.RED + " kits!");
                            }
                        } catch (NullPointerException e) {
                            for (ItemStack i : p.getInventory().getContents()) {
                                if (i != null) {
                                    cfg.getConfig().set("players." + playerUUID + "." + args[1] + ".slots." + indexItems, i);
                                }
                                indexItems++;
                            }
                            p.sendMessage(ChatColor.GREEN + "Inventory saved to kit " + ChatColor.GOLD + args[1]);
                        }
                        cfg.saveConfig();
                        cfg.reloadConfig();
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                     printUsage(sender, label, null,"<save/load/list/delete> <value>");
                }
                break;

            case "load":
                try {
                    if (cfg.getConfig().contains("players." + playerUUID + "." + args[1])) {
                        p.getInventory().clear();
                        fillInv(cfg.getConfig().getConfigurationSection("players." + playerUUID + "." + args[1] + ".slots"), playerUUID, p.getPlayer(), args[1]);
                        p.sendMessage(ChatColor.GREEN + "Loaded kit " + ChatColor.GOLD + args[1]);
                    } else {
                        p.sendMessage(ChatColor.RED + "Couldn't find kit " + ChatColor.GOLD + args[1]);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                     printUsage(sender, label, null,"<save/load/list/delete> <value>");
                }
                break;

            case "delete":
                try {
                    if (cfg.getConfig().contains("players." + playerUUID + "." + args[1])) {
                        cfg.getConfig().set("players." + playerUUID + "." + args[1], null);
                        p.sendMessage(ChatColor.GOLD + "Deleted kit " + ChatColor.RED + args[1]);
                    } else {
                        p.sendMessage(ChatColor.RED + "Couldn't find kit " + ChatColor.GOLD + args[1]);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                     printUsage(sender, label, null,"<save/load/list/delete> <value>");
                }
                break;

            case "list":
                if (args.length <= 1) {
                    try {
                        if (cfg.getConfig().getConfigurationSection("players." + playerUUID).getKeys(false).isEmpty()) {
                            p.sendMessage(ChatColor.RED + "No available kits to list");
                        } else {
                            p.sendMessage(ChatColor.GREEN + "Available kits: " + ChatColor.GOLD + cfg.getConfig().getConfigurationSection("players." + playerUUID).getKeys(false));
                        }
                    } catch (NullPointerException e) {
                        p.sendMessage(ChatColor.RED + "No kits found!");
                    }
                } else if (cfg.getConfig().contains("players." + playerUUID + "." + args[1])){
                    p.sendMessage(ChatColor.GOLD + "Contents of " + ChatColor.RED + args[1]);
                   kitGUI(cfg.getConfig().getConfigurationSection("players." + playerUUID + "." + args[1] + ".slots."), playerUUID, p, args[1]);
                } else {
                    p.sendMessage(ChatColor.RED + "Couldn't find kit " + ChatColor.GOLD + args[1]);
                }
                break;

            case "reload":
                cfg.saveConfig();
                cfg.reloadConfig();
                p.sendMessage(ChatColor.GREEN + "Config reloaded!");
                break;

            default:
                printUsage(sender, label, null, "<save/load/list/delete> <value>");
                break;
        }
        cfg.saveConfig();
        cfg.reloadConfig();
        return true;
    }

    public void fillInv(ConfigurationSection cs, String playerUUID, Player p, String kitToSave){
        try {
            for (String i : cs.getKeys(false)) {
                int itemIndex =Integer.parseInt(i);
                ItemStack itemToGive = cfg.getConfig().getItemStack("players." + playerUUID + "." + kitToSave + ".slots." + i);
                p.getInventory().setItem(itemIndex, itemToGive);
            }
        } catch (NullPointerException e) {
            p.sendMessage(ChatColor.RED + "from the method");
            e.getStackTrace();
        }
    }

    public void kitGUI(ConfigurationSection cs, String playerUUID, Player p, String kitToSave) {
        Inventory kitList = Bukkit.createInventory(p, 45, ChatColor.GOLD + kitToSave);
        for (String i : cs.getKeys(false)) {
            int currentSlot = Integer.parseInt(i);
            ItemStack item = new ItemStack(cfg.getConfig().getItemStack("players." + playerUUID + "." + kitToSave + ".slots." + currentSlot));
            kitList.setItem(currentSlot, item);
        }
        p.openInventory(kitList);
    }
}
