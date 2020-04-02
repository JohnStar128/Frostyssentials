package me.johnstar128.frostyssentials.commands;

import me.johnstar128.frostyssentials.Frostyssentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;

public class KittycannonCommand implements CommandExecutor {

    Frostyssentials plugin;

    public KittycannonCommand(Frostyssentials plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command");
            return true;
        }
        if (!(sender.hasPermission("frostyssentials.command.kittycannon"))) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            return true;
        }
        Player p = (Player) sender;
        Ocelot cat = (Ocelot) p.getWorld().spawnEntity(p.getLocation().add(0.0, 1.0, 0.0), EntityType.OCELOT);
        cat.setVelocity(p.getLocation().getDirection().multiply(2));
        cat.setSitting(true);
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                cat.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, cat.getLocation(), 1);
                cat.getWorld().playSound(cat.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
                cat.remove();
            }
        }, 30L);
        return true;
    }
}
