package me.johnstar128.frostyssentials.commands;


import me.johnstar128.frostyssentials.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
        if (!(sender.hasPermission("frostyssentials.kits"))) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            return true;
        }
        if(args.length <= 1) {
            sender.sendMessage(ChatColor.RED + "/kit <save/load/delete/list> <value>");
            return true;
        }
        Player p = (Player) sender;
        String kitToSave = args[1];
        String sl = args[0];
        String playerUUID = p.getUniqueId().toString();
        int indexItems = 0;
        int kitMax = cfg.getConfig().getInt("max-kits");
        ConfigurationSection loadKit = cfg.getConfig().getConfigurationSection("players." + playerUUID + "." + kitToSave + ".slots");
        switch (sl) {
            case "save":
                if(cfg.getConfig().getConfigurationSection("player." + playerUUID) != null) {
                    for (ItemStack i : p.getInventory().getContents()) {
                        if (i != null) {
                            cfg.getConfig().set("players." + playerUUID + "." + kitToSave + ".slots." + indexItems, i);
                        }
                        indexItems++;
                    }
                    p.sendMessage(ChatColor.GREEN + "Inventory saved to kit " + ChatColor.GOLD + kitToSave);
                }else {
                    try {
                        int kitCount = cfg.getConfig().getConfigurationSection("players." + playerUUID).getKeys(false).size();
                        if (kitCount + 1 <= kitMax) {
                            for (ItemStack i : p.getInventory().getContents()) {
                                if (i != null) {
                                    cfg.getConfig().set("players." + playerUUID + "." + kitToSave + ".slots." + indexItems, i);
                                }
                                indexItems++;
                            }
                            p.sendMessage(ChatColor.GREEN + "Inventory saved to kit " + ChatColor.GOLD + kitToSave);
                        } else {
                            p.sendMessage(ChatColor.RED + "Can't save more than " + ChatColor.GOLD + kitMax + ChatColor.RED + " kits!");
                        }
                    }catch (NullPointerException e) {
                        for (ItemStack i : p.getInventory().getContents()) {
                            if (i != null) {
                                cfg.getConfig().set("players." + playerUUID + "." + kitToSave + ".slots." + indexItems, i);
                            }
                            indexItems++;
                        }
                        p.sendMessage(ChatColor.GREEN + "Inventory saved to kit " + ChatColor.GOLD + kitToSave);
                    }
                    cfg.saveConfig();
                    cfg.reloadConfig();
                }
                break;
            case "load":
                if (cfg.getConfig().contains("players." + playerUUID + "." + kitToSave)) {
                    p.getInventory().clear();
                    fillInv(loadKit, playerUUID, p.getPlayer(), kitToSave);
                    p.sendMessage(ChatColor.GREEN + "Loaded kit " + ChatColor.GOLD + kitToSave);
                } else {
                    p.sendMessage(ChatColor.RED + "Couldn't find kit " + ChatColor.GOLD + kitToSave);
                }
                break;
            case "delete":
                if (cfg.getConfig().contains("players." + playerUUID + "." + kitToSave)) {
                    cfg.getConfig().set("players." + playerUUID + "." + kitToSave, null);
                    p.sendMessage(ChatColor.GOLD + "Deleted kit " + ChatColor.RED + kitToSave);
                } else {
                    p.sendMessage(ChatColor.RED + "Couldn't find kit " + ChatColor.GOLD + kitToSave);
                }
                break;
            case "list":
                try{
                    p.sendMessage(ChatColor.GREEN + "Available kits: " + ChatColor.GOLD + cfg.getConfig().getConfigurationSection("players." + playerUUID).getKeys(false));
                }catch (NullPointerException e) {
                    p.sendMessage(ChatColor.RED + "No kits found!");
                }
                break;
            default:
                p.sendMessage(ChatColor.RED + "/kit <save/load/delete/list> <name>");
                break;
        }
        cfg.saveConfig();
        cfg.reloadConfig();
        return true;
    }
    public void fillInv(ConfigurationSection cs, String playerUUID, Player p, String kitToSave){
        try {
            for (String i : cs.getKeys(false)) {
                int itemIndex = parseInt(i);
                ItemStack itemToGive = cfg.getConfig().getItemStack("players." + playerUUID + "." + kitToSave + ".slots." + i);
                p.getInventory().setItem(itemIndex, itemToGive);
            }
        } catch (NullPointerException e) {
            p.sendMessage(ChatColor.RED + "from the method");
            e.getStackTrace();
        }
    }
    private int parseInt (String s){
        int x = 0;
        try {
            x = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            e.getStackTrace();
        }
        return x;
    }
}
