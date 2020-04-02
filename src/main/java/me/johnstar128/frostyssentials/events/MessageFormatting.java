package me.johnstar128.frostyssentials.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class MessageFormatting implements Listener {

    @EventHandler
    public void onMessageSend(AsyncPlayerChatEvent e) {
        String msg = e.getMessage();
        e.setMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }
}
