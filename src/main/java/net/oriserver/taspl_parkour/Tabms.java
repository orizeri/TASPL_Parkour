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
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author orizeri
 */
public class Tabms implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String header = ChatColor.translateAlternateColorCodes('&', "&6Welcom to TarutoAthleticServer " + player.getName());
        String footer = ChatColor.translateAlternateColorCodes('&', "&eHP:&bhttp://seesaawiki.jp/taruto-server/");
        Headerfooter.message(player, header, footer);
        if (player.hasPermission("tas.admin")) {
            event.setJoinMessage("");
        }
    }

}
