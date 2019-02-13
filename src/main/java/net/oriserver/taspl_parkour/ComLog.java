/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.oriserver.taspl_parkour;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 *
 * @author orizeri
 */
public class ComLog implements Listener {

    @EventHandler
    public void OnCommand(PlayerCommandPreprocessEvent event) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("tas.admin.comlog")) {
                player.sendMessage(ChatColor.GRAY + "[Log]" + ChatColor.DARK_RED + event.getPlayer().getName() + ChatColor.GRAY + ":" + event.getMessage());
            }
        }
    }    
}
