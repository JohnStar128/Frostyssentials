package me.johnstar128.frostyssentials;


import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private static File f;
    private Frostyssentials plugin = Frostyssentials.getPlugin(Frostyssentials.class);
    private static FileConfiguration fc;

    public void setup() {
        f = new File(plugin.getDataFolder(), "kits.yml");
        if(!(f.exists())) {
            try {
                plugin.saveResource("kits.yml", false);
            }catch (Exception e) {
                System.out.println("Couldn't create kits.yml, it most likely already exists");
            }
        }
        fc = YamlConfiguration.loadConfiguration(f);
    }

    public static FileConfiguration getConfig() {
        return fc;
    }
    public void saveConfig() {
        try {
            fc.save(f);
        }catch (IOException e) {
            System.out.println("Couldn't save kits.yml, no idea why");
        }
    }
    public void reloadConfig() {
        fc = YamlConfiguration.loadConfiguration(f);
    }
}
