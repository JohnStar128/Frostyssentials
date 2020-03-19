package me.johnstar128.frostyssentials;

import me.johnstar128.frostyssentials.commands.EnderChestCommand;
import me.johnstar128.frostyssentials.commands.NightVisCommand;
import me.johnstar128.frostyssentials.commands.SpeedCommand;
import me.johnstar128.frostyssentials.commands.WorkbenchCommand;
import me.johnstar128.frostyssentials.events.MessageFormatting;
import org.bukkit.plugin.java.JavaPlugin;

public final class Frostyssentials extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new MessageFormatting(), this);
        getCommand("speed").setExecutor(new SpeedCommand());
        getCommand("nightvis").setExecutor(new NightVisCommand());
        getCommand("enderchest").setExecutor(new EnderChestCommand());
        getCommand("workbench").setExecutor(new WorkbenchCommand());
    }
}
