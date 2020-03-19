package me.johnstar128.frostyssentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NightVisCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(p.hasPermission("frostyssentials.command.nightvis")) {
                if(p.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                    p.removePotionEffect(PotionEffectType.NIGHT_VISION);
                    p.sendMessage(ChatColor.GRAY + "Night vision disabled");
                    p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_DRINK, 1, 1);
                }else if(!p.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000, 1, true, false));
                    p.sendMessage(ChatColor.GRAY + "Night vision enabled");
                    p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_DRINK, 1, 1);
                }
            }
        }
        return true;
    }
}
