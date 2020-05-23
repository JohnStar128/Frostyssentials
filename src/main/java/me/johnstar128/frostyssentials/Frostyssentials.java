package me.johnstar128.frostyssentials;

import me.johnstar128.frostyssentials.commands.*;
import me.johnstar128.frostyssentials.events.MessageFormatting;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class Frostyssentials extends JavaPlugin {

    private ConfigManager cfg;

    @Override
    public void onEnable() {
        // Plugin startup logic
        registerEvents();
        registerKitsConfig();
        registerCommands();
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new MessageFormatting(), this);
    }
    public void registerKitsConfig() {
        cfg = new ConfigManager();
        cfg.setup();
        cfg.getConfig().options().copyDefaults(true);
        cfg.saveConfig();
    }

    public void registerCommands() {
        getCommand("kit").setExecutor(new KitCommand(cfg));
        getCommand("workbench").setExecutor(new WorkbenchCommand());
        getCommand("speed").setExecutor(new SpeedCommand());
        getCommand("nightvis").setExecutor(new NightVisCommand());
        getCommand("enderchest").setExecutor(new EnderChestCommand());
        getCommand("kittycannon").setExecutor(new KittycannonCommand(this));
        getCommand("map").setExecutor(new MapCommand());
        getCommand("skull").setExecutor(new SkullCommand());
        getCommand("signedit").setExecutor(new SignEditCommand());
    }

    public static void printUsage(CommandSender sender, String label, String problem, String context) {
        if (problem == null) {
            problem = "";
        } else {
            problem = problem + '\n';
        }

        if (context == null) {
            context = "";
        }
        sender.sendMessage(problem + ChatColor.RED + "Usage: /" + label + " " + context);
    }
}
