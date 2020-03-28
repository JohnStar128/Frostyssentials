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
      if(!(sender instanceof Player)) {
        sender.sendMessage("You must be a player to use this command");
        return true;
      }
      if(!(sender.hasPermission("frostyssentials.command.nightvis"))) {
          sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
          return true;
      }
      Player p = (Player) sender;
      if(p.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
          p.removePotionEffect(PotionEffectType.NIGHT_VISION);
          p.sendMessage(ChatColor.GRAY + "Disabled night vision");
          p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_DRINK, 1, 1);
      }else{
          p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 10000000, 1, false, false));
          p.sendMessage(ChatColor.GRAY + "Enabled night vision");
          p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_DRINK, 1, 1);
      }
      return true;
    }
}
